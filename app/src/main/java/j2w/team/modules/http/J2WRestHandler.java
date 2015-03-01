package j2w.team.modules.http;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.squareup.okhttp.Request;

import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.BaseHandler;

/**
 * Created by sky on 15/2/24. 动态代理 - 网络层
 */
public class J2WRestHandler extends BaseHandler {

	private final Map<Method, J2WMethodInfo>	methodDetailsCache;

	private final J2WRestAdapter				j2WRestAdapter;

	public J2WRestHandler(J2WRestAdapter j2WRestAdapter, Map<Method, J2WMethodInfo> methodDetailsCache) {
		super("");
		this.j2WRestAdapter = j2WRestAdapter;
		this.methodDetailsCache = methodDetailsCache;
	}

	@SuppressWarnings("unchecked")//
	@Override public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {
		// 如果是实现类 直接执行方法
		if (method.getDeclaringClass() == Object.class) {
			L.tag("J2W-Method");
			L.i("直接执行: " + method.getName());
			return method.invoke(this, args);
		}

		// 获取方法
		J2WMethodInfo methodInfo = J2WRestAdapter.getMethodInfo(methodDetailsCache, method);
		// 创建请求
		Request request = j2WRestAdapter.createRequest(methodInfo, args);
		switch (methodInfo.executionType) {
			case SYNC:
				return j2WRestAdapter.invokeSync(methodInfo, request);
			case ASYNC:
				j2WRestAdapter.invokeAsync(methodInfo, request, (J2WCallback) args[args.length - 1]);
				return null;
			default:
				throw new IllegalStateException("未知的反应类型: " + methodInfo.executionType);
		}
	}

}
