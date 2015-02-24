package j2w.team.modules.http;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.DynamicProxyUtils;
import j2w.team.common.utils.proxy.J2WRestHandler;
import j2w.team.modules.http.converter.J2WConverter;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 15/2/24. 网络适配器
 */
public class J2WRestAdapter {
    //缓存
    private final Map<Class<?>, Map<Method, J2WMethodInfo>> serviceMethodInfoCache = new LinkedHashMap<Class<?>, Map<Method, J2WMethodInfo>>();

    //端点地址
    final J2WEndpoint j2WEndpoint;

    //转换器
    private J2WConverter converter;

    //拦截器
    private J2WRequestInterceptor requestInterceptor;

    //OKHTTP
    private final OkHttpClient client;

    //错误
    private J2WErrorHandler errorHandler;

    /**
     * 构造器
     *
     * @param j2WEndpoint 端点地址
     */
    public J2WRestAdapter(OkHttpClient client, J2WEndpoint j2WEndpoint, J2WConverter converter, J2WRequestInterceptor requestInterceptor, J2WErrorHandler errorHandler) {
        //OKHTTP
        this.client = client;
        //端点地址
        this.j2WEndpoint = j2WEndpoint;
        //数据转换器
        this.converter = converter;
        //拦截器
        this.requestInterceptor = requestInterceptor;
        //错误
        this.errorHandler = errorHandler;
    }

    /**
     * 创建代理
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        // 验证是否是接口
        DynamicProxyUtils.validateServiceClass(service);
        // 验证是否继承其他接口
        DynamicProxyUtils.validateInterfaceServiceClass(service);
        // 创建动态代理-网络层
        J2WRestHandler j2WRestHandler = new J2WRestHandler(this, getMethodInfoCache(service));
        // 创建代理类并返回
        return DynamicProxyUtils.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, j2WRestHandler);
    }

    /**
     * 执行同步请求
     *
     * @param methodInfo
     * @param request
     * @return
     * @throws Throwable
     */
    public Object invokeSync(J2WMethodInfo methodInfo, Request request) throws Throwable {
        try {
            //发送请求
            Response response = client.newCall(request).execute();
            //拿到结果调用结果处理方法
            return createResult(methodInfo, response);
        } catch (IOException e) {
            throw new IllegalStateException("异常" + e.toString());
        }
    }

    /**
     * 执行异步请求
     *
     * @param methodInfo
     * @param request
     * @param callback
     */
    public void invokeAsync(final J2WMethodInfo methodInfo, final Request request,
                            final J2WCallback callback) {
        Call call = client.newCall(request);
        call.enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callFailure(callback, J2WError.networkFailure(request.urlString(), e));
            }

            @Override
            public void onResponse(Response response) {
                try {
                    Object result = createResult(methodInfo, response);
                    callResponse(callback, result, response);
                } catch (J2WError error) {
                    callFailure(callback, error);
                }
            }
        });
    }

    /**
     * 正确回调
     *
     * @param callback
     * @param result
     * @param response
     */
    private void callResponse(final J2WCallback callback, final Object result,
                              final Response response) {
        //主线程执行
        J2WHelper.getMainLooper().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.success(result, response);
                } catch (Throwable throwable) {
                    L.tag("J2W-Method");
                    L.i("方法执行失败");
                    return;
                }
            }
        });
    }

    /**
     * 错误回调
     *
     * @param callback
     * @param error
     */
    private void callFailure(final J2WCallback callback, J2WError error) {
        Throwable throwable = handleError(error);
        if (throwable != error) {
            Response response = error.getResponse();
            if (response != null) {
                error = J2WError.unexpectedError(response, throwable);
            } else {
                error = J2WError.unexpectedError(error.getUrl(), throwable);
            }
        }
        final J2WError finalError = error;
        //主线程执行
        J2WHelper.getMainLooper().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.failure(finalError);
                } catch (Throwable throwable) {
                    L.tag("J2W-Method");
                    L.i("方法执行失败");
                    return;
                }
            }
        });
    }

    /**
     * 错误操作
     *
     * @param error
     * @return
     */
    private Throwable handleError(J2WError error) {
        Throwable throwable = errorHandler.handleError(error);
        if (throwable == null) {
            return new IllegalStateException("Error handler returned null for wrapped exception.",
                    error);
        }
        return throwable;
    }

    /**
     * 创建结果
     *
     * @param methodInfo
     * @param response
     * @return
     */
    private Object createResult(J2WMethodInfo methodInfo, Response response) {
        try {
            //解析结果
            return parseResult(methodInfo, response);
        } catch (IOException e) {
            throw new IllegalStateException("异常" + e.toString());
        }
    }

    /**
     * 解析结果集
     *
     * @param methodInfo
     * @param response
     * @return
     * @throws IOException
     */
    private Object parseResult(J2WMethodInfo methodInfo, Response response)
            throws IOException {
        //获取结果类型
        Type type = methodInfo.responseObjectType;

        //拿到响应编号
        int statusCode = response.code();

        //判断
        if (statusCode < 200 || statusCode >= 300) {
            response = J2WMethodInfo.readBodyToBytesIfNecessary(response);
            throw new RuntimeException(response + "");
        }

        //如果是结果集
        if (type.equals(Response.class)) {
            return response;
        }

        //读取响应体
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        // 捕获请求异常
        ExceptionCatchingRequestBody wrapped = new ExceptionCatchingRequestBody(body);
        try {
            return converter.fromBody(wrapped, type);
        } catch (RuntimeException e) {
            if (wrapped.threwException()) {
                throw wrapped.getThrownException();
            }
            throw e;
        }
    }

    /**
     * 创建请求
     *
     * @param methodInfo 方法信息
     * @param args       参数
     * @return
     */
    public Request createRequest(J2WMethodInfo methodInfo, Object[] args) {
        //获取url
        String serverUrl = j2WEndpoint.url();
        //编辑请求
        J2WRequestBuilder requestBuilder = new J2WRequestBuilder(serverUrl, methodInfo, converter);
        //设置参数
        requestBuilder.setArguments(args);
        //交给拦截器
        requestInterceptor.intercept(requestBuilder);

        return requestBuilder.build();
    }

    /**
     * 获取方法信息
     *
     * @param cache
     * @param method
     * @return
     */
    public static J2WMethodInfo getMethodInfo(Map<Method, J2WMethodInfo> cache, Method method) {
        synchronized (cache) {
            J2WMethodInfo methodInfo = cache.get(method);
            if (methodInfo == null) {
                methodInfo = new J2WMethodInfo(method);
                cache.put(method, methodInfo);
            }
            return methodInfo;
        }
    }

    /**
     * 从缓存里获取方法
     *
     * @param service
     * @return
     */
    Map<Method, J2WMethodInfo> getMethodInfoCache(Class<?> service) {
        synchronized (serviceMethodInfoCache) {
            Map<Method, J2WMethodInfo> methodInfoCache = serviceMethodInfoCache.get(service);
            if (methodInfoCache == null) {
                methodInfoCache = new LinkedHashMap<Method, J2WMethodInfo>();
                serviceMethodInfoCache.put(service, methodInfoCache);
            }
            return methodInfoCache;
        }
    }


}
