package j2w.team.mvp.fragment;

/**
 * Created by sky on 15/3/13.
 */
public interface J2WIViewPullListFragment extends J2WIViewListFragment {

	/**
	 * 头部刷新
	 */
	public void onRefresh();

	/**
	 * 尾部刷新
	 */
	public void onLoad();

	/**
	 * 设置头部进度条 - 显示和隐藏
	 * 
	 * @param bool
	 *            true 显示 false 隐藏
	 */
	public void setRefreshing(boolean bool);

	/**
	 * 设置尾部进度条 - 显示和隐藏
	 * 
	 * @param bool
	 *            true 显示 false 隐藏
	 */
	public void setLoading(boolean bool);

	/**
	 * 关闭头部进度条
	 */
	public void notRefreshing();

	/**
	 * 关闭尾部进度条
	 */
	public void notLoading();
}