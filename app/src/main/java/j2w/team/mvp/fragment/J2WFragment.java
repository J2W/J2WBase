package j2w.team.mvp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.mvp.J2WIViewActivity;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.presenter.J2WPresenterUtils;

/**
 * Created by sky on 15/2/1. fragment 视图
 */
public abstract class J2WFragment<T extends J2WIPresenter> extends Fragment implements J2WIViewFragment, View.OnTouchListener {

	/**
	 * view *
	 */
	View			mContentView;

	/**
	 * view *
	 */
	ViewAnimator	mViewAnimator;

	/**
	 * 业务逻辑对象 *
	 */
	private T		presenter	= null;

	/**
	 * 获取TAG标记
	 *
	 * @return tag
	 */
	@Override public String initTag() {
		return getClass().getSimpleName();
	}

	/**
	 * 初始化数据
	 *
	 * @param savedInstanceState
	 *            数据
	 */
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-initData()");
	}

	/**
	 * 获取Presenter
	 *
	 * @return 业务
	 */
	@Override public final T getPresenter() {
		if (presenter == null) {
			synchronized (this) {
				/** 创建业务类 **/
				presenter = J2WPresenterUtils.createPresenter(getClass(), this);
				L.tag(initTag());
				L.i("Presneter初始化完");
			}
		}
		return presenter;
	}

	/**
	 * 是否打开EventBus
	 *
	 * @return true 打开 false 关闭
	 */
	@Override public boolean isOpenEventBus() {
		return false;
	}

	/**
	 * 跳转
	 *
	 * @param clazz
	 *            activity.class
	 */
	@Override public void intent2Activity(Class clazz) {
		J2WHelper.intentTo(clazz);
	}

	/**
	 * 跳转
	 *
	 * @param clazz
	 *            activity.class
	 * @param bundle
	 *            数据
	 */
	@Override public void intent2Activity(Class clazz, Bundle bundle) {
		J2WHelper.intentTo(clazz, bundle);
	}

	/**
	 * 跳转
	 *
	 * @param clazz
	 *            activity.class
	 * @param requestCode
	 *            请求编号
	 */
	@Override public void intent2Activity(Class clazz, int requestCode) {
		J2WHelper.intentTo(clazz, requestCode);
	}

	/**
	 * 跳转
	 *
	 * @param clazz
	 *            acitivity.class
	 * @param bundle
	 *            数据
	 * @param requestCode
	 *            请求编号
	 */
	@Override public void intent2Activity(Class clazz, Bundle bundle, int requestCode) {
		J2WHelper.intentTo(clazz, bundle, requestCode);
	}

	/**
	 * 销毁当前页面
	 */
	@Override public void activityFinish() {
		L.tag(initTag());
		L.i("activityFinish()");
		getActivity().finish();
	}

	/**
	 * 初始化视图
	 *
	 * @param inflater
	 *            布局加载器
	 * @param container
	 *            父容器
	 */
	@Override public void initLayout(LayoutInflater inflater, ViewGroup container) {
		L.tag(initTag());
		L.i("Fragment-initLayout()");
		mContentView = inflater.inflate(R.layout.j2w_fragment_main, container, false);

		mViewAnimator = ButterKnife.findById(mContentView, android.R.id.home);

		// 获取View层接口
		J2WIViewActivity j2WIViewActivity = (J2WIViewActivity) getActivity();

		// 加载布局-初始化
		mViewAnimator.addView(inflater.inflate(j2WIViewActivity.fragmentLoadingLayout(), null, false));
		// 内容布局-初始化
		mViewAnimator.addView(inflater.inflate(layoutId(), null, false));
		// 空布局-初始化
		mViewAnimator.addView(inflater.inflate(j2WIViewActivity.fragmentEmptyLayout(), null, false));
		// 错误布局-初始化
		mViewAnimator.addView(inflater.inflate(j2WIViewActivity.fragmentErrorLayout(), null, false));
	}

	/**
	 * 获取主视图
	 *
	 * @return
	 */
	@Override public View getContentView() {
		L.tag(initTag());
		L.i("Fragment-getContentView()");
		return mContentView;
	}

	/**
	 * fragment 刷新标题栏
	 */
	@Override public void onActionBar() {
		L.tag(initTag());
		L.i("Fragment-onActionBar()");
	}

	/**
	 * fragment 刷新标题栏
	 */
	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		onActionBar();
	}

	/**
	 * fragment防止事件穿透
	 *
	 * @return true 禁止 false 开房
	 */
	@Override public boolean isTouch() {
		return true;
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		L.tag(initTag());
		L.i("Fragment-onAttach()");
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onCreate()");
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-onCreateView()");
		/** 打开开关触发菜单项 **/
		setHasOptionsMenu(true);
		/** 初始化视图 **/
		initLayout(inflater, container);
		/** 初始化所有组建 **/
		ButterKnife.inject(this, mContentView);
		return mContentView;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onActivityCreated()");
		initData(savedInstanceState);
	}

	@Override public void onStart() {
		super.onStart();
		L.tag(initTag());
		L.i("Fragment-onStart()");
	}

	@Override public void onResume() {
		super.onResume();
		L.tag(initTag());
		L.i("Fragment-onResume()");
		/** 判断EventBus 然后注册 **/
		if (isOpenEventBus()) {
			if (!EventBus.getDefault().isRegistered(this)) {
				EventBus.getDefault().register(this);
			}
		}
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		L.tag(initTag());
		L.i("Fragment-onSaveInstanceState()");

	}

	@Override public void onPause() {
		super.onPause();
		L.tag(initTag());
		L.i("Fragment-onPause()");
	}

	@Override public void onStop() {
		super.onStop();
		L.tag(initTag());
		L.i("Fragment-onStop()");
	}

	@Override public void onDestroyView() {
		super.onDestroyView();
		L.tag(initTag());
		L.m("Fragment-onDestroyView()");
		/** 切断关联 **/
		if (presenter != null) {
			presenter.detach();
		}
		/** 判断EventBus 然后销毁 **/
		if (isOpenEventBus()) {
			if (EventBus.getDefault().isRegistered(this)) {
				EventBus.getDefault().unregister(this);
			}
		}
		/** 清空注解view **/
		ButterKnife.reset(this);
	}

	@Override public void onDestroy() {
		super.onDestroy();
		L.tag(initTag());
		L.i("Fragment-onDestroy()");
	}

	@Override public void onDetach() {
		super.onDetach();
		L.tag(initTag());
		L.i("Fragment-onDetach()");
	}

	@Override public void showLoading() {
		L.tag(initTag());
		L.i("Fragment-loading()");
		show(0);
	}

	@Override public void showContent() {
		L.tag(initTag());
		L.i("Fragment-content()");
		show(1);
	}

	@Override public void showEmpty() {
		L.tag(initTag());
		L.i("Fragment-empty()");
		show(2);
	}

	@Override public void showError() {
		L.tag(initTag());
		L.i("Fragment-error()");
		show(3);
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

	/**
	 * 显示 * @param showState 0，1，2，3
	 */
	private final void show(int showState) {
		L.tag(initTag());
		L.i("show() mViewAnimator :" + mViewAnimator);
		if (mViewAnimator == null) {
			return;
		}
		int displayedChild = mViewAnimator.getDisplayedChild();
		if (displayedChild == showState) {
			return;
		}
		mViewAnimator.setDisplayedChild(showState);
	}

}