package j2w.team.mvp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import j2w.team.R;
import j2w.team.modules.dialog.iface.IDialogCancelListener;
import j2w.team.modules.dialog.iface.IDialogListener;
import j2w.team.modules.dialog.provided.ProgressDailogFragment;
import j2w.team.mvp.model.J2WConstants;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.presenter.J2WPresenterUtils;

/**
 * Created by sky on 15/2/5. actionbarActivity 视图
 */
public abstract class J2WABActivity<T extends J2WIPresenter> extends ActionBarActivity implements J2WIViewABActivity, IDialogListener {

	/** 默认进度条 **/
	DialogFragment	dialogFragment;	// 交互弹窗

	/** 标题栏 **/
	ActionBar		actionBar;

	/** 业务逻辑对象 **/
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
		setContentView(layoutId());
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
	public void commitFragment(Fragment fragment, String tag) {
		L.tag(initTag());
		L.i("commitFragment(Fragment fragment, String tag)");
		if (fragment != null && fragment.isAdded()) {
			L.tag(initTag());
			L.i("fragment 不能为空，或者已经被添加！");
			return;
		}
		getFManager().beginTransaction().add(android.R.id.custom, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
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
		L.i("commitFragment(int layoutId,Fragment fragment, String tag)");
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
		getFManager().beginTransaction().add(layoutId, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
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
	}

	/**
	 * 销毁当前页面
	 */
	@Override public void activityFinish() {
		L.tag(initTag());
		L.i("activityFinish()");
		finish();
	}

	/**
	 * 是否固定竖屏
	 */
	@Override public boolean isFixedVerticalScreen() {
		return true;
	}

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 是否固定竖屏 **/
		if (isFixedVerticalScreen()) {
			/** 竖屏显示 **/
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
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
		L.tag(initTag());
		L.i("onStart()");
	}

	@Override protected void onResume() {
		super.onResume();
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
		L.tag(initTag());
		L.i("onPause()");
	}

	@Override protected void onRestart() {
		super.onRestart();
		L.tag(initTag());
		L.i("onRestart()");
	}

	@Override protected void onStop() {
		super.onStop();
		L.tag(initTag());
		L.i("onStop()");
	}

	@Override protected void onDestroy() {
		super.onDestroy();
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
		return R.layout.j2w_fragment_loading;
	}

	/**
	 * 初始化fragment状态布局 - 空
	 *
	 * @return
	 */
	@Override public int fragmentEmptyLayout() {
		return R.layout.j2w_fragment_empty;
	}

	/**
	 * 初始化fragment状态布局 - 错误
	 *
	 * @return
	 */
	@Override public int fragmentErrorLayout() {
		return R.layout.j2w_fragment_error;
	}

	/**
	 * 弹框
	 * 
	 * @param requestCode
	 *            请求编号
	 */
	@Override public void onPositiveButtonClicked(int requestCode) {
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
		switch (requestCode) {
			case J2WConstants.J2W_ERROR_CODE:
				onBackPressed();
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
        dialogFragment = ProgressDailogFragment.createBuilder().setRequestCode(J2WConstants.J2W_DIALOG_CODE).setCancelable(cancel).setMessage("正在加载...")// 设置内容
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
        dialogFragment = ProgressDailogFragment.createBuilder().setRequestCode(J2WConstants.J2W_DIALOG_CODE).setCancelable(cancel).setMessage(value)// 设置内容
                .showAllowingStateLoss();// 显示
    }

	/**
	 * 弹框进度条
	 */
	@Override public void loadingClose() {
        if(dialogFragment != null){
            dialogFragment.dismissAllowingStateLoss();
        }
	}

}
