package j2w.team.modules.http;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

import j2w.team.modules.http.converter.J2WConverter;

/**
 * Created by sky on 15/2/23.
 */
public class J2WError extends RuntimeException {

	public static J2WError networkFailure(String url, IOException exception) {
		return new J2WError(exception.getMessage(), url, null, null, null, Kind.NETWORK, exception);
	}

	public static J2WError networkError(Response response, IOException exception) {
		response = response.newBuilder().body(null).build();
		return new J2WError(exception.getMessage(), response.request().urlString(), null, null, null, Kind.NETWORK, exception);
	}

	public static J2WError httpError(Response response, J2WConverter converter, Type successType) {
		String message = response.code() + " " + response.message();
		return new J2WError(message, response.request().urlString(), response, converter, successType, Kind.HTTP, null);
	}

	public static J2WError unexpectedError(Response response, Throwable exception) {
		response = response.newBuilder().body(null).build(); // Remove any body.
		return new J2WError(exception.getMessage(), response.request().urlString(), response, null, null, Kind.UNEXPECTED, exception);
	}

	public static J2WError unexpectedError(String url, Throwable exception) {
		return new J2WError(exception.getMessage(), url, null, null, null, Kind.UNEXPECTED, exception);
	}

	public enum Kind {
		/** An {@link IOException} occurred while communicating to the server. */
		NETWORK,
		/** A non-200 HTTP status code was received from the server. */
		HTTP,
		/**
		 * An internal error occurred while attempting to execute a request. It
		 * is best practice to re-throw this exception so your application
		 * crashes.
		 */
		UNEXPECTED
	}

	private final String url;
	private final Response response;
	private final J2WConverter converter;
	private final Type successType;
	private final Kind kind;

	J2WError(String message, String url, Response response, J2WConverter converter, Type successType, Kind kind, Throwable exception) {
		super(message, exception);
		this.url = url;
		this.response = response;
		this.converter = converter;
		this.successType = successType;
		this.kind = kind;
	}

	/** 返回错误的url */
	public String getUrl() {
		return url;
	}

	/** 返回错误的结果. */
	public Response getResponse() {
		return response;
	}

	/** 引发这个错误的事件类-索引. */
	public Kind getKind() {
		return kind;
	}
    /** 返回错误的结果集. */
	public Object getBody() {
		return getBodyAs(successType);
	}

    /** 类型*/
	public Type getSuccessType() {
		return successType;
	}

    /**
     * 返回结果集*
     * @param type
     * @return
     */
	public Object getBodyAs(Type type) {
		if (response == null) {
			return null;
		}
		ResponseBody body = response.body();
		if (body == null) {
			return null;
		}
		try {
			return converter.fromBody(body, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}