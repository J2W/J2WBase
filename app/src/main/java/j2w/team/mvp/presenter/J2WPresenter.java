package j2w.team.mvp.presenter;

import java.lang.ref.WeakReference;

import j2w.team.mvp.view.J2WIView;

/**
 * Created by sky on 15/2/1. 中央处理器
 */
public class J2WPresenter<T extends J2WIView> {

	// 弱引用 view层接口
	private WeakReference<T> iView;

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
		this.iView = new WeakReference<T>(iView);
	}

	/** 获取视图接口 **/
	protected T getIView() {
		return iView.get();
	}

	/**
	 * 判断视图层是否存在
	 * 
	 * @return true 存在 false 不存在
	 */
	protected boolean isIView() {
		return iView.get() != null ? true : false;
	}
}
