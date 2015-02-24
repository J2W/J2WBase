package j2w.team.mvp.presenter;

import com.squareup.picasso.PicassoTools;

import j2w.team.common.utils.looper.SynchronousExecutor;
import j2w.team.modules.http.J2WRestAdapter;
import j2w.team.modules.screen.J2WIScreenManager;
import j2w.team.modules.screen.J2WScreenManager;
import j2w.team.modules.threadpool.J2WThreadPoolManager;
import j2w.team.mvp.J2WApplication;

/**
 * Created by sky on 15/1/28. helper 管理
 */
public class J2WHelper {

	/**
	 * 单例模式-握有application
	 */
	private volatile static J2WApplication mJ2WApplication = null;

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
	 * 单例模式-网络适配器
	 */
	private volatile static J2WRestAdapter mJ2WRestAdapter = null;

	/**
	 * 单例模式-初始化J2WRestAdapter网络适配器
	 *
	 * @param j2WRestAdapter
	 *            网络适配器
	 */
	public static void initJ2WRestAdatper(J2WRestAdapter j2WRestAdapter) {
		if (j2WRestAdapter == null) {
			synchronized (J2WHelper.class) {
				if (mJ2WRestAdapter == null) {
					mJ2WRestAdapter = j2WRestAdapter;
				}
			}
		}
	}

	/**
	 * 网络适配器
	 * 
	 * @return 适配器
	 */
	public static final J2WRestAdapter getJ2WRestAdapter() {
		return mJ2WRestAdapter;
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
}
