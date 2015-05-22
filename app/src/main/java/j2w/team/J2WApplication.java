package j2w.team;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import j2w.team.common.log.L;
import j2w.team.modules.http.J2WRestAdapter;
import j2w.team.mvp.J2WIView;
import j2w.team.mvp.common.J2WIViewCommon;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 15/1/26. 说明：使用架构必须继承
 */
public abstract class J2WApplication extends Application implements J2WIViewCommon {

	/**
	 * 日志是否打印
	 * 
	 * @return true 打印 false 不打印
	 */
	public abstract boolean isLogOpen();

	/**
	 * 获取网络适配器
	 * 
	 * @return
	 */
	public abstract J2WRestAdapter getRestAdapter();

	/**
	 * 应用程序启动首先被执行
	 */
	@Override public void onCreate() {
		super.onCreate();
		// 初始化Application
		J2WHelper.with(this);
		// 初始化网络适配器
		J2WHelper.createRestAdapter(getRestAdapter());
		// 日志初始化
		L.init(isLogOpen(), this);
	}

	@Override public void onCreate(J2WIView j2WIView, Bundle bundle) {

	}

	@Override public void onStart(J2WIView j2WIView) {

	}

	@Override public void onResume(J2WIView j2WIView) {

	}

	@Override public void onPause(J2WIView j2WIView) {

	}

	@Override public void onStop(J2WIView j2WIView) {

	}

	@Override public void onDestroy(J2WIView j2WIView) {

	}

	@Override public void onRestart(J2WIView j2WIView) {

	}

	@Override public void onFragmentCreate(J2WIView j2WIView, Bundle bundle) {

	}

	@Override public void onFragmentStart(J2WIView j2WIView) {

	}

	@Override public void onFragmentResume(J2WIView j2WIView) {

	}

	@Override public void onFragmentPause(J2WIView j2WIView) {

	}

	@Override public void onFragmentStop(J2WIView j2WIView) {

	}

	@Override public void onFragmentDestroy(J2WIView j2WIView) {

	}

	@Override public void onFragmentAttach(J2WIView j2WIView) {

	}

	@Override public void onFragmentCreateView(J2WIView j2WIView, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	}

	@Override public void onFragmentActivityCreated(J2WIView j2WIView, Bundle savedInstanceState) {

	}

	@Override public void onSaveInstanceState(J2WIView j2WIView, Bundle outState) {

	}

	@Override public void onFragmentDestroyView(J2WIView j2WIView) {

	}

	@Override public void onFragmentDetach(J2WIView j2WIView) {

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
	 * listFragment底部布局
	 *
	 * @return
	 */
	@Override public int listFragmentFooterLayout() {
		return R.layout.j2w_fragment_nomore;
	}
}
