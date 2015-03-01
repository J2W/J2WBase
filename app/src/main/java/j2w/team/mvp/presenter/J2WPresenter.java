package j2w.team.mvp.presenter;

import j2w.team.common.log.L;
import j2w.team.common.utils.proxy.DynamicProxyUtils;

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
	public abstract String initTag();

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

}
