package j2w.team.mvp.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.lang.StringUtils;

import butterknife.ButterKnife;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.widget.PagerSlidingTabStrip;
import j2w.team.mvp.view.iview.J2WViewpagerIView;

/**
 * Created by sky on 15/3/3.ViewPager
 */
public abstract class J2WBaseViewPagerFragment extends Fragment implements J2WViewpagerIView {

	/**
	 * view *
	 */
	View							mContentView;

	/**
	 * ViewPager 头部 *
	 */
	private PagerSlidingTabStrip	tabs;

	/**
	 * Viewpager 内部*
	 */
	private ViewPager				pager;

	/**
	 * Viewpager 适配器*
	 */
//	private MyPagerAdapter			mPagerAdapter;

	/**
	 * 初始化视图 *
	 */
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("ViewPagerFragment-initData()");
	}

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		L.tag(initTag());
		L.i("ViewPagerFragment-onAttach()");
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.tag(initTag());
		L.i("ViewPagerFragment-onCreate()");
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("ViewPagerFragment-onCreateView()");
		mContentView = inflater.inflate(R.layout.j2w_fragment_viewpager, container, false);
		tabs = ButterKnife.findById(mContentView, R.id.tabs);
		pager = ButterKnife.findById(mContentView, R.id.pager);
		return mContentView;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		L.tag(initTag());
		L.i("ViewPagerFragment-onActivityCreated()");
		// 设置Viewpager头部
		initTabsValue();
		// 设置Viewpager内部
		initViewPager();
		// 设置数据
		initData(savedInstanceState);
	}

	@Override public void initTabsValue() {
		L.tag(initTag());
		L.i("ViewPagerFragment-initTabsValue()");
		DisplayMetrics dm = getResources().getDisplayMetrics();
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		// tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#51a3ff"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#51a3ff"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	@Override public void initViewPager() {
		L.tag(initTag());
		L.i("ViewPagerFragment-initViewPager()");
		// 创建适配器
//		mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
//		// 设置适配器
//		pager.setAdapter(mPagerAdapter);
		// 间隔距离
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		// 设置距离
		pager.setPageMargin(pageMargin);
		// 设置给头部
		tabs.setViewPager(pager);
	}

	@Override public void setTitleCount(int position, String count) {

	}

	@Override public final int layoutId() {
		return 0;
	}

	@Override public final Object getPresenter() {
		return null;
	}

//	private final class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.TitleCountTabProvider {
//
//		ArrayList<ViewPagerData>	viewPagerDatas;
//
//		public MyPagerAdapter(FragmentManager fm) {
//			super(fm);
//			viewPagerDatas = getFragments();
//		}
//
//		public ArrayList<ViewPagerData> getViewPagerDatas() {
//			return viewPagerDatas;
//		}
//
//		public ViewPagerData getData(int position) {
//			return viewPagerDatas.get(position);
//		}
//
//		@Override public CharSequence getPageTitle(int position) {
//			return viewPagerDatas.get(position).tab;
//		}
//
//		@Override public long getItemId(int position) {
//			return super.getItemId(position);
//		}
//
//		@Override public int getCount() {
//			return viewPagerDatas.size();
//		}
//
//		@Override public Fragment getItem(int position) {
//			return viewPagerDatas.get(position).fragment;
//		}
//
//		@Override public String getPageCount(int position) {
//			String count = viewPagerDatas.get(position).count;
//			if (StringUtils.isEmpty(count)) {
//				return null;
//			}
//			return count;
//		}
//	}
}
