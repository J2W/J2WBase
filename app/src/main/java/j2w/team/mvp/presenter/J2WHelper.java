package j2w.team.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.squareup.picasso.PicassoTools;

import de.greenrobot.event.EventBus;
import j2w.team.J2WApplication;
import j2w.team.common.utils.looper.SynchronousExecutor;
import j2w.team.modules.dialog.J2WDialogFragment;
import j2w.team.modules.download.J2WDownloadManager;
import j2w.team.modules.http.J2WRestAdapter;
import j2w.team.modules.screen.J2WIScreenManager;
import j2w.team.modules.screen.J2WScreenManager;
import j2w.team.modules.threadpool.J2WThreadPoolManager;

/**
 * Created by sky on 15/1/28. helper 管理
 */
public class J2WHelper {

	/**
	 * 单例模式-握有application
	 */
	private volatile static J2WApplication	mJ2WApplication	= null;

	/**
	 * 单例模式 - 获取application
	 * 
	 * @return
	 */
	public static J2WApplication getInstance() {
		return mJ2WApplication;
	}

	/**
	 * 单例模式-初始化application
	 *
	 * @param j2WApplication
	 *            系统上下文
	 */
	public static void with(J2WApplication j2WApplication) {
		if (mJ2WApplication == null) {
			synchronized (J2WHelper.class) {
				if (mJ2WApplication == null) {
					mJ2WApplication = j2WApplication;
				}
			}
		}
	}

	/**
	 * 单例模式-握有 网络适配器
	 */
	private volatile static J2WRestAdapter	mJ2WRestAdapter;

	/**
	 * 获取 网络适配器
	 * 
	 * @return
	 */
	public static J2WRestAdapter initRestAdapter() {
		return mJ2WRestAdapter;
	}

	/**
	 * 单例模式-初始化网络适配器
	 *
	 * @param j2WRestAdapter
	 *            网络适配器
	 */
	public static void createRestAdapter(J2WRestAdapter j2WRestAdapter) {
		if (mJ2WRestAdapter == null) {
			synchronized (J2WHelper.class) {
				if (mJ2WRestAdapter == null) {
					mJ2WRestAdapter = j2WRestAdapter;
				}
			}
		}
	}

	/** 生成器 **/
	private static final J2WRestAdapter.Builder	j2WRestAdapterBuilder	= new J2WRestAdapter.Builder();

	/**
	 * 网络适配器-生成器
	 * 
	 * @return
	 */
	public static final J2WRestAdapter.Builder getJ2WRestBuilder() {
		return j2WRestAdapterBuilder;
	}

	/**
	 * fragmentactivity管理器
	 *
	 * @return 管理器
	 */
	public static final J2WIScreenManager getScreenHelper() {
		return J2WScreenManager.getInstance();
	}

	/**
	 * J2WThreadPoolManager 线程池管理器
	 */

	public static final J2WThreadPoolManager getThreadPoolHelper() {
		return J2WThreadPoolManager.getInstance();
	}

	/**
	 * MainLooper 主线程中执行
	 * 
	 * @return
	 */
	public static final SynchronousExecutor getMainLooper() {
		return SynchronousExecutor.getInstance();
	}

	/**
	 * 下载器工具
	 * 
	 * @return
	 */
	public static final J2WDownloadManager getDownloader() {
		return J2WDownloadManager.getInstance();
	}

	/**
	 * Picasso工具
	 * 
	 * @return picasso
	 */
	public static PicassoTools getPicassoHelper() {
		return PicassoTools.getInstance();
	}

	/**
	 * 提交Event
	 * 
	 * @param object
	 */
	public static void eventPost(final Object object) {
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			getMainLooper().execute(new Runnable() {

				@Override public void run() {
					EventBus.getDefault().post(object);
				}
			});
		} else {
			EventBus.getDefault().post(object);
		}

	}

	/**
	 * 显示FragmentDilaog 弹框
	 */
	public static void showDialog(Class<? extends J2WDialogFragment> mClass) {
		final J2WDialogFragment fragment = (J2WDialogFragment) Fragment.instantiate(getScreenHelper().currentActivity(), mClass.getName(), null);
		showDialog(fragment);
	}

	/**
	 * 显示FragmentDilaog 弹框
	 */
	public static void showDialog(J2WDialogFragment j2WDialogFragment) {
		getScreenHelper().currentActivity().getSupportFragmentManager().beginTransaction().add(j2WDialogFragment, j2WDialogFragment.getClass().getSimpleName()).commitAllowingStateLoss();
	}

	/**
	 * Activity 跳转工具
	 * 
	 * @param clazz
	 */
	public static final void intentTo(Class clazz) {
		Intent intent = new Intent();
		intent.setClass(J2WHelper.getScreenHelper().currentActivity(), clazz);
		J2WHelper.getScreenHelper().currentActivity().startActivity(intent);
	}

	public static final void intentTo(Class clazz, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(J2WHelper.getScreenHelper().currentActivity(), clazz);
		intent.putExtras(bundle);
		J2WHelper.getScreenHelper().currentActivity().startActivity(intent);
	}

	public static final void intentTo(Class clazz, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(J2WHelper.getScreenHelper().currentActivity(), clazz);
		J2WHelper.getScreenHelper().currentActivity().startActivityForResult(intent, requestCode);
	}

	public static final void intentTo(Class clazz, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(J2WHelper.getScreenHelper().currentActivity(), clazz);
		intent.putExtras(bundle);
		J2WHelper.getScreenHelper().currentActivity().startActivityForResult(intent, requestCode);
	}

	/**
	 * Fragment 跳转工具
	 *
	 */
	public static final void commitFragment(Fragment fragment) {
		commitFragment(fragment, fragment.getClass().getSimpleName());
	}

	public static final void commitFragment(Fragment fragment, String tag) {
		FragmentManager fragmentManager = J2WHelper.getScreenHelper().currentActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().add(android.R.id.custom, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
	}

	public static final void commitFragment(int layoutId, Fragment fragment) {
		commitFragment(layoutId, fragment, fragment.getClass().getSimpleName());
	}

	public static final void commitFragment(int layoutId, Fragment fragment, String tag) {
		FragmentManager fragmentManager = J2WHelper.getScreenHelper().currentActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().add(layoutId, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
	}

	public static final void commitBackStackFragment(Fragment fragment) {
		commitBackStackFragment(fragment, fragment.getClass().getSimpleName());
	}

	public static final void commitBackStackFragment(Fragment fragment, String tag) {
		FragmentManager fragmentManager = J2WHelper.getScreenHelper().currentActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().add(android.R.id.custom, fragment, tag).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
	}

	public static final void commitBackStackFragment(int layoutId, Fragment fragment) {
		commitBackStackFragment(layoutId, fragment, fragment.getClass().getSimpleName());
	}

	public static final void commitBackStackFragment(int layoutId, Fragment fragment, String tag) {
		FragmentManager fragmentManager = J2WHelper.getScreenHelper().currentActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction().add(layoutId, fragment, tag).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
	}

}
