package j2w.team.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import j2w.team.mvp.model.ModelPager;

/**
 * Created by sky on 15/3/3. viewpager
 */
public interface J2WIViewViewpagerABActivity extends J2WIView {

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
	 * 获取当前fragment
	 * 
	 * @return
	 */
	public Fragment getCurrentFragment();

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

	public int getTabsIndicatorSize();// 设置Tab Indicator 指示灯高度

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
	 * @param current
	 *            当前
	 * @param old
	 *            过去
	 * @param currentPosition
	 *            当前坐标
	 * @param oldPosition
	 *            过去坐标
	 * @param v
	 * @param i2
	 */
	public void onExtraPageScrolled(View current, View old, int currentPosition, int oldPosition, float v, int i2);

	/**
	 * ViewPager 滑动事件 - 滑动完成
	 * 
	 * @param current
	 *            当前
	 * @param old
	 *            过去
	 * @param currentPosition
	 *            当前坐标
	 * @param oldPosition
	 *            过去坐标
	 */
	public void onExtraPageSelected(View current, View old, int currentPosition, int oldPosition);

	/**
	 * ViewPager 滑动事件 - 滑动改变
	 * 
	 * @param i
	 */
	public void onExtraPageScrollStateChanged(int i);

	/**
	 * 获取TabsView
	 * 
	 * @param position
	 *            下标
	 */
	public View getTabsView(int position);

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
