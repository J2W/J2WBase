package j2w.team.mvp.fragment;

import j2w.team.common.widget.swipeRefresh.SwipeRefreshLayout;

/**
 * Created by sky on 15/3/13.
 */
public interface J2WIViewPullListFragment extends J2WIViewListFragment {

	/**
	 * 下拉刷新标识
	 */
	int	PULL_ONREFRESH	= 0x0000001;

	/**
	 * 上拉加载标识
	 */
	int	PULL_ONLOAD		= 0x0000002;

	/**
	 * 头部刷新
	 */
	void onRefresh();

	/**
	 * 尾部刷新
	 */
	void onLoad();

	/**
	 * 设置头部进度条 - 显示和隐藏
	 * 
	 * @param bool
	 *            true 显示 false 隐藏
	 */
	void setRefreshing(boolean bool);

	/**
	 * 设置尾部进度条 - 显示和隐藏
	 * 
	 * @param bool
	 *            true 显示 false 隐藏
	 */
	void setLoading(boolean bool);

	/**
	 * 打开头部进度条
	 */
	void openPullRefreshing();

	/**
	 * 关闭头部进度条
	 */
	void closePullRefreshing();

	/**
	 * 打开尾部进度条
	 */
	void openPullLoading();

	/**
	 * 关闭尾部进度条
	 */
	void closePullLoading();

	/**
	 * 获取下拉刷新控件
	 */
	SwipeRefreshLayout getSwipeRefreshLayout();
}
