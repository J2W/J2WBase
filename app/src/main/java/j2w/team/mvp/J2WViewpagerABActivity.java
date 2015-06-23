package j2w.team.mvp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import org.apache.commons.lang.StringUtils;

import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.widget.J2WViewPager;
import j2w.team.common.widget.PagerSlidingTabStrip;
import j2w.team.mvp.adapter.J2WVPDefaultPagerAdapter;
import j2w.team.mvp.fragment.J2WFragment;
import j2w.team.mvp.model.ModelPager;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/3/3.ViewPager
 */
public abstract class J2WViewpagerABActivity<T extends J2WIPresenter> extends J2WABActivity<T> implements J2WIViewViewpagerABActivity {

	/**
	 * ViewPager 头部 *
	 */
	protected PagerSlidingTabStrip	tabs;

	/**
	 * Viewpager 内部*
	 */
	protected J2WViewPager			pager;

	/**
	 * 适配器
	 */
	private PagerAdapter			adapter;

	/**
	 * 获取布局ID
	 *
	 * @return 布局ID
	 */
	@Override public int layoutId() {
		return R.layout.j2w_activity_viewpager;
	}

	/**
	 * 初始化视图
	 */
	@Override public void initLayout() {
		super.initLayout();
		tabs = (PagerSlidingTabStrip) findViewById(android.R.id.tabs);
		pager = (J2WViewPager) findViewById(R.id.pager);
		// 设置Viewpager头部
		initTabsValue();
		// 设置Viewpager内部
		initViewPager();
	}

	/**
	 * 是否添加Activity状态布局
	 *
	 * @return true 打开 false 关闭
	 */
	@Override public final boolean activityState() {
		return super.activityState();
	}

	/**
	 * 初始化 tabs样式
	 */
	@Override public final void initTabsValue() {
		L.tag(initTag());
		L.i("ViewPagerActivity-initTabsValue() tabs :" + tabs);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(getTabsShouldExpand());
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(getTabsDividerColor());
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getTabsUnderlineHeight(), dm));
		// 设置Tab底部线的颜色
		tabs.setUnderlineColor(getTabsUnderlineColor());
		// 设置Tab 指示灯的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getTabsIndicatorSize(), dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, getTabsTitleSize(), dm));
		// 设置Tab Indicator 指示灯的颜色
		tabs.setIndicatorColor(getTabsIndicatorColor());
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(getTabsSelectedTitleColor());
		// 设置Tab文字颜色
		tabs.setTextColor(getTabsTitleColor());
		// 取消点击Tab时的背景色
		tabs.setTabBackground(getTabsOnClickTitleColor());
		// 设置背景颜色
		tabs.setBackgroundResource(getTabsBackgroundResource());
	}

	/**
	 * 初始化 viewpager - 设置适配器
	 */
	@Override public void initViewPager() {
		initViewPager(initModelPagers(), 4);
	}

	/**
	 * 初始化 viewpager - 设置适配器
	 */
	@Override public void initViewPager(ModelPager[] modelPagers, int offScreenPageLimit) {
		L.tag(initTag());
		L.i("ViewPagerActivity-initViewPager()");
		if (modelPagers == null) {
			return;
		}
		// 设置适配器
		adapter = getPagerAdapter();
		((J2WVPDefaultPagerAdapter) adapter).setModelPagers(modelPagers);
		pager.setAdapter(adapter);
		// 间隔距离
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		// 设置距离
		pager.setPageMargin(pageMargin);
		// 预留数量
		pager.setOffscreenPageLimit(offScreenPageLimit);
		// 设置给头部
		tabs.setViewPager(pager);
	}

	/**
	 * 初始化 TabHost - item 样式
	 *
	 * @return　 布局ID
	 */
	@Override public int getViewPagerItemLayout() {
		return 0;
	}

	/**
	 * 初始化 TabHost - item 值
	 *
	 * @param view
	 *            TabItem
	 * @param modelPager
	 *            Item对象
	 */
	@Override public void initTab(View view, ModelPager modelPager) {
		L.tag(initTag());
		L.i("ViewPagerActivity-initTabsItem()");
	}

	/**
	 * 初始化 ViewPager - adapter
	 *
	 * @return 适配器
	 */
	@Override public PagerAdapter getPagerAdapter() {
		return new J2WVPDefaultPagerAdapter(initTag(), getSupportFragmentManager(), tabs, pager, this);
	}

	/**
	 * 获取当前fragment
	 *
	 * @return
	 */
	public Fragment getCurrentFragment() {
		L.tag(initTag());
		L.i("ViewPagerActivity-getCurrentFragment()");
		return ((J2WVPDefaultPagerAdapter) adapter).getViewPagerDatas()[pager.getCurrentItem()].fragment;
	}

	/**
	 * 设置Item样式 -背景颜色
	 */
	@Override public int getTabsBackgroundResource() {
		return android.R.color.transparent;
	}

	/**
	 * 设置Item样式 -设置Tab是自动填充满屏幕的
	 */
	@Override public boolean getTabsShouldExpand() {
		return true;
	}

	/**
	 * 设置Item样式 -设置Tab的分割线是透明的
	 */
	@Override public int getTabsDividerColor() {
		return Color.TRANSPARENT;
	}

	/**
	 * 设置Item样式 -设置Tab的文字颜色
	 */
	@Override public int getTabsTitleColor() {
		return Color.parseColor("#FF666666");
	}

	/**
	 * 设置Item样式 -设置Tab标题文字的大小
	 */
	@Override public int getTabsTitleSize() {
		return 12;
	}

	/**
	 * 设置Item样式 -设置选中Tab文字的颜色
	 */
	@Override public int getTabsSelectedTitleColor() {
		return Color.parseColor("#51A3FF");
	}

	/**
	 * 设置Item样式 -设置Tab Indicator 指示灯高度
	 */
	@Override public int getTabsIndicatorSize() {
		return 2;
	}

	/**
	 * 设置Item样式 -设置Tab Indicator 指示灯的颜色
	 */
	@Override public int getTabsIndicatorColor() {
		return Color.parseColor("#51A3FF");
	}

	/**
	 * 设置Item样式 -设置点击颜色
	 */
	@Override public int getTabsOnClickTitleColor() {
		return 0;
	}

	/**
	 * 设置Item样式 -设置Tab底部线的颜色
	 */
	@Override public int getTabsUnderlineColor() {
		return 0;
	}

	/**
	 * 设置Item样式 -设置Tab底部线的高度
	 */
	@Override public int getTabsUnderlineHeight() {
		return 1;
	}

	/**
	 * 设置Item样式 -替换item
	 */
	@Override public void replaceViewPageItem(ModelPager... modelPagers) {
		if (adapter instanceof J2WVPDefaultPagerAdapter) {
			((J2WVPDefaultPagerAdapter) adapter).replaceViewPagerDatas(modelPagers);
			adapter.notifyDataSetChanged();
		}
	}

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
	@Override public void onExtraPageScrolled(View left, View right, float v, int i2) {}

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
	@Override public void onExtraPageSelected(View current, View old, int currentPosition, int oldPosition) {
		L.tag(initTag());
		L.i("onExtraPageSelected()");
	}

	/**
	 * ViewPager 滑动事件 - 滑动改变
	 *
	 * @param i
	 */
	@Override public void onExtraPageScrollStateChanged(int i) {}

	/**
	 * 获取TabsView
	 *
	 * @param position
	 *            下标
	 */
	public View getTabsView(int position) {
		return tabs.tabsContainer.getChildAt(position);
	}

	/**
	 * 设置下标
	 *
	 * @param index
	 *            位置
	 * @param bool
	 *            是否开启动画
	 */
	@Override public void setIndex(int index, boolean bool) {
		int childCount = pager.getChildCount();
		if (0 <= index && index < childCount) {
			pager.setCurrentItem(index, bool);
		}
	}

	@Override protected void onRestart() {
		super.onRestart();

		if (adapter instanceof J2WVPDefaultPagerAdapter) {
			J2WVPDefaultPagerAdapter defaultPagerAdapter = (J2WVPDefaultPagerAdapter) adapter;
			ModelPager modelPager = defaultPagerAdapter.getData(pager.getCurrentItem());
			((J2WFragment) modelPager.fragment).onFragmentRestart(modelPager.position);
		}
	}

	/**
	 * 默认是只有图标
	 */
	public final class DefaultIconPagerAdapter<T extends J2WIPresenter> extends J2WVPDefaultPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

		public DefaultIconPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerABActivity j2WIViewViewpagerABActivity) {
			super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerABActivity);
		}

		@Override public int getPageIconResId(int position) {
			return viewPagerDatas[position].icon;
		}
	}

	/**
	 * 默认带数量标题的
	 */
	public final class DefaultCountPagerAdapter<T extends J2WIPresenter> extends J2WVPDefaultPagerAdapter implements PagerSlidingTabStrip.TitleCountTabProvider {

		public DefaultCountPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerABActivity j2WIViewViewpagerABActivity) {
			super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerABActivity);
		}

		@Override public String getPageCount(int position) {
			String count = viewPagerDatas[position].count;
			if (StringUtils.isEmpty(count)) {
				return null;
			}
			return count;
		}

		public void setTitleCount(int position, String count) {
			if (adapter instanceof DefaultCountPagerAdapter) {
				DefaultCountPagerAdapter defaultCountPagerAdapter = (DefaultCountPagerAdapter) adapter;
				ModelPager modelPager = defaultCountPagerAdapter.getData(position);
				modelPager.count = count;
				tabs.notifyDataSetChanged();
			}
		}
	}
}
