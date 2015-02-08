package j2w.team.mvp.presenter;

import j2w.team.common.utils.proxy.DynamicProxyUtils;

/**
 * Created by sky on 15/2/8. 包装类
 */
public class J2WPresenterBean<T> {
	private T iView;
	public boolean isCallBack;

	public void setiView(T iView) {
		this.iView = DynamicProxyUtils.newProxyPresenter(iView, this);
	}

    public T getiView() {
        return iView;
    }
}
