package j2w.team.mvp.presenter;

import j2w.team.common.log.L;

/**
 * Created by sky on 15/2/1. 中央处理器
 */
public abstract class J2WPresenter<T> implements J2WIPresenter {

	private J2WPresenterBean j2WPresenterBean;

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
		j2WPresenterBean = new J2WPresenterBean();
		j2WPresenterBean.isCallBack = true;
		j2WPresenterBean.setiView(iView);

	}

	/***
	 * 获取视图
	 * 
	 * @return 视图接口
	 */
	@Override public final T getView() {
		return (T) j2WPresenterBean.getiView();
	}

	/**
	 * 消除引用
	 */
	@Override public void detach() {
		L.tag(initTag());
		L.i("Presenter-detach()");
		if (j2WPresenterBean != null) {
			j2WPresenterBean.isCallBack = false;
		}
	}
}
