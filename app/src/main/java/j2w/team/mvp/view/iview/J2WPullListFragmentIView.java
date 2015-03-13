package j2w.team.mvp.view.iview;

import j2w.team.common.widget.swipeRefresh.SwipeRefreshLayout;

/**
 * Created by sky on 15/3/13.
 */
public interface J2WPullListFragmentIView extends J2WListFragmentIView {

	/** 下拉刷新 **/
	public void onRefresh();

	/** 上拉加载 **/
	public void onLoad();

    public void setLoadingColor(int colorRes1, int colorRes2, int colorRes3, int colorRes4);
}
