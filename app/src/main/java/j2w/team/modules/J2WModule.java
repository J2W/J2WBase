package j2w.team.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import j2w.team.common.log.L;
import j2w.team.mvp.J2WApplication;
import j2w.team.mvp.presenter.J2WPresenter;
import j2w.team.mvp.presenter.J2WPresenterBean;

/**
 * Created by sky on 15/2/9. 匕首-BaseModule
 */

@Module(injects = { J2WApplication.class, J2WPresenter.class }, library = true) public final class J2WModule {

	private final J2WApplication app;

	public J2WModule(J2WApplication app) {
		this.app = app;
	}

	/** 注入Application **/
	@Provides @Singleton public Application provideApplication() {
        L.i("获取app");
        return app;
	}

	/** 注入J2WPresenterBean **/
	@Provides public J2WPresenterBean provideJ2WPresenterBean() {
		return new J2WPresenterBean();
	}

	/**
	 * 获取依赖注入Modlue
	 * 
	 * @param app
	 * @return
	 */
	public static Object[] list(J2WApplication app) {
		return new Object[] { new J2WModule(app) };
	}
}