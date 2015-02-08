package j2w.team.mvp;

import android.app.Application;

import dagger.ObjectGraph;
import j2w.team.modules.J2WModule;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.common.log.L;

/**
 * Created by sky on 15/1/26. 说明：使用架构必须继承
 */
public abstract class J2WApplication extends Application {
	/** dagger 依赖注入 工具类 **/
	private ObjectGraph objectGraph;

	/**
	 * 日志是否打印
	 * 
	 * @return true 打印 false 不打印
	 */
	public abstract boolean isLogOpen();

	/**
	 * 应用程序启动首先被执行
	 */
	@Override public void onCreate() {
		super.onCreate();
		// 初始化Application
		J2WHelper.with(this);
		// 日志初始化
		L.init(isLogOpen(), this);
		// 初始化-依赖注入工具类
		buildObjectGraphAndInject();
	}

	/**
	 * dagger - 初始化工具类
	 */
	private void buildObjectGraphAndInject() {
		objectGraph = ObjectGraph.create(J2WModule.list(this));
	}

	/**
	 * dagger - 注入
	 * 
	 * @param o
	 *            当前类
	 */
	public void inject(Object o) {
		objectGraph.inject(o);
	}
}
