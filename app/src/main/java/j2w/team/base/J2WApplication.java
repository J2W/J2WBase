package j2w.team.base;

import android.app.Application;

import j2w.team.common.log.L;

/**
 * Created by sky on 15/1/26. 说明：使用架构必须继承
 */
public abstract class J2WApplication extends Application {

	/**
	 * 日志是否打印
	 * 
	 * @return true 打印 false 不打印
	 */
	abstract boolean isLogOpen();

	/**
	 * 应用程序启动首先被执行
	 */
	@Override public void onCreate() {
		super.onCreate();
		// 初始化Application
		J2WHelper.with(this);
		// 日志初始化
		L.init(isLogOpen(),this);
	}
}
