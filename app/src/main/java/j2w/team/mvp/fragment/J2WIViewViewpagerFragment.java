package j2w.team.mvp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import j2w.team.mvp.model.ModelPager;

/**
 * @创建人 sky
 * @创建时间 15/6/26 下午9:54
 * @类描述 一句话说明这个类是干什么的
 */
public interface J2WIViewViewpagerFragment extends J2WIViewFragment {
    /**
     * 初始化 ItemPager
     *
     * @return Item集合
     */
    ModelPager[] initModelPagers();

    /**
     * 初始化 tabs样式
     */
    void initTabsValue();

    /**
     * 初始化 viewpager - 设置适配器
     */
    void initViewPager();

    /**
     * 初始化 viewpager - 设置适配器
     *
     * @param modelPagers
     *            集合
     * @param offScreenPageLimit
     *            预留数量
     */
    void initViewPager(ModelPager[] modelPagers, int offScreenPageLimit);

    /**
     * 初始化 ViewPager - adapter
     *
     * @return 适配器
     */
    PagerAdapter getPagerAdapter();

    /**
     * 获取当前fragment
     *
     * @return
     */
    Fragment getCurrentFragment();

    /**
     * 初始化 TabHost - item 样式
     *
     * @return　 布局ID
     */
    int getViewPagerItemLayout();

    /**
     * 初始化 TabHost - item 值
     *
     * @param view
     *            TabItem
     * @param modelPager
     *            Item对象
     */
    void initTab(View view, ModelPager modelPager);

    /**
     * 设置Item样式
     */
    int getTabsBackgroundResource(); // 背景颜色

    boolean getTabsShouldExpand();// 设置Tab是自动填充满屏幕的

    int getTabsDividerColor();// 设置Tab的分割线是透明的

    int getTabsTitleColor(); // 设置Tab的文字颜色

    int getTabsTitleSize();// 设置Tab标题文字的大小

    int getTabsSelectedTitleColor();// 设置选中Tab文字的颜色

    int getTabsIndicatorSize();// 设置Tab Indicator 指示灯高度

    int getTabsIndicatorColor();// 设置Tab Indicator 指示灯的颜色

    int getTabsOnClickTitleColor();// 设置点击颜色

    int getTabsUnderlineColor();// 设置Tab底部线的颜色

    int getTabsUnderlineHeight();// 设置Tab底部线的高度

    int getTabWidth();// 设置每个Tab宽度

    /**
     * 设置Item样式 -替换item
     */
    void replaceViewPageItem(ModelPager... modelPagers); // 替换item

    /**
     * ViewPager 滑动事件 - 滑动过程
     *
     * @param left
     *            左视图
     * @param right
     *            右视图
     * @param v
     *            数值
     * @param i2
     *            偏移量
     */
    void onExtraPageScrolled(View left, View right, float v, int i2);

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
    void onExtraPageSelected(View current, View old, int currentPosition, int oldPosition);

    /**
     * ViewPager 滑动事件 - 滑动改变
     *
     * @param i
     */
    void onExtraPageScrollStateChanged(int i);

    /**
     * 获取TabsView
     *
     * @param position
     *            下标
     */
    View getTabsView(int position);

    /**
     * 设置下标
     *
     * @param index
     *            位置
     * @param bool
     *            是否开启动画
     */
    void setIndex(int index, boolean bool);

}