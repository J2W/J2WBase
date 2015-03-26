package j2w.team.mvp;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import j2w.team.mvp.model.ModelPager;

/**
 * Created by sky on 15/3/3. viewpager
 */
public interface J2WIViewViewpagerABActivity extends J2WIViewABActivity {

	/**
	 * 初始化 ItemPager
	 * 
	 * @return Item集合
	 */
	public ModelPager[] initModelPagers();

	/**
	 * 初始化 tabs样式
	 */
	public void initTabsValue();

	/**
	 * 初始化 viewpager - 设置适配器
	 */
	public void initViewPager();

	/**
	 * 初始化 ViewPager - adapter
	 *
	 * @return 适配器
	 */
	public PagerAdapter getPagerAdapter();

	/**
	 * 初始化 TabHost - item 样式
	 * 
	 * @return　 布局ID
	 */
	public int getViewPagerItemLayout();

	/**
	 * 初始化 TabHost - item 值
	 * 
	 * @param view
	 *            TabItem
	 * @param modelPager
	 *            Item对象
	 */
	public void initTab(View view, ModelPager modelPager);

	/**
	 * 设置Item样式
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

	/**
	 * 设置Item样式 -替换item
	 */
	public void replaceViewPageItem(ModelPager... modelPagers); // 替换item

	/**
	 * ViewPager 滑动事件 - 滑动过程
	 * 
	 * @param i
	 * @param v
	 * @param i2
	 */
	public void onExtraPageScrolled(int i, float v, int i2);

	/**
	 * ViewPager 滑动事件 - 滑动完成
	 * 
	 * @param view
	 * @param i
	 */
	public void onExtraPageSelected(View view, int i);

	/**
	 * ViewPager 滑动事件 - 滑动改变
	 * 
	 * @param i
	 */
	public void onExtraPageScrollStateChanged(int i);

	/**
	 * 设置下标
	 * 
	 * @param index
	 *            位置
	 * @param bool
	 *            是否开启动画
	 */
	public void setIndex(int index, boolean bool);

}
