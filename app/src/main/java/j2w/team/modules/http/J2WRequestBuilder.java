package j2w.team.modules.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.net.URLEncoder;

import j2w.team.modules.http.annotations.Body;
import j2w.team.modules.http.annotations.Header;
import j2w.team.modules.http.annotations.Path;
import j2w.team.modules.http.converter.J2WConverter;
import okio.BufferedSink;

/**
 * Created by sky on 15/2/24.
 */
final class J2WRequestBuilder implements J2WRequestInterceptor.RequestFacade {

	/** 空头信息 */
	private static final Headers NO_HEADERS = Headers.of();

	/** 相对路径 **/
	private String relativeUrl;

	/** 头信息-内容类型 **/
	private String contentTypeHeader;

	/** okhttp 头信息 **/
	private Headers.Builder headers;

	/** 是否是异步 **/
	private final boolean async;
	/** API地址 **/
	private final String apiUrl;
	/** 数据转换器 **/
	private final J2WConverter converter;
	/** 参数注解数组 **/
	private final Annotation[] paramAnnotations;
	/** 请求方法名 **/
	private final String requestMethod;
	/** 请求体 **/
	private RequestBody body;

	J2WRequestBuilder(String apiUrl, J2WMethodInfo methodInfo, J2WConverter converter) {
		/**
		 * 初始化
		 */
		this.apiUrl = apiUrl; // API地址
		this.converter = converter;// 转换器
		paramAnnotations = methodInfo.requestParamAnnotations;// 参数注解数组
		requestMethod = methodInfo.requestMethod;// 请求方法
		contentTypeHeader = methodInfo.contentTypeHeader;// 头信息内容类型
		relativeUrl = methodInfo.requestUrl;// 请求相对路径
		/** 初始化-头信息 */
		if (methodInfo.headers != null) {
			headers = methodInfo.headers.newBuilder();
		}
		/** 判断方法是否是异步 **/
		async = methodInfo.executionType == J2WMethodInfo.ExecutionType.ASYNC;
	}

	@Override public void addHeader(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("Header name must not be null.");
		}
		if ("Content-Type".equalsIgnoreCase(name)) {
			contentTypeHeader = value;
			return;
		}

		Headers.Builder headers = this.headers;
		if (headers == null) {
			this.headers = headers = new Headers.Builder();
		}
		headers.add(name, value);
	}

	@Override public void addPathParam(String name, String value) {
		addPathParam(name, value, true);
	}

	@Override public void addEncodedPathParam(String name, String value) {
		addPathParam(name, value, false);
	}

	/**
	 * 添加参数
	 * 
	 * @param name
	 *            参数名称
	 * @param value
	 *            参数值
	 * @param urlEncodeValue
	 *            是否转码
	 */
	private void addPathParam(String name, String value, boolean urlEncodeValue) {
		if (name == null) {
			throw new IllegalArgumentException("参数名称不能为空");
		}
		if (value == null) {
			throw new IllegalArgumentException("Path 参数  \"" + name + "\" 值不能唯恐");
		}
		try {
			if (urlEncodeValue) {
				String encodedValue = URLEncoder.encode(String.valueOf(value), "UTF-8");
				// http 编码规范-任何+号都以空格 %20 代替
				encodedValue = encodedValue.replace("+", "%20");
				relativeUrl = relativeUrl.replace("{" + name + "}", encodedValue);
			} else {
				relativeUrl = relativeUrl.replace("{" + name + "}", String.valueOf(value));
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("无法转换路径参数  \"" + name + "\" 值  UTF-8:" + value, e);
		}
	}

	/**
	 * 设置参数
	 * 
	 * @param args
	 */
	void setArguments(Object[] args) {
		if (args == null) {
			return;
		}
		int count = args.length;
		if (async) {
			count -= 1;
		}
		for (int i = 0; i < count; i++) {
			Object value = args[i];

			Annotation annotation = paramAnnotations[i];
			Class<? extends Annotation> annotationType = annotation.annotationType();
			if (annotationType == Path.class) {
				Path path = (Path) annotation;
				String name = path.value();
				if (value == null) {
					throw new IllegalArgumentException("Path parameter \"" + name + "\" value must not be null.");
				}
				addPathParam(name, value.toString(), path.encode());
			} else if (annotationType == Header.class) {
				if (value != null) { // Skip null values.
					String name = ((Header) annotation).value();
					if (value instanceof Iterable) {
						for (Object iterableValue : (Iterable<?>) value) {
							if (iterableValue != null) { // Skip null values.
								addHeader(name, iterableValue.toString());
							}
						}
					} else if (value.getClass().isArray()) {
						for (int x = 0, arrayLength = Array.getLength(value); x < arrayLength; x++) {
							Object arrayValue = Array.get(value, x);
							if (arrayValue != null) { // Skip null values.
								addHeader(name, arrayValue.toString());
							}
						}
					} else {
						addHeader(name, value.toString());
					}
				}

			} else if (annotationType == Body.class) {
				if (value == null) {
					throw new IllegalArgumentException("Body parameter value must not be null.");
				}
				if (value instanceof RequestBody) {
					body = (RequestBody) value;
				} else {
					body = converter.toBody(value, value.getClass());
				}
			} else {
				throw new IllegalArgumentException("Unknown annotation: " + annotationType.getCanonicalName());
			}
		}
	}

	/**
	 * 编辑-okhttp 请求
	 * 
	 * @return
	 */
	Request build() {
		/** 请求api **/
		String apiUrl = this.apiUrl;
		StringBuilder url = new StringBuilder(apiUrl);
		if (apiUrl.endsWith("/")) {
			// 删除斜线 - 防止双斜线
			url.deleteCharAt(url.length() - 1);
		}
		// 增加相对路径
		url.append(relativeUrl);
		// 请求体
		RequestBody body = this.body;
		// 请求头
		Headers.Builder headerBuilder = this.headers;
		// 头信息内容
		if (contentTypeHeader != null) {
			if (body != null) {
				body = new MediaTypeOverridingRequestBody(body, contentTypeHeader);
			} else {
				if (headerBuilder == null) {
					headerBuilder = new Headers.Builder();
				}
				headerBuilder.add("Content-Type", contentTypeHeader);
			}
		}

		// 设置头信息
		Headers headers = headerBuilder != null ? headerBuilder.build() : NO_HEADERS;

		return new Request.Builder().url(url.toString()).method(requestMethod, body).headers(headers).build();
	}

	/**
	 * 媒体类型重写请求正文
	 */
	private static class MediaTypeOverridingRequestBody extends RequestBody {
		private final RequestBody delegate;
		private final MediaType mediaType;

		MediaTypeOverridingRequestBody(RequestBody delegate, String mediaType) {
			this.delegate = delegate;
			this.mediaType = MediaType.parse(mediaType);
		}

		@Override public MediaType contentType() {
			return mediaType;
		}

		@Override public long contentLength() throws IOException {
			return delegate.contentLength();
		}

		@Override public void writeTo(BufferedSink sink) throws IOException {
			delegate.writeTo(sink);
		}
	}
}
