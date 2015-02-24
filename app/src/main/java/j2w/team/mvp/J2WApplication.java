package j2w.team.mvp;

import android.app.Application;

import j2w.team.modules.http.J2WRestAdapter;
import j2w.team.mvp.presenter.J2WHelper;
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
	public abstract boolean isLogOpen();

	/**
	 * 初始化网络适配器
	 * 
	 * @return 网络适配器
	 */
	public abstract J2WRestAdapter initRestAdapter();

	/**
	 * 应用程序启动首先被执行
	 */
	@Override public void onCreate() {
		super.onCreate();
		// 初始化Application
		J2WHelper.with(this);
		// 初始化网络适配器
		J2WHelper.initJ2WRestAdatper(initRestAdapter());
		// 日志初始化
		L.init(isLogOpen(), this);
	}
}
