package j2w.team.mvp.presenter;

import java.net.ConnectException;

import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.DynamicProxyUtils;
import j2w.team.modules.http.J2WError;
import j2w.team.modules.toast.J2WToast;

/**
 * Created by sky on 15/2/1. 中央处理器
 */
public abstract class J2WPresenter<T> {

	private boolean	isCallBack;

	private T		iView;

	/** 初始化 **/
	void initPresenter(T iView) {
		isCallBack = true;
		this.iView = DynamicProxyUtils.newProxyPresenter(iView, this);// 动态代理-业务
	}

	/** 获取TAG标记 **/
	public String initTag() {
		return getClass().getSimpleName();
	}

	/***
	 * 获取视图
	 * 
	 * @return 视图接口
	 */
	public final T getView() {
		return this.iView;
	}

	/**
	 * 是否回调视图层方法
	 * 
	 * @return
	 */
	public boolean isCallBack() {
		return isCallBack;
	}

	/**
	 * 消除引用
	 */
	public void detach() {
		L.tag(initTag());
		L.i("detach()");
		isCallBack = false;
	}

	/**
	 * 错误处理
	 * 
	 * @param methodName
	 * @param throwable
	 */
	public void methodError(String methodName, Throwable throwable) {
		L.tag(initTag());
		L.i("methodError() methodName : " + methodName);
		if (throwable.getCause() instanceof J2WError) {
			methodHttpError(methodName, (J2WError) throwable.getCause());
		} else {
			methodCodingError(methodName, throwable.getCause());
		}
	}

	/** 网络异常 **/
	public void methodHttpError(String methodName, J2WError j2WError) {
		L.tag(initTag());
		L.i("methodHttpError() methodName : " + methodName);
		if (j2WError.getCause() instanceof ConnectException) {
			J2WToast.show(J2WHelper.getScreenHelper().currentActivity().getString(R.string.http_error));
		}
	}

	/** 编码异常 **/
	public void methodCodingError(String methodName, Throwable throwable) {
		L.tag(initTag());
		L.i("methodCodingError() methodName : " + methodName);
	}
}
