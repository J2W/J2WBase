package j2w.team.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/2/1. fragment 视图
 */
public abstract class J2WBaseCleanFragment<T extends J2WIPresenter> extends J2WBaseFragment<T> {

	@Override public String initTag() {
		return getClass().getSimpleName();
	}

	/**
	 * 初始化视图 *
	 */
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-initData()");
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-onCreateView()");
		/** 打开开关触发菜单项 **/
		setHasOptionsMenu(true);
		mContentView = inflater.inflate(layoutId(), null, false);
		ButterKnife.inject(this, mContentView);

		return mContentView;
	}

	@Override public final void showLoading() {
		L.tag(initTag());
		L.i("Fragment-loading() 执行失败,请继承J2WBaseFragment");
	}

	@Override public final void showContent() {
		L.tag(initTag());
		L.i("Fragment-content() 执行失败,请继承J2WBaseFragment");
	}

	@Override public final void showEmpty() {
		L.tag(initTag());
		L.i("Fragment-empty() 执行失败,请继承J2WBaseFragment");
	}

	@Override public final void showError() {
		L.tag(initTag());
		L.i("Fragment-error() 执行失败,请继承J2WBaseFragment");
	}

	@Override public final int initLoadingLayout() {
		return 0;
	}

	@Override public final int initEmptyLayout() {
		return 0;
	}

	@Override public final int initErrorLayout() {
		return 0;
	}

}
