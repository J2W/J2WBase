package j2w.team.mvp.presenter;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import java.io.IOException;

import j2w.team.common.log.L;
import j2w.team.common.utils.looper.SynchronousExecutor;
import j2w.team.modules.screen.J2WIScreenManager;
import j2w.team.modules.screen.J2WScreenManager;
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
	 * fragmentactivity管理器
	 *
	 * @return 管理器
	 */
	public static final J2WIScreenManager getScreenHelper() {
		return J2WScreenManager.getInstance();
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
	 * 图片下载管理器
	 * 
	 * @return picasso
	 */
	public static Picasso getPicasso() {
		return PicassoTools.with();
	}

	/**
	 * 清空图片下载管理器缓存
	 */
	/**
	 * 清空图片下载管理器缓存
	 * 
	 * @param flg
	 *            true 清空内存和磁盘缓存 false 只清空内存缓存
	 */
	public static void clearPicassoCache(boolean flg) {
		try {
			if (flg) {
				PicassoTools.removeDiskCache();
			} else {
				PicassoTools.clearCache();
			}
		} catch (IOException e) {
			L.e("删除缓存错误");
		}
	}

	public static void removePicassoCache(String key) {

	}
}
