package j2w.team.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewAnimator;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.utils.KeyboardUtils;
import j2w.team.modules.dialog.iface.IDialogListener;
import j2w.team.modules.dialog.provided.ProgressDailogFragment;
import j2w.team.modules.http.J2WRestAdapter;
import j2w.team.mvp.model.J2WConstants;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.presenter.J2WPresenterUtils;

/**
 * Created by sky on 15/2/5. actionbarActivity 视图
 */
public abstract class J2WABActivity<T extends J2WIPresenter> extends ActionBarActivity implements J2WIViewABActivity, IDialogListener, View.OnClickListener {

	/** 默认进度条 **/
	ProgressDailogFragment	dialogFragment;			// 交互弹窗

	/** 标题栏 **/
	ActionBar				actionBar;

	/** 业务逻辑对象 **/
	private T				presenter		= null;

	/**
	 * view *
	 */
	ViewAnimator			mViewAnimator;

	/**
	 * view *
	 */
	View					mContentView;

	/**
	 * 是否显示状态
	 */
	private boolean			isShowContent	= false;

	/**
	 * 获取TAG标记
	 *
	 * @return tag
	 */
	@Override public String initTag() {
		return getClass().getSimpleName();
	}

	/**
	 * 获取布局ID
	 *
	 * @return 布局ID
	 */
	@Override public int layoutId() {
		return R.layout.j2w_layout_default;
	}

	/**
	 * 初始化视图
	 */
	@Override public void initLayout() {
		L.tag(initTag());
		L.i("initLayout()");
		if (activityState()) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mContentView = inflater.inflate(R.layout.j2w_fragment_main, null, false);
			mViewAnimator = ButterKnife.findById(mContentView, android.R.id.home);
			// 加载布局-初始化
			inflater.inflate(fragmentLoadingLayout(), mViewAnimator, true);
			// 内容布局-初始化
			inflater.inflate(layoutId(), mViewAnimator, true);
			// 空布局-初始化
			inflater.inflate(fragmentEmptyLayout(), mViewAnimator, true);
			// 错误布局-初始化
			inflater.inflate(fragmentErrorLayout(), mViewAnimator, true);
			setContentView(mContentView);
		} else {
			setContentView(layoutId());
		}
	}

	/**
	 * 初始化数据
	 *
	 * @param savedInstanceState
	 *            数据
	 */
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("initData()");
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
		L.tag(initTag());
		L.i("intent2Activity(clazz) " + clazz.getSimpleName());
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
		L.tag(initTag());
		L.i("intent2Activity(clazz,bundle) " + clazz.getSimpleName());
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
		L.tag(initTag());
		L.i("intent2Activity(clazz,requestCode) " + clazz.getSimpleName());
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
		L.tag(initTag());
		L.i("intent2Activity(clazz,bundle,requestCode) " + clazz.getSimpleName());
		J2WHelper.intentTo(clazz, bundle, requestCode);
	}

	/**
	 * 获取上下文
	 *
	 * @return 上下文
	 */
	@Override public Context getContext() {
		return this;
	}

	/**
	 * 获取碎片管理器
	 *
	 * @return
	 */
	@Override public FragmentManager getFManager() {
		return getSupportFragmentManager();
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
	@Override public void commitFragment(Fragment fragment, String tag) {
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
	@Override public void commitFragment(int layoutId, Fragment fragment, String tag) {
		L.tag(initTag());
		L.i("commitFragment(Fragment fragment, String tag)");
		if (fragment != null && fragment.isAdded()) {
			L.tag(initTag());
			L.i("fragment 不能为空，或者已经被添加！");
			return;
		}
		getFManager().beginTransaction().add(layoutId, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
		if (!activityState()) {
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
		if (!activityState()) {
			getFManager().executePendingTransactions();
		}
	}

	/**
	 * 提交fragment - 压栈
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
		if (!activityState()) {
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
		if (!activityState()) {
			getFManager().executePendingTransactions();
		}
	}

	/**
	 * 销毁当前页面
	 */
	@Override public void activityFinish() {
		L.tag(initTag());
		L.i("activityFinish()");
		finish();
	}

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		J2WHelper.getInstance().onCreate(this, savedInstanceState);
		L.tag(initTag());
		L.i("onCreate()");
		/** 初始化视图 **/
		initLayout();

		/** 初始化标题栏 **/
		initActionBar();
		/** 初始化所有组建 **/
		ButterKnife.inject(this);
		/** 添加到堆栈 **/
		J2WHelper.getScreenHelper().pushActivity(this);
		/** 初始化视图组建 **/
		initData(savedInstanceState);
	}

	@Override protected void onStart() {
		super.onStart();
		J2WHelper.getInstance().onStart(this);
		L.tag(initTag());
		L.i("onStart()");
	}

	@Override protected void onResume() {
		super.onResume();
		J2WHelper.getInstance().onResume(this);
		L.tag(initTag());
		L.i("onResume()");
		/** 判断EventBus 然后注册 **/
		if (isOpenEventBus()) {
			if (!EventBus.getDefault().isRegistered(this)) {
				EventBus.getDefault().register(this);
			}
		}
	}

	@Override protected void onPause() {
		super.onPause();
		J2WHelper.getInstance().onPause(this);
		L.tag(initTag());
		L.i("onPause()");
	}

	@Override protected void onRestart() {
		super.onRestart();
		J2WHelper.getInstance().onRestart(this);
		L.tag(initTag());
		L.i("onRestart()");
	}

	@Override protected void onStop() {
		super.onStop();
		J2WHelper.getInstance().onStop(this);
		L.tag(initTag());
		L.i("onStop()");
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		J2WHelper.getInstance().onDestroy(this);
		L.tag(initTag());
		L.i("onDestroy()");
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
		/** 从堆栈里移除 **/
		J2WHelper.getScreenHelper().popActivity(this);
	}

	/**
	 * 初始化actionbar
	 */
	@Override public void initActionBar() {
		L.tag(initTag());
		L.i("initActionBar()");
		actionBar = getSupportActionBar();
		actionBar.setCustomView(actionBarLayoutID()); // 设置actionbar布局ID
		actionBar.setDisplayShowCustomEnabled(true); // 使自定义的普通View能在title栏显示
		actionBar.setDisplayShowHomeEnabled(false);// 使左上角图标是否显示
		actionBar.setDisplayHomeAsUpEnabled(false);// 是否显示返回图标
		actionBar.setDisplayShowTitleEnabled(false);// 隐藏/显示Tittle true 显示 false
	}

	/**
	 * 设置标题栏
	 *
	 * @param value
	 *            数据
	 * @param code
	 *            标记
	 */
	@Override public void setActionbarTitle(Object value, int code) {
		L.tag(initTag());
		L.i("setActionbarTitle() " + value + " code " + code);
	}

	/**
	 * 初始化fragment状态布局 - 进度
	 *
	 * @return
	 */
	@Override public int fragmentLoadingLayout() {
		return J2WHelper.getInstance().fragmentLoadingLayout();
	}

	/**
	 * 初始化fragment状态布局 - 空
	 *
	 * @return
	 */
	@Override public int fragmentEmptyLayout() {
		return J2WHelper.getInstance().fragmentEmptyLayout();
	}

	/**
	 * 初始化fragment状态布局 - 错误
	 *
	 * @return
	 */
	@Override public int fragmentErrorLayout() {
		return J2WHelper.getInstance().fragmentErrorLayout();
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
				onBackPressed();
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
				onBackPressed();
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
				onBackPressed();
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
			case J2WConstants.J2W_DIALOG_CODE:
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
		dialogFragment = (ProgressDailogFragment) ProgressDailogFragment.createBuilder().setTag(J2WConstants.J2W_DIALOG_PROGRESS).setRequestCode(J2WConstants.J2W_DIALOG_CODE).setCancelable(cancel)
				.setMessage(R.string.progress_dialog_value)// 设置内容
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
		dialogFragment = (ProgressDailogFragment) ProgressDailogFragment.createBuilder().setTag(J2WConstants.J2W_DIALOG_PROGRESS).setRequestCode(J2WConstants.J2W_DIALOG_CODE).setCancelable(cancel)
				.setMessage(value)// 设置内容
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
	 * 是否添加Activity状态布局
	 *
	 * @return true 打开 false 关闭
	 */
	@Override public boolean activityState() {
		return false;
	}

	@Override public void showLoading() {
		L.tag(initTag());
		L.i("Fragment-loading()");
		show(0);
		isShowContent = false;
	}

	@Override public void showContent() {
		L.tag(initTag());
		L.i("Fragment-content()");
		show(1);
		isShowContent = true;
	}

	/**
	 * 判断是否显示内容
	 */
	public boolean isShowContent() {
		return isShowContent;
	}

	@Override public void showEmpty() {
		L.tag(initTag());
		L.i("Fragment-empty()");
		show(2);
		isShowContent = false;
	}

	@Override public void showError() {
		L.tag(initTag());
		L.i("Fragment-error()");
		show(3);
		isShowContent = false;
	}

	/**
	 * 显示 * @param showState 0，1，2，3
	 */
	private final void show(int showState) {
		L.tag(initTag());
		L.i("show() mViewAnimator :" + mViewAnimator);
		if (!activityState()) {
			L.tag(initTag());
			L.i("当前activity没有打开状态模式! activityState() = false");
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
		if (showState == 3) {
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
		initData(getIntent().getExtras());
	}

	/**
	 * 屏幕点击事件 - 关闭键盘
	 * 
	 * @param ev
	 * @return
	 */
	@Override public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();
			if (KeyboardUtils.isShouldHideInput(v, ev)) {
				KeyboardUtils.hideSoftInput(J2WHelper.getScreenHelper().currentActivity());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

}
