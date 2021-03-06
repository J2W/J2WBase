package j2w.team.mvp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
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
import j2w.team.modules.dialog.iface.IDialogListener;
import j2w.team.modules.dialog.provided.ProgressDailogFragment;
import j2w.team.modules.http.J2WRestAdapter;
import j2w.team.mvp.J2WIViewABActivity;
import j2w.team.mvp.J2WIViewActivity;
import j2w.team.mvp.model.J2WConstants;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.presenter.J2WPresenterUtils;

/**
 * Created by sky on 15/2/1. fragment 视图
 */
public abstract class J2WFragment<T extends J2WIPresenter> extends Fragment implements J2WIViewFragment, View.OnTouchListener, IDialogListener, View.OnClickListener {

	/** 默认进度条 **/
	ProgressDailogFragment	dialogFragment;					// 交互弹窗

	/**
	 * view *
	 */
	View					mContentView;

	/**
	 * view *
	 */
	ViewAnimator			mViewAnimator;

	/**
	 * 业务逻辑对象 *
	 */
	private T				presenter				= null;

	/**
	 * 是否显示状态
	 */
	private boolean			isShowContent			= false;

	/**
	 * 延迟加载 标记
	 */
	private boolean			isDelayedData			= false;

	/**
	 * 延迟加载是否完毕 true 完成 false 没有完成
	 */
	private boolean			isDelayedDateSuccess	= false;

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
	 * 获取上下文
	 *
	 * @return 上下文
	 */
	@Override public Context getContext() {
		return getActivity();
	}

	/**
	 * 获取碎片管理器
	 *
	 * @return
	 */
	@Override public FragmentManager getFManager() {
		return getFragmentManager();
	}

	/**
	 * 获取碎片管理器 - 内部管理器
	 *
	 * @return
	 */
	@Override public FragmentManager getChildFManager() {
		return getChildFragmentManager();
	}

	/**
	 * 设置Acitvity标题栏
	 */
	@Override public void setActivityTitle(Object object, int code) {
		if (getActivity() instanceof J2WIViewABActivity) {
			J2WIViewABActivity j2WIViewABActivity = (J2WIViewABActivity) getActivity();
			j2WIViewABActivity.setActionbarTitle(object, code);
		}
	}

	/**
	 * 设置Acitvity标题栏
	 */
	@Override public void setActivityTitle(Object value) {
		setActivityTitle(value, 0);
	}

	/**
	 * 提交fragment
	 *
	 * @param fragment
	 *            实例
	 */
	@Override public void commitFragment(Fragment fragment) {
		commitFragment(fragment, fragment.getClass().getSimpleName());
	}

	/**
	 * 提交fragment
	 *
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	public final void commitFragment(Fragment fragment, String tag) {
		commitFragment(android.R.id.custom, fragment, tag);
	}

	/**
	 * 提交fragment
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 */
	@Override public void commitFragment(int layoutId, Fragment fragment) {
		commitFragment(layoutId, fragment, fragment.getClass().getSimpleName());

	}

	/**
	 * 提交fragment
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	public void commitFragment(int layoutId, Fragment fragment, String tag) {
		L.tag(initTag());
		L.i("commitFragment(int layoutId, Fragment fragment, String tag)");
		if (fragment != null && fragment.isAdded()) {
			L.tag(initTag());
			L.i("fragment 不能为空，或者已经被添加！");
			return;
		}
		getFManager().beginTransaction().add(layoutId, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
		if (!fragmentState()) {
			getFManager().executePendingTransactions();
		}
	}

	/**
	 * 提交fragment
	 *
	 * @param old
	 *            需要销毁的fragment
	 * @param fragment
	 *            新碎片
	 */
	@Override public void commitFragment(Fragment old, Fragment fragment) {
		commitFragment(old, fragment, fragment.getClass().getSimpleName());
	}

	/**
	 * 提交fragment
	 *
	 * @param old
	 *            需要销毁的fragment
	 * @param fragment
	 *            实例
	 * @param tag
	 */
	@Override public void commitFragment(Fragment old, Fragment fragment, String tag) {
		commitFragment(old, android.R.id.custom, fragment, tag);
	}

	/**
	 * @param old
	 *            需要销毁的fragment
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 */
	@Override public void commitFragment(Fragment old, int layoutId, Fragment fragment) {
		commitFragment(old, layoutId, fragment, fragment.getClass().getSimpleName());
	}

	/**
	 * 提交fragment
	 *
	 * @param old
	 *            需要销毁的fragment
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 * @param tag
	 */
	@Override public void commitFragment(Fragment old, int layoutId, Fragment fragment, String tag) {
		L.tag(initTag());
		L.i("commitFragment(Fragment old,int layoutId,Fragment fragment, String tag)");
		if (layoutId == 0) {
			L.tag(initTag());
			L.i("layoutId 不能为空！");
			return;
		}
		if (fragment != null && fragment.isAdded()) {
			L.tag(initTag());
			L.i("fragment 不能为空，或者已经被添加！");
			return;
		}
		FragmentTransaction fragmentTransaction = getFManager().beginTransaction();
		if (old != null) {
			fragmentTransaction.detach(old);
		}
		fragmentTransaction.add(layoutId, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
		if (!fragmentState()) {
			getFManager().executePendingTransactions();
		}
	}

	/**
	 * 提交fragment
	 *
	 * @param fragment
	 *            实例
	 */
	@Override public void commitBackStackFragment(Fragment fragment) {
		commitBackStackFragment(fragment, fragment.getClass().getSimpleName());
	}

	/**
	 * 提交fragment - 压栈
	 *
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	@Override public void commitBackStackFragment(Fragment fragment, String tag) {
		L.tag(initTag());
		L.i("commitBackStackFragment(Fragment fragment, String tag)");
		if (fragment != null && fragment.isAdded()) {
			L.tag(initTag());
			L.i("fragment 不能为空，或者已经被添加！");
			return;
		}
		getFManager().beginTransaction().add(android.R.id.custom, fragment, tag).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
		if (!fragmentState()) {
			getFManager().executePendingTransactions();
		}
	}

	/**
	 * 提交fragment - 压栈
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 */
	@Override public void commitBackStackFragment(int layoutId, Fragment fragment) {
		commitBackStackFragment(layoutId, fragment, fragment.getClass().getSimpleName());
	}

	/**
	 * 提交fragment - 压栈
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	@Override public void commitBackStackFragment(int layoutId, Fragment fragment, String tag) {
		L.tag(initTag());
		L.i("commitBackStackFragment(int layoutId,Fragment fragment, String tag)");
		if (layoutId == 0) {
			L.tag(initTag());
			L.i("layoutId 不能为空！");
			return;
		}
		if (fragment != null && fragment.isAdded()) {
			L.tag(initTag());
			L.i("fragment 不能为空，或者已经被添加！");
			return;
		}
		getFManager().beginTransaction().add(layoutId, fragment, tag).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
		if (!fragmentState()) {
			getFManager().executePendingTransactions();
		}
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
		if (isFragmentBackground()) {
			mContentView.setBackgroundResource(android.R.color.transparent);
		}
		mViewAnimator = ButterKnife.findById(mContentView, android.R.id.home);

		// 获取View层接口
		J2WIViewActivity j2WIViewActivity = (J2WIViewActivity) getActivity();

		// 加载布局-初始化
		inflater.inflate(fragmentLoadingLayout() == 0 ? j2WIViewActivity.fragmentLoadingLayout() : fragmentLoadingLayout(), mViewAnimator, true);
		// 内容布局-初始化
		inflater.inflate(layoutId(), mViewAnimator, true);
		// 空布局-初始化
		inflater.inflate(fragmentEmptyLayout() == 0 ? j2WIViewActivity.fragmentEmptyLayout() : fragmentEmptyLayout(), mViewAnimator, true);
		// 错误布局-初始化
		inflater.inflate(fragmentErrorLayout() == 0 ? j2WIViewActivity.fragmentErrorLayout() : fragmentErrorLayout(), mViewAnimator, true);
	}

	/**
	 * 初始化视图 - 无状态
	 */
	@Override public void initNotState(LayoutInflater inflater, ViewGroup container) {
		L.tag(initTag());
		L.i("Fragment-initNotState()");
		isShowContent = true;
		mContentView = inflater.inflate(R.layout.j2w_layout_default, container, false);
		if (isFragmentBackground()) {
			mContentView.setBackgroundResource(android.R.color.transparent);
		}
		inflater.inflate(layoutId(), (ViewGroup) mContentView, true);
	}

	/**
	 * 是否添加Fragment状态布局
	 *
	 * @return true 打开 false 关闭
	 */
	public boolean fragmentState() {
		return true;
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
		J2WHelper.getInstance().onFragmentAttach(this);
		L.tag(initTag());
		L.i("Fragment-onAttach()");
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		J2WHelper.getInstance().onFragmentCreate(this, savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onCreate()");
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		J2WHelper.getInstance().onFragmentCreateView(this, inflater, container, savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onCreateView()");
		/** 打开开关触发菜单项 **/
		setHasOptionsMenu(true);
		/** 初始化视图 **/
		if (fragmentState()) {
			initLayout(inflater, container);
		} else {
			initNotState(inflater, container);
		}
		/** 初始化所有组建 **/
		ButterKnife.inject(this, mContentView);
		/** 初始化点击事件 **/
		mContentView.setOnTouchListener(this);// 设置点击事件

		return mContentView;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		J2WHelper.getInstance().onFragmentActivityCreated(this, savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onActivityCreated()");
		if (!isAddDelayedData()) { // 如果打开延迟加载 不执行初始化数据
			initData(savedInstanceState);
		}
	}

	@Override public void onStart() {
		super.onStart();
		J2WHelper.getInstance().onFragmentStart(this);
		L.tag(initTag());
		L.i("Fragment-onStart()");
	}

	@Override public void onResume() {
		super.onResume();
		J2WHelper.getInstance().onFragmentResume(this);
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
		J2WHelper.getInstance().onSaveInstanceState(this, outState);
		L.tag(initTag());
		L.i("Fragment-onSaveInstanceState()");

	}

	@Override public void onPause() {
		super.onPause();
		J2WHelper.getInstance().onFragmentPause(this);
		L.tag(initTag());
		L.i("Fragment-onPause()");
	}

	@Override public void onStop() {
		super.onStop();
		J2WHelper.getInstance().onFragmentStop(this);
		L.tag(initTag());
		L.i("Fragment-onStop()");
	}

	@Override public void onDestroyView() {
		super.onDestroyView();
		J2WHelper.getInstance().onFragmentDestroyView(this);
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
		J2WHelper.getInstance().onFragmentDestroy(this);
		L.tag(initTag());
		L.i("Fragment-onDestroy()");
	}

	@Override public void onDetach() {
		super.onDetach();
		J2WHelper.getInstance().onFragmentDetach(this);
		L.tag(initTag());
		L.i("Fragment-onDetach()");
	}

	@Override public void showLoading() {
		L.tag(initTag());
		L.i("Fragment-loading()");
		show(J2WConstants.J2W_STATE_LOADING);
		isShowContent = false;
	}

	@Override public void showContent() {
		L.tag(initTag());
		L.i("Fragment-content()");
		show(J2WConstants.J2W_STATE_CONTENT);
		isShowContent = true;
	}

	public boolean isShowContent() {
		return isShowContent;
	}

	@Override public void showEmpty() {
		L.tag(initTag());
		L.i("Fragment-empty()");
		show(J2WConstants.J2W_STATE_EMPTY);
		isShowContent = false;
	}

	@Override public void showError() {
		L.tag(initTag());
		L.i("Fragment-error()");
		show(J2WConstants.J2W_STATE_ERROR);
		isShowContent = false;
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
		if (!fragmentState()) {
			L.tag(initTag());
			L.i("当前fragment没有打开状态模式! fragmentState() = false");
			return;
		}
		if (mViewAnimator == null) {
			return;
		}
		int displayedChild = mViewAnimator.getDisplayedChild();
		if (displayedChild == showState) {
			return;
		}
		mViewAnimator.setDisplayedChild(showState);

		// 如果是错误页面可以点击
		if (showState == J2WConstants.J2W_STATE_ERROR || showState == J2WConstants.J2W_STATE_EMPTY) {
			mViewAnimator.getCurrentView().setOnClickListener(this);
		}
	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@Override public void onClick(View v) {
		showLoading();
		initData(getArguments());
	}

	/**
	 * 弹框
	 *
	 * @param requestCode
	 *            请求编号
	 */
	@Override public void onPositiveButtonClicked(int requestCode) {
		L.tag(initTag());
		L.i("onPositiveButtonClicked() requestCode : " + requestCode);
		switch (requestCode) {
			case J2WConstants.J2W_ERROR_CODE:
				getActivity().onBackPressed();
				break;
		}
	}

	/**
	 * 弹框
	 *
	 * @param requestCode
	 *            请求编号
	 */
	@Override public void onNeutralButtonClicked(int requestCode) {
		L.tag(initTag());
		L.i("onNeutralButtonClicked() requestCode : " + requestCode);
		switch (requestCode) {
			case J2WConstants.J2W_ERROR_CODE:
				getActivity().onBackPressed();
				break;
		}
	}

	/**
	 * 弹框
	 *
	 * @param requestCode
	 *            请求编号
	 */
	@Override public void onNegativeButtonClicked(int requestCode) {
		L.tag(initTag());
		L.i("onNegativeButtonClicked() requestCode : " + requestCode);
		switch (requestCode) {
			case J2WConstants.J2W_ERROR_CODE:
				getActivity().onBackPressed();
				break;
		}
	}

	/**
	 * 进度条取消
	 *
	 * @param requestCode
	 */
	@Override public void onCancelled(int requestCode) {
		L.tag(initTag());
		L.i("onCancelled() requestCode : " + requestCode);
		switch (requestCode) {
			case J2WConstants.J2W_DIALOG_FRAGMENT_CODE:
				J2WRestAdapter j2WRestAdapter = J2WHelper.initRestAdapter();
				J2WHelper.initRestAdapter().cancel(j2WRestAdapter.getService());
				break;
		}
	}

	/**
	 * 弹框进度条
	 */
	@Override public void loading() {
		loading(false);
	}

	/**
	 * 弹框进度条
	 *
	 * @param cancel
	 */
	@Override public void loading(boolean cancel) {
		dialogFragment = (ProgressDailogFragment) ProgressDailogFragment.createBuilder().setTag(J2WConstants.J2W_DIALOG_PROGRESS).setTargetFragment(this, J2WConstants.J2W_DIALOG_FRAGMENT_CODE)
				.setCancelable(cancel).setMessage(R.string.progress_dialog_value)// 设置内容
				.showAllowingStateLoss();// 显示
	}

	/**
	 * 弹框进度条
	 *
	 * @param value
	 */
	@Override public void loading(String value) {
		loading(value, false);
	}

	/**
	 * 弹框进度条
	 *
	 * @param value
	 * @param cancel
	 */
	@Override public void loading(String value, boolean cancel) {
		dialogFragment = (ProgressDailogFragment) ProgressDailogFragment.createBuilder().setTag(J2WConstants.J2W_DIALOG_PROGRESS).setTargetFragment(this, J2WConstants.J2W_DIALOG_FRAGMENT_CODE)
				.setCancelable(cancel).setMessage(value)// 设置内容
				.showAllowingStateLoss();// 显示
	}

	/**
	 * 替换进度条文案
	 *
	 * @param value
	 */
	public void replaceLoading(String value) {
		if (dialogFragment != null) {
			dialogFragment.setArgMessage(value);
		}
	}

	/**
	 * 弹框进度条
	 */
	@Override public void loadingClose() {
		if (dialogFragment == null) {
			dialogFragment = (ProgressDailogFragment) getFManager().findFragmentByTag(J2WConstants.J2W_DIALOG_PROGRESS);
			dialogFragment.dismissAllowingStateLoss();
			getFManager().executePendingTransactions();
		} else {
			dialogFragment.dismissAllowingStateLoss();
			getFManager().executePendingTransactions();
		}
	}

	/**
	 * 初始化fragment状态布局 - 进度
	 *
	 * @return
	 */
	public int fragmentLoadingLayout() {
		return 0;
	}

	/**
	 * 初始化fragment状态布局 - 空
	 *
	 * @return
	 */
	public int fragmentEmptyLayout() {
		return 0;
	}

	/**
	 * 初始化fragment状态布局 - 错误
	 *
	 * @return
	 */
	public int fragmentErrorLayout() {
		return 0;
	}

	/**
	 * ViewPager切换 是否调用延迟加载
	 */
	@Override public final void isDelayedData() {
		if (!isAddDelayedData()) {
			L.i("Fragment-isDelayedData() 无效");
			return;
		}
		L.tag(initTag());
		L.i("Fragment-isDelayedData() return " + isDelayedData);
		if (isDelayedData) {
			isDelayedDateSuccess = true; // 延迟加载完成
			return;
		}
		// 为了只初始化一次
		isDelayedData = true;
		// 延迟加载数据
		initData(getArguments());
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
	 * 是否添加延迟加载
	 */
	@Override public boolean isAddDelayedData() {
		return false;
	}

	/**
	 * 获取延迟加载状态
	 *
	 * @return true 加载过 false 没有加载过
	 */
	@Override public boolean getDelayeDateState() {
		return isDelayedDateSuccess;
	}

	/**
	 * 删除fragment
	 */
	@Override public void removeFragment(String fragmentName) {
		int index = getFragmentManager().getBackStackEntryCount() - 1;
		while (index >= 0) {
			String name = getFragmentManager().getBackStackEntryAt(index).getName();
			if (name != null && name.equals(fragmentName)) {
				break;
			}
			getFragmentManager().popBackStack();
			index--;
		}
	}

	/**
	 * 背景是否透明
	 * 
	 * @return
	 */
	@Override public boolean isFragmentBackground() {
		return false;
	}

	/**
	 * 按钮返回键
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 */
	@Override public boolean onJ2WKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
