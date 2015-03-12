package j2w.team.mvp.view.iview;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import j2w.team.mvp.model.ViewPagerModel;

/**
 * Created by sky on 15/3/3. viewpager 接口
 */
public interface J2WViewpagerIView extends J2WActionBarIView {

	/**
	 * 初始化Viewpager
	 */
	public void initTabsValue();

	public void initViewPager();

	public int getViewPagerItemLayout();

	public void initTab(View view, ViewPagerModel viewPagerModel);

	public PagerAdapter getPagerAdapter();

	/**
	 * 设置Item信息
	 */
	public int getTabsBackgroundResource(); // 背景颜色

	public boolean getTabsShouldExpand();// 设置Tab是自动填充满屏幕的

	public int getTabsDividerColor();// 设置Tab的分割线是透明的

	public int getTabsTitleColor(); // 设置Tab的文字颜色

	public int getTabsTitleSize();// 设置Tab标题文字的大小

	public int getTabsSelectedTitleColor();// 设置选中Tab文字的颜色

	public int getTabsIndicatorColor();// 设置Tab Indicator 指示灯的颜色

	public int getTabsOnClickTitleColor();// 设置点击颜色

	public int getTabsUnderlineColor();// 设置Tab底部线的颜色

	public int getTabsUnderlineHeight();// 设置Tab底部线的高度

	public void replaceViewPageItem(ViewPagerModel... viewPagerModels); // 替换item

	/** 初始化事件 **/
	public void onExtraPageScrolled(int i, float v, int i2);

	public void onExtraPageSelected(View view, int i);

	public void onExtraPageScrollStateChanged(int i);

	/**
	 * 设置自定义适配器
	 * 
	 * @return
	 */

	public void setTitleCount(int position, String count);

	/**
	 * 设置下标
	 * 
	 * @param index
	 *            位置
	 * @param bool
	 *            是否开启动画
	 */
	public void setIndex(int index, boolean bool);

	/** 获取TabHost内容 **/
	public ViewPagerModel[] getViewPagerModels();

}
