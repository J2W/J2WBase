package j2w.team.mvp.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import j2w.team.mvp.J2WIView;

/**
 * @创建人 sky
 * @创建时间 15/5/14 下午5:56
 * @类描述 公共视图接口
 */
public interface J2WIViewCommon {

	/**
	 * 公共
	 */
	void onSaveInstanceState(J2WIView j2WIView, Bundle outState);

	/**
	 * activity
	 */
	void onCreate(J2WIView j2WIView, Bundle bundle);

	void onStart(J2WIView j2WIView);

	void onResume(J2WIView j2WIView);

	void onPause(J2WIView j2WIView);

	void onStop(J2WIView j2WIView);

	void onDestroy(J2WIView j2WIView);

	void onRestart(J2WIView j2WIView);

	/**
	 * fragment
	 */
	void onFragmentCreate(J2WIView j2WIView, Bundle bundle);

	void onFragmentStart(J2WIView j2WIView);

	void onFragmentResume(J2WIView j2WIView);

	void onFragmentPause(J2WIView j2WIView);

	void onFragmentStop(J2WIView j2WIView);

	void onFragmentDestroy(J2WIView j2WIView);

	void onFragmentAttach(J2WIView j2WIView);

	void onFragmentCreateView(J2WIView j2WIView, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	void onFragmentActivityCreated(J2WIView j2WIView, Bundle savedInstanceState);

	void onFragmentDestroyView(J2WIView j2WIView);

	void onFragmentDetach(J2WIView j2WIView);

	/**
	 * 初始化fragment状态布局 - 进度
	 *
	 * @return
	 */
	int fragmentLoadingLayout();

	/**
	 * 初始化fragment状态布局 - 空
	 *
	 * @return
	 */
	int fragmentEmptyLayout();

	/**
	 * 初始化fragment状态布局 - 错误
	 *
	 * @return
	 */
	int fragmentErrorLayout();

	/**
	 * listFragment底部布局
	 * 
	 * @return
	 */
	int listFragmentFooterLayout();
}
