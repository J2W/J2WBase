package j2w.team.modules.http;

import java.lang.reflect.Method;
import java.util.Map;

import com.squareup.okhttp.Request;

import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.BaseHandler;
import j2w.team.modules.dialog.provided.ProgressDailogFragment;
import j2w.team.mvp.model.J2WConstants;

/**
 * Created by sky on 15/2/24. 动态代理 - 网络层
 */
public class J2WRestHandler extends BaseHandler {

	private final Map<Method, J2WMethodInfo>	methodDetailsCache;

	private final J2WRestAdapter				j2WRestAdapter;

	private final String						tag;

	private ProgressDailogFragment				progressDailogFragment;

	public J2WRestHandler(J2WRestAdapter j2WRestAdapter, Map<Method, J2WMethodInfo> methodDetailsCache, String tag) {
		super("");
		this.j2WRestAdapter = j2WRestAdapter;
		this.methodDetailsCache = methodDetailsCache;
		this.tag = tag;
	}

	@SuppressWarnings("unchecked")//
	@Override public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {
		Object object;// 返回结果
		// 如果是实现类 直接执行方法
		if (method.getDeclaringClass() == Object.class) {
			L.tag("J2W-Method");
			L.i("直接执行: " + method.getName());
			return method.invoke(this, args);
		}

		// 获取方法
		J2WMethodInfo methodInfo = J2WRestAdapter.getMethodInfo(methodDetailsCache, method);
		// 设置tag标记
		methodInfo.requestTag = tag;
		// 创建请求
		Request request = j2WRestAdapter.createRequest(methodInfo, args);

		switch (methodInfo.executionType) {
			case SYNC:
				if (J2WConstants.J2W_DIALOG_PROGRESS.equals(tag)) {
					if (progressDailogFragment == null) {
						progressDailogFragment = (ProgressDailogFragment) ProgressDailogFragment.createBuilder().setTag(J2WConstants.J2W_DIALOG_PROGRESS).setRequestCode(J2WConstants.J2W_DIALOG_CODE)
								.setMessage(R.string.progress_dialog_value)// 设置内容
								.showAllowingStateLoss();// 显示
					}
				}
				object = j2WRestAdapter.invokeSync(methodInfo, request); //执行

				if (progressDailogFragment != null && progressDailogFragment.isAdded()) {
					progressDailogFragment.dismiss();
                    progressDailogFragment = null;
				}
				return object;
			case ASYNC:
				j2WRestAdapter.invokeAsync(methodInfo, request, (J2WCallback) args[args.length - 1]);
				return null;
			default:
				throw new IllegalStateException("未知的反应类型: " + methodInfo.executionType);
		}
	}

}
