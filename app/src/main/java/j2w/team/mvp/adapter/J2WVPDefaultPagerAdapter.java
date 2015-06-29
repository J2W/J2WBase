package j2w.team.mvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import j2w.team.common.log.L;
import j2w.team.common.widget.J2WViewPager;
import j2w.team.common.widget.PagerSlidingTabStrip;
import j2w.team.mvp.J2WIViewViewpagerABActivity;
import j2w.team.mvp.J2WIViewViewpagerActivity;
import j2w.team.mvp.fragment.J2WFragment;
import j2w.team.mvp.fragment.J2WIViewViewpagerFragment;
import j2w.team.mvp.model.ModelPager;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * @创建人 sky
 * @创建时间 15/4/24 下午3:09
 * @类描述 ViewPager 默认适配器
 */
public class J2WVPDefaultPagerAdapter<T extends J2WIPresenter> extends PagerAdapter implements ViewPager.OnPageChangeListener {

	protected ModelPager[]					viewPagerDatas;				// 数据类型

	protected FragmentManager				fragmentManager;				// 管理器

	protected int							currentPageIndex	= 0;		// 当前page索引（切换之前）

	protected int							replacePosition		= -1;		// 替换标识

	protected String						tag;							// 标记

	protected PagerSlidingTabStrip			tabs;							// 标题

	protected J2WViewPager					pager;							// viewpager

	protected J2WIViewViewpagerABActivity	j2WIViewViewpagerABActivity;	// View层接口

	protected J2WIViewViewpagerActivity		j2WIViewViewpagerActivity;		// View层接口

	protected J2WIViewViewpagerFragment		j2WIViewViewpagerFragment;		// View层接口

	/**
	 * 记录Viewpager Item
	 */
	protected View							oldView				= null;

	/**
	 * 就Viewpager 坐标
	 */
	protected int							oldPosition			= -1;

	/**
	 * 父类容器
	 */
	ViewGroup								container;

	private View							left;							// 滑动渐变
																			// 左面视图

	private View							right;							// 滑动渐变
																			// 右面视图

	/**
	 * 初始化
	 * 
	 * @param tag
	 *            标记
	 * @param fragmentManager
	 *            管理器
	 * @param tabs
	 *            标题
	 * @param pager
	 *            内容
	 * @param j2WIViewViewpagerABActivity
	 *            接口
	 */
	public J2WVPDefaultPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerABActivity j2WIViewViewpagerABActivity) {
		L.tag(tag);
		L.i("J2WVPDefaultPagerAdapter()");
		this.tag = tag;
		this.j2WIViewViewpagerABActivity = j2WIViewViewpagerABActivity;
		this.fragmentManager = fragmentManager;
		this.tabs = tabs;
		this.pager = pager;
		this.tabs.setOnPageChangeListener(this);
	}

	/**
	 * 初始化
	 * 
	 * @param tag
	 *            标记
	 * @param fragmentManager
	 *            管理器
	 * @param tabs
	 *            标题
	 * @param pager
	 *            内容
	 * @param j2WIViewViewpagerFragment
	 *            接口
	 */
	public J2WVPDefaultPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerFragment j2WIViewViewpagerFragment) {
		L.tag(tag);
		L.i("J2WVPDefaultPagerAdapter()");
		this.tag = tag;
		this.j2WIViewViewpagerFragment = j2WIViewViewpagerFragment;
		this.fragmentManager = fragmentManager;
		this.tabs = tabs;
		this.pager = pager;
		this.tabs.setOnPageChangeListener(this);
	}

	/**
	 * 初始化
	 * 
	 * @param tag
	 *            标记
	 * @param fragmentManager
	 *            管理器
	 * @param tabs
	 *            标题
	 * @param pager
	 *            内容
	 * @param j2WIViewViewpagerActivity
	 *            接口
	 */
	public J2WVPDefaultPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerActivity j2WIViewViewpagerActivity) {
		L.tag(tag);
		L.i("J2WVPDefaultPagerAdapter()");
		this.tag = tag;
		this.j2WIViewViewpagerActivity = j2WIViewViewpagerActivity;
		this.fragmentManager = fragmentManager;
		this.tabs = tabs;
		this.pager = pager;
		this.tabs.setOnPageChangeListener(this);
	}

	/**
	 * 设置数据
	 * 
	 * @param viewPagerDatas
	 */
	public void setModelPagers(ModelPager[] viewPagerDatas) {
		this.viewPagerDatas = viewPagerDatas;
	}

	/**
	 * 替换
	 * 
	 * @param modelPagers
	 *            数据
	 */
	public void replaceViewPagerDatas(ModelPager... modelPagers) {
		L.tag(tag);
		L.i("replaceViewPagerDatas() ");
		replacePosition = pager.getCurrentItem();

		for (ModelPager modelPager : modelPagers) {
			int position = modelPager.position;
			container.removeView(viewPagerDatas[position].fragment.getView());
			FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
			fragmentTransaction.detach(viewPagerDatas[position].fragment).commitAllowingStateLoss();
			viewPagerDatas[position] = modelPager;
		}

	}

	/**
	 * 根据返回值 刷新
	 * 
	 * @param object
	 * @return
	 */
	@Override public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	/**
	 * 返回数据集
	 * 
	 * @return
	 */
	public ModelPager[] getViewPagerDatas() {
		return viewPagerDatas;
	}

	/**
	 * 返回单个数据
	 * 
	 * @param position
	 * @return
	 */
	public ModelPager getData(int position) {
		return viewPagerDatas[position];
	}

	/**
	 * 返回标题
	 * 
	 * @param position
	 * @return
	 */
	@Override public CharSequence getPageTitle(int position) {
		return viewPagerDatas[position].title;
	}

	/**
	 * 返回数量
	 * 
	 * @return
	 */
	@Override public int getCount() {
		return viewPagerDatas.length;
	}

	/**
	 * 销毁
	 * 
	 * @param container
	 * @param position
	 * @param object
	 */
	@Override public void destroyItem(ViewGroup container, int position, Object object) {
		L.i("destroyItem:" + position);
		this.container = container;
		container.removeView(viewPagerDatas[position].fragment.getView()); // 移出viewpager两边之外的page布局
	}

	/**
	 * 判断是否一致
	 * 
	 * @param view
	 * @param object
	 * @return
	 */
	@Override public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	/**
	 * 生成
	 * 
	 * @param container
	 * @param position
	 * @return
	 */
	@Override public Object instantiateItem(ViewGroup container, int position) {
		L.i("instantiateItem:" + position);
		this.container = container;
		Fragment fragment = viewPagerDatas[position].fragment;
		if (!fragment.isAdded()) { // 如果fragment还没有added
			L.i("instantiateItem:commitAllowingStateLoss");
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
			fragment.setHasOptionsMenu(false);// 设置actionbar不执行
			if (replacePosition != -1) {
				((J2WFragment) viewPagerDatas[replacePosition].fragment).isDelayedData();
				((J2WFragment) viewPagerDatas[replacePosition].fragment).onActionBar();
				((J2WFragment) viewPagerDatas[replacePosition].fragment).onResume();
				replacePosition = -1;
			}
		}
		if(fragment.getView() == null){
			throw new NullPointerException("fragment,没有给布局，导致获取不到View");
		}

		if (fragment.getView().getParent() == null) {
			L.i("container.addView(fragment.getView())");
			container.addView(fragment.getView()); // 为viewpager增加布局
		}

		pager.setObjectForPosition(fragment.getView(), position);

		return fragment.getView();
	}

	/**
	 * 滑动中
	 * 
	 * @param position
	 * @param positionOffset
	 * @param positionOffsetPixels
	 */
	@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		left = tabs.tabsContainer.getChildAt(position);
		right = tabs.tabsContainer.getChildAt(position + 1);
		if (j2WIViewViewpagerABActivity != null) {
			j2WIViewViewpagerABActivity.onExtraPageScrolled(left, right, positionOffset, positionOffsetPixels);
		}
		if (j2WIViewViewpagerActivity != null) {
			j2WIViewViewpagerActivity.onExtraPageScrolled(left, right, positionOffset, positionOffsetPixels);
		}
		if (j2WIViewViewpagerFragment != null) {
			j2WIViewViewpagerFragment.onExtraPageScrolled(left, right, positionOffset, positionOffsetPixels);
		}
	}

	/**
	 * 滑动选择
	 * 
	 * @param position
	 */
	@Override public void onPageSelected(int position) {
		L.tag(tag);
		L.i("onPageSelected() :getCurrentItem() :" + pager.getCurrentItem() + " currentPageIndex : " + currentPageIndex + " position :" + position);
		viewPagerDatas[currentPageIndex].fragment.onPause(); // 调用切换前Fargment的onPause()
		// 调用切换前Fargment的onStop()
		if (viewPagerDatas[position].fragment.isAdded()) {

			((J2WFragment) viewPagerDatas[position].fragment).isDelayedData(); // 调用延迟加载

			if (pager.getCurrentItem() == position) {
				// 更新actionbar
				((J2WFragment) viewPagerDatas[position].fragment).onActionBar();
			}

			viewPagerDatas[position].fragment.onResume(); // 调用切换后Fargment的onResume()

		}

		currentPageIndex = position;
		if (j2WIViewViewpagerABActivity != null) {
			j2WIViewViewpagerABActivity.onExtraPageSelected(tabs.tabsContainer.getChildAt(position), oldView, position, oldPosition);
		}
		if (j2WIViewViewpagerActivity != null) {
			j2WIViewViewpagerActivity.onExtraPageSelected(tabs.tabsContainer.getChildAt(position), oldView, position, oldPosition);
		}
		if (j2WIViewViewpagerFragment != null) {
			j2WIViewViewpagerFragment.onExtraPageSelected(tabs.tabsContainer.getChildAt(position), oldView, position, oldPosition);
		}
		oldView = tabs.tabsContainer.getChildAt(position);// 缓存视图
		oldPosition = position; // 缓存坐标
	}

	/**
	 * 滑动状态
	 * 
	 * @param state
	 */
	@Override public void onPageScrollStateChanged(int state) {
		if (j2WIViewViewpagerABActivity != null) {
			j2WIViewViewpagerABActivity.onExtraPageScrollStateChanged(state);
		}
		if (j2WIViewViewpagerActivity != null) {
			j2WIViewViewpagerActivity.onExtraPageScrollStateChanged(state);
		}
		if (j2WIViewViewpagerFragment != null) {
			j2WIViewViewpagerFragment.onExtraPageScrollStateChanged(state);
		}
	}
}
