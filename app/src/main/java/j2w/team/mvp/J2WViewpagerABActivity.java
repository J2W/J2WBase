package j2w.team.mvp;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.lang.StringUtils;

import butterknife.ButterKnife;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.widget.J2WViewPager;
import j2w.team.common.widget.PagerSlidingTabStrip;
import j2w.team.mvp.fragment.J2WVPFragment;
import j2w.team.mvp.model.ModelPager;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/3/3.ViewPager
 */
public abstract class J2WViewpagerABActivity<T extends J2WIPresenter> extends J2WABActivity<T> implements J2WIViewViewpagerABActivity {

	/**
	 * ViewPager 头部 *
	 */
	private PagerSlidingTabStrip	tabs;

	/**
	 * Viewpager 内部*
	 */
	private J2WViewPager			pager;

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

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** 是否固定竖屏 **/
		if (isFixedVerticalScreen()) {
			/** 竖屏显示 **/
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		L.tag(initTag());
		L.i("ViewPagerActivity-onCreate()");
		setContentView(layoutId());
		/** 初始化标题栏 **/
		initActionBar();
		tabs = (PagerSlidingTabStrip) findViewById(android.R.id.tabs);
		pager = (J2WViewPager) findViewById(R.id.pager);
		// 设置Viewpager内部
		initViewPager();
		// 设置Viewpager头部
		initTabsValue();
		/** 初始化所有组建 **/
		ButterKnife.inject(this);
		/** 添加到堆栈 **/
		J2WHelper.getScreenHelper().pushActivity(this);
		// 设置数据
		initData(savedInstanceState);

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
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, dm));
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
	@Override public final void initViewPager() {
		L.tag(initTag());
		L.i("ViewPagerActivity-initViewPager()");
		// 设置适配器
		if (adapter == null) {
			adapter = getPagerAdapter();
		}
		pager.setAdapter(adapter);
		// 间隔距离
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		// 设置距离
		pager.setPageMargin(pageMargin);
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
		return new DefaultPagerAdapter();
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
		if (adapter instanceof DefaultPagerAdapter) {
			((DefaultPagerAdapter) adapter).replaceViewPagerDatas(modelPagers);
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * ViewPager 滑动事件 - 滑动过程
	 *
	 * @param i
	 * @param v
	 * @param i2
	 */
	@Override public void onExtraPageScrolled(int i, float v, int i2) {}

	/**
	 * ViewPager 滑动事件 - 滑动完成
	 *
	 * @param view
	 * @param i
	 */
	@Override public void onExtraPageSelected(View view, int i) {
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

	/**
	 * 适配器 - 默认适配器
	 */
	protected class DefaultPagerAdapter<T extends J2WIPresenter> extends PagerAdapter implements ViewPager.OnPageChangeListener {

		ModelPager[]			viewPagerDatas;			// 数据类型

		private FragmentManager	fragmentManager;			// 管理器

		private int				currentPageIndex	= 0;	// 当前page索引（切换之前）

		private int				replacePosition		= -1;	// 替换标识

		public DefaultPagerAdapter() {
			viewPagerDatas = initModelPagers();
			fragmentManager = getSupportFragmentManager();
			tabs.setOnPageChangeListener(this);
		}

		ViewGroup	container;

		public void replaceViewPagerDatas(ModelPager... modelPagers) {
			L.tag(initTag());
			L.i("replaceViewPagerDatas() ");
			replacePosition = pager.getCurrentItem();
			for (ModelPager modelPager : modelPagers) {
				int position = modelPager.position;
				container.removeView(viewPagerDatas[position].fragment.getView());
				viewPagerDatas[position] = modelPager;
			}

		}

		@Override public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public ModelPager[] getViewPagerDatas() {
			return viewPagerDatas;
		}

		public ModelPager getData(int position) {
			return viewPagerDatas[position];
		}

		@Override public CharSequence getPageTitle(int position) {
			return viewPagerDatas[position].title;
		}

		@Override public int getCount() {
			return viewPagerDatas.length;
		}

		@Override public void destroyItem(ViewGroup container, int position, Object object) {
			L.i("destroyItem:" + position);
			this.container = container;
			container.removeView(viewPagerDatas[position].fragment.getView()); // 移出viewpager两边之外的page布局
		}

		@Override public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override public Object instantiateItem(ViewGroup container, int position) {
			L.i("instantiateItem:" + position);
			this.container = container;
			Fragment fragment = viewPagerDatas[position].fragment;
			if (!fragment.isAdded()) { // 如果fragment还没有added
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
				ft.add(fragment, fragment.getClass().getSimpleName() + position);
				ft.commitAllowingStateLoss();
				/**
				 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
				 * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
				 * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
				 */
				fragmentManager.executePendingTransactions();
				if (replacePosition != -1) {
					((J2WVPFragment) viewPagerDatas[replacePosition].fragment).isDelayedData();
					replacePosition = -1;
				}
			}

			if (fragment.getView().getParent() == null) {
				container.addView(fragment.getView()); // 为viewpager增加布局
			}

			pager.setObjectForPosition(fragment.getView(), position);

			return fragment.getView();
		}

		@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			onExtraPageScrolled(position, positionOffset, positionOffsetPixels);
		}

		@Override public void onPageSelected(int position) {
			L.tag(initTag());
			L.i("onPageSelected() :getCurrentItem() :" + pager.getCurrentItem() + " currentPageIndex : " + currentPageIndex + " position :" + position);
			viewPagerDatas[currentPageIndex].fragment.onPause(); // 调用切换前Fargment的onPause()
			// 调用切换前Fargment的onStop()
			if (viewPagerDatas[position].fragment.isAdded()) {
				if (pager.getCurrentItem() == position) {
					// 更新actionbar
					((J2WVPFragment) viewPagerDatas[position].fragment).updateActionBar();
				}

				viewPagerDatas[position].fragment.onResume(); // 调用切换后Fargment的onResume()

				((J2WVPFragment) viewPagerDatas[position].fragment).isDelayedData(); // 调用延迟加载\
			}

			currentPageIndex = position;

			onExtraPageSelected(tabs.tabsContainer.getChildAt(position), position);
		}

		@Override public void onPageScrollStateChanged(int state) {
			onExtraPageScrollStateChanged(state);
		}
	}

	@Override protected void onRestart() {
		super.onRestart();

		if (adapter instanceof DefaultPagerAdapter) {
			DefaultPagerAdapter defaultPagerAdapter = (DefaultPagerAdapter) adapter;
			ModelPager modelPager = defaultPagerAdapter.getData(pager.getCurrentItem());
			((J2WVPFragment) modelPager.fragment).onFragmentRestart(modelPager.position);
		}
	}

	/**
	 * 默认是只有图标
	 */
	public final class DefaultIconPagerAdapter<T extends J2WIPresenter> extends DefaultPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

		@Override public int getPageIconResId(int position) {
			return viewPagerDatas[position].icon;
		}
	}

	/**
	 * 默认带数量标题的
	 */
	public final class DefaultCountPagerAdapter<T extends J2WIPresenter> extends DefaultPagerAdapter implements PagerSlidingTabStrip.TitleCountTabProvider {

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

	/**
	 * 自定义适配器
	 */
	public final class CustomPagerAdapter<T extends J2WIPresenter> extends DefaultPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

		@Override public int getCustomTabView() {
			if (getViewPagerItemLayout() == 0) {
				new IllegalArgumentException("必须要有自定义布局！");
			}
			return getViewPagerItemLayout();
		}

		@Override public void initTabsItem(View view, int position) {
			initTab(view, viewPagerDatas[position]);
		}
	}
}
