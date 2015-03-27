package j2w.team.mvp.fragment;

/**
 * Created by sky on 15/3/25.ViewPagerFragment
 */
public interface J2WIViewVPFragment extends J2WIViewFragment {

	/**
	 * 是否添加Fragment状态布局
	 * 
	 * @return true 打开 false 关闭
	 */
	public boolean isAddFragmentState();

	/**
	 * ViewPager切换 是否调用延迟加载
	 */
	public void isDelayedData();

	/**
	 * ViewPager切换 延迟数据初始化 - 执行一次
	 */
	public void initDelayedData();

	/**
	 * ViewPager 重新执行
	 * 
	 * @param index
	 *            下标
	 */
	public void onFragmentRestart(int index);

	/**
	 * ViewPager切换 更新ActionBar
	 */
	public void updateActionBar();
}