package j2w.team.mvp.view.iview;

import android.view.View;

/**
 * Created by sky on 15/2/5. fragment 视图接口
 */
public interface J2WFragmentIView extends J2WIView {

	/** 获取主视图 **/
	public View getContentView();

	/** ViewPager切换 延迟初始化 **/
	public void isDelayedData();

	public void initDelayedData();

    public void onViewPagerFragmentRestart(int index);

	/** ViewPager切换 更新ActionBar **/
	public void updateActionBar();

	/** 初始化布局 **/
	public int initLoadingLayout();

	public int initEmptyLayout();

	public int initErrorLayout();

	/** 布局切换 **/
	public void showLoading();

	public void showContent();

	public void showEmpty();

	public void showError();

	/** 防止事件穿透开关 **/
	public boolean isTouch();
}
