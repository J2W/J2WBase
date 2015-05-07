package j2w.team.mvp.fragment;

/**
 * Created by sky on 15/3/25.ViewPagerFragment
 */
public interface J2WIViewVPFragment extends J2WIViewFragment {

	/**
	 * ViewPager切换 是否调用延迟加载
	 */
	void isDelayedData();

	/**
	 * ViewPager切换 延迟数据初始化 - 执行一次
	 */
	void initDelayedData();

	/**
	 * ViewPager 重新执行
	 * 
	 * @param index
	 *            下标
	 */
	void onFragmentRestart(int index);

	/**
	 * ViewPager切换 更新ActionBar
	 */
	void updateActionBar();
}
