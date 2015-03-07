package j2w.team.mvp.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;
import java.util.List;

import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.mvp.model.TabHostModel;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.view.iview.J2WTabHostIView;

/**
 * Created by sky on 15/3/6.
 */
public abstract class J2WBaseTabHostAcitvity extends FragmentActivity implements J2WTabHostIView, TabHost.OnTabChangeListener {

	private FragmentTabHost				mTabHost;

	private ViewPager					viewPager;

	private List<Fragment>				list	= new ArrayList<Fragment>();

	private FragmentViewPagerAdapter	adapter;

	@Override public final int layoutId() {
		return R.layout.j2w_acitvity_tabhost;
	}

	/** 初始化数据 **/
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("initData()");
	}

	@Override public final Object getPresenter() {
		return null;
	}

	/** 初始化视图 **/
	@Override public void initView() {
		L.tag(initTag());
		L.i("initView()");
		/** 初始化 **/
		viewPager = (ViewPager) findViewById(R.id.pager);
		/** 初始化 **/
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.pager);
		mTabHost.setOnTabChangedListener(this);
		/** 初始化显示 **/

		for (TabHostModel tabHostModel : getTabHostModels()) {
			View view = LayoutInflater.from(this).inflate(getTabItemLayout(), null);
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabHostModel.tabName).setIndicator(getTabItemView(view, tabHostModel));
			mTabHost.addTab(tabSpec, tabHostModel.aClass, null);
			mTabHost.setTag(tabHostModel.tag);
			list.add(tabHostModel.fragment);
		}

		adapter = new FragmentViewPagerAdapter(this.getSupportFragmentManager(), viewPager, list);
		/** 默认选中第一个 **/
		viewPager.setCurrentItem(0);
	}

	@Override public FragmentTabHost getTabHost() {
		return mTabHost;
	}

	@Override public FragmentViewPagerAdapter getViewPagerAdapter() {
		return adapter;
	}

	/** onCreate 无法重写 **/
	@Override protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 是否固定竖屏 **/
		if (isFixedVerticalScreen()) {
			/** 竖屏显示 **/
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		L.tag(initTag());
		L.i("onCreate()");
		/** 初始化视图 **/
		setContentView(layoutId());
		/** 添加到堆栈 **/
		J2WHelper.getScreenHelper().pushActivity(this);
		/** 初始化视图 **/
		initView();
		/** 初始化视图组建 **/
		initData(savedInstanceState);
	}

	@Override protected void onStart() {
		super.onStart();
		L.tag(initTag());
		L.i("onStart()");
	}

	@Override protected void onResume() {
		super.onResume();
		L.tag(initTag());
		L.i("onResume()");
	}

	@Override protected void onPause() {
		super.onPause();
		L.tag(initTag());
		L.i("onPause()");
	}

	@Override protected void onRestart() {
		super.onRestart();
		L.tag(initTag());
		L.i("onRestart()");
	}

	@Override protected void onStop() {
		super.onStop();
		L.tag(initTag());
		L.i("onStop()");
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		L.tag(initTag());
		L.i("onDestroy()");
		/** 从堆栈里移除 **/
		J2WHelper.getScreenHelper().popActivity(this);
	}

	/**
	 * 是否固定竖屏
	 */
	@Override public boolean isFixedVerticalScreen() {
		return true;
	}

	@Override public void onTabChanged(String tabId) {
		int position = mTabHost.getCurrentTab();

		viewPager.setCurrentItem(position, false);
	}

	@Override public void onExtraPageScrolled(int i, float v, int i2) {}

	@Override public void onExtraPageSelected(int i) {
		L.tag(initTag());
		L.i("onExtraPageSelected()");
	}

	@Override public void onExtraPageScrollStateChanged(int i) {}

	public class FragmentViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

		private List<Fragment>	fragments;					// 每个Fragment对应一个Page

		private FragmentManager	fragmentManager;

		private ViewPager		viewPager;					// viewPager对象

		private int				currentPageIndex	= 0;	// 当前page索引（切换之前）

		public FragmentViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager, List<Fragment> fragments) {
			this.fragments = fragments;
			this.fragmentManager = fragmentManager;
			this.viewPager = viewPager;
			this.viewPager.setAdapter(this);
			this.viewPager.setOnPageChangeListener(this);
		}

		@Override public int getCount() {
			return fragments.size();
		}

		@Override public boolean isViewFromObject(View view, Object o) {
			return view == o;
		}

		@Override public void destroyItem(ViewGroup container, int position, Object object) {
			L.i("destroyItem:" + position);

			container.removeView(fragments.get(position).getView()); // 移出viewpager两边之外的page布局
		}

		@Override public Object instantiateItem(ViewGroup container, int position) {
			L.i("instantiateItem:" + position);
			Fragment fragment = fragments.get(position);
			if (!fragment.isAdded()) { // 如果fragment还没有added
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.add(fragment, fragment.getClass().getSimpleName());
				ft.commit();
				/**
				 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
				 * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
				 * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
				 */
				fragmentManager.executePendingTransactions();
			}

			if (fragment.getView().getParent() == null) {
				container.addView(fragment.getView()); // 为viewpager增加布局
			}

			return fragment.getView();
		}

		/**
		 * 当前page索引（切换之前）
		 *
		 * @return
		 */
		public int getCurrentPageIndex() {
			return currentPageIndex;
		}

		@Override public void onPageScrolled(int i, float v, int i2) {
			onExtraPageScrolled(i, v, i2);
		}

		@Override public void onPageSelected(int i) {
			L.tag("FragmentViewPagerAdapter");
			L.i("onPageSelected() : " + i);
			fragments.get(currentPageIndex).onPause(); // 调用切换前Fargment的onPause()
			// 调用切换前Fargment的onStop()
			if (fragments.get(i).isAdded()) {
				fragments.get(i).onResume(); // 调用切换后Fargment的onResume()
				((J2WBaseFragment) fragments.get(i)).isDelayedData(); // 调用延迟加载
			}
			currentPageIndex = i;

			onExtraPageSelected(i);
		}

		@Override public void onPageScrollStateChanged(int i) {
			onExtraPageScrollStateChanged(i);
		}
	}
}
