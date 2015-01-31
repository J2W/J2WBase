package j2w.team.base;

import j2w.team.modules.screen.J2WIScreenManager;
import j2w.team.modules.screen.J2WScreenManager;

/**
 * Created by sky on 15/1/28. helper 管理
 */
public class J2WHelper {

	/**
	 * 单例模式-握有application
	 */
	private volatile static J2WApplication mJ2WApplication = null;

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


}
