package j2w.team.mvp.presenter;

import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.DynamicProxyUtils;

/**
 * Created by sky on 15/2/1. 中央处理器
 */
public abstract class J2WPresenter<T> implements J2WIPresenter {

	private boolean isCallBack;
	private T iView;

	/** 禁止创建默认构造函数 **/
	private J2WPresenter() {
	}

	/**
	 * 初始化
	 *
	 * @param iView
	 *            传递接口
	 */
	public J2WPresenter(T iView) {
		isCallBack = true;
		this.iView = DynamicProxyUtils.newProxyPresenter(iView, this);
	}

	/***
	 * 获取视图
	 * 
	 * @return 视图接口
	 */
	@Override public final T getView() {
		return iView;
	}

	/**
	 * 是否回调视图层方法
	 * 
	 * @return
	 */
	@Override public boolean isCallBack() {
		return isCallBack;
	}

	/**
	 * 消除引用
	 */
	@Override public void detach() {
		L.tag(initTag());
		L.i("Presenter-detach()");
		isCallBack = false;
	}

}
