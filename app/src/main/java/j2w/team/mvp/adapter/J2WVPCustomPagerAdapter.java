package j2w.team.mvp.adapter;

import android.support.v4.app.FragmentManager;
import android.view.View;

import j2w.team.common.widget.J2WViewPager;
import j2w.team.common.widget.PagerSlidingTabStrip;
import j2w.team.mvp.J2WIViewViewpagerABActivity;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * @创建人 sky
 * @创建时间 15/4/24 下午3:40
 * @类描述 ViewPager自定义布局
 */
public class J2WVPCustomPagerAdapter<T extends J2WIPresenter> extends J2WVPDefaultPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

	public J2WVPCustomPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerABActivity j2WIViewViewpagerABActivity) {
		super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerABActivity);
	}

	@Override public int getCustomTabView() {
		if (j2WIViewViewpagerABActivity.getViewPagerItemLayout() == 0) {
			new IllegalArgumentException("必须要有自定义布局！");
		}
		return j2WIViewViewpagerABActivity.getViewPagerItemLayout();
	}

	@Override public void initTabsItem(View view, int position) {
		j2WIViewViewpagerABActivity.initTab(view, viewPagerDatas[position]);
	}
}