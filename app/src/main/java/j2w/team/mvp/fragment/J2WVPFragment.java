package j2w.team.mvp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/2/1. fragment 视图
 */
public abstract class J2WVPFragment<T extends J2WIPresenter> extends J2WFragment<T> implements J2WIViewVPFragment {

	private boolean	isDelayedData	= false;

	/**
	 * 是否添加Fragment状态布局
	 *
	 * @return true 打开 false 关闭
	 */
	@Override public boolean isAddFragmentState() {
		return true;
	}

	/**
	 * ViewPager切换 是否调用延迟加载
	 */
	@Override public final void isDelayedData() {
		L.tag(initTag());
		L.i("Fragment-isDelayedData() return " + isDelayedData);
		if (isDelayedData) {
			return;
		}
		// 为了只初始化一次
		isDelayedData = true;
		initDelayedData();
		// 更新actionbar
		updateActionBar();
	}

	/**
	 * ViewPager切换 延迟数据初始化 - 执行一次
	 */
	@Override public void initDelayedData() {
		L.tag(initTag());
		L.i("Fragment-initDelayedData()");
	}

	/**
	 * ViewPager 重新执行
	 *
	 * @param index
	 *            下标
	 */
	@Override public void onFragmentRestart(int index) {
		L.tag(initTag());
		L.i("Fragment-onFragmentRestart()");
	}

	/**
	 * ViewPager切换 更新ActionBar
	 */
	@Override public void updateActionBar() {
		L.tag(initTag());
		L.i("Fragment-updateActionBar()");
	}

	/**
	 * fragment 刷新标题栏
	 */
	@Override public final void onActionBar() {
		L.tag(initTag());
		L.i("Fragment-onActionBar()");
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-onCreateView()");
		/** 打开开关触发菜单项 **/
		setHasOptionsMenu(true);
		/** 初始化视图 **/
		if (isAddFragmentState()) {
			initLayout(inflater, container);
		} else {
			mContentView = inflater.inflate(layoutId(), container, false);
		}
		/** 初始化所有组建 **/
		ButterKnife.inject(this, mContentView);
		/** 初始化点击事件 **/
		mContentView.setOnTouchListener(this);// 设置点击事件

		return mContentView;
	}

	/**
	 * 防止事件穿透
	 *
	 * @param v
	 *            View
	 * @param event
	 *            事件
	 * @return true 拦截 false 不拦截
	 */
	@Override public boolean onTouch(View v, MotionEvent event) {
		return isTouch();
	}
}
