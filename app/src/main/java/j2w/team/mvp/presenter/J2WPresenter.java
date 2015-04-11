package j2w.team.mvp.presenter;

import android.os.Bundle;

import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.DynamicProxyUtils;
import j2w.team.modules.http.J2WError;

/**
 * Created by sky on 15/2/1. 中央处理器
 */
public abstract class J2WPresenter<T> {

	private boolean	isCallBack;

	private T		iView;

	/**
	 * 初始化 - 业务
	 * 
	 * @param iView
	 *            view层引用
	 */
	void initPresenter(T iView) {
		isCallBack = true;
		this.iView = DynamicProxyUtils.newProxyPresenter(iView, this);// 动态代理-业务
	}

	/**
	 * 获取TAG标记
	 *
	 * @return tag
	 */
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
	 * 获取公用接口
	 * 
	 * @param <I>
	 * @return
	 */
	public final <I> I getCommonView() {
		L.tag("J2WPresenter");
		L.i("getCommonView()");
		return (I) DynamicProxyUtils.newProxyPresenter(iView, this);
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
	public final void methodError(String methodName, Throwable throwable) {
		L.tag(initTag());
		L.i("methodError() methodName : " + methodName);
		if (throwable.getCause() instanceof J2WError) {
			methodHttpError(methodName, (J2WError) throwable.getCause());
		} else {
			methodCodingError(methodName, throwable.getCause());
		}
	}

	/** 网络异常 **/
	public final void methodHttpError(String methodName, J2WError j2WError) {
		L.tag(initTag());
		L.i("methodHttpError() methodName : " + methodName);
		if (j2WError.getKind() == J2WError.Kind.NETWORK) { // 请求发送前，网络问题
			L.tag(initTag());
			L.i("J2WError.Kind.NETWORK");
			errorNetWork();
		} else if (j2WError.getKind() == J2WError.Kind.HTTP) {// 请求响应后，网络错误
			L.tag(initTag());
			L.i("J2WError.Kind.HTTP");
			errorHttp();
		} else if (j2WError.getKind() == J2WError.Kind.UNEXPECTED) {// 意外错误
			L.tag(initTag());
			L.i("J2WError.Kind.UNEXPECTED");

			errorUnexpected();
		}
	}

	/** 发送请求前错误 **/
	public void errorNetWork() {}

	/** 请求得到响应后错误 **/
	public void errorHttp() {}

	/** 请求或者响应 意外错误 **/
	public void errorUnexpected() {}

	/** 编码异常 **/
	public void methodCodingError(String methodName, Throwable throwable) {
		L.tag(initTag());
		L.i("methodCodingError() methodName : " + methodName);
	}

	/**
	 * 读数据
	 * 
	 * @param bundle
	 */
	public void readData(Bundle bundle) {
		L.tag(initTag());
		L.i("readData(bundle)");
	}
}
