package j2w.team.mvp.view.iview;

/**
 * Created by sky on 15/3/3. viewpager 接口
 */
public interface J2WViewpagerIView extends J2WIView {

	/**
	 * 初始化Viewpager头部信息。
	 */
	public void initTabsValue();

	/**
	 * 初始化Viewpager内部信息。
	 */
	public void initViewPager();

    public void setTitleCount(int position, String count);

}
