package j2w.team.mvp.presenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.util.Stack;

import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.DynamicProxyUtils;
import j2w.team.modules.http.J2WError;
import j2w.team.mvp.J2WIView;
import j2w.team.mvp.fragment.J2WIViewPullListFragment;

/**
 * Created by sky on 15/2/1. 中央处理器
 */
public abstract class J2WPresenter<T> {

	private boolean	isCallBack; // 是否回调

	private T		iView;		// View代理

	private int		state;		// 状态

	/**
	 * 初始化 - 业务
	 * 
	 * @param iView
	 *            view层引用
	 */
	void initPresenter(T iView) {
		this.isCallBack = true;
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
			if ("Canceled".equals(throwable.getCause().getMessage())) {
				errorCancel();
			} else {
				methodHttpError(methodName, (J2WError) throwable.getCause());
			}
		} else {
			methodCodingError(methodName, throwable.getCause());
		}
	}

	/** 网络异常 **/
	public final void methodHttpError(String methodName, J2WError j2WError) {
		L.tag(initTag());
		L.i("methodHttpError() methodName : " + methodName);

		if (iView instanceof J2WIViewPullListFragment) { // 如果是有上下拉刷新
			((J2WIViewPullListFragment) iView).setRefreshing(false);
			((J2WIViewPullListFragment) iView).setLoading(false);
		}

		if (iView instanceof J2WIView) {
			((J2WIView) iView).loadingClose(); // 如果有进度条
		}

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

	/** 发送请求前取消 **/
	public void errorCancel() {
		L.tag(initTag());
		L.i("errorCancel()");
	}

	/** 发送请求前错误 **/
	public void errorNetWork() {
		L.tag(initTag());
		L.i("errorNetWork()");
	}

	/** 请求得到响应后错误 **/
	public void errorHttp() {
		L.tag(initTag());
		L.i("errorHttp()");
	}

	/** 请求或者响应 意外错误 **/
	public void errorUnexpected() {
		L.tag(initTag());
		L.i("errorUnexpected()");
	}

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

	/**
	 * 获取状态
	 */
	public int getState() {
		return state;
	}

	/**
	 * 获取碎片管理器
	 * 
	 * @return
	 */
	public FragmentManager getFManager() {
		return J2WHelper.getScreenHelper().currentActivity().getSupportFragmentManager();
	}
}
