package j2w.team.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.picasso.PicassoTools;

import de.greenrobot.event.EventBus;
import j2w.team.J2WApplication;
import j2w.team.common.utils.looper.SynchronousExecutor;
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
	public static void eventPost(Object object) {
		EventBus.getDefault().post(object);
	}

	/**
	 * 跳转工具
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
}
