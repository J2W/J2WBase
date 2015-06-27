package j2w.team.mvp.adapter;

import android.support.v4.app.FragmentManager;

import j2w.team.common.widget.J2WViewPager;
import j2w.team.common.widget.PagerSlidingTabStrip;
import j2w.team.mvp.J2WIViewViewpagerABActivity;
import j2w.team.mvp.J2WIViewViewpagerActivity;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * @创建人 sky
 * @创建时间 15/6/26 下午9:36
 * @类描述 viewpager适配器
 */
public final class DefaultIconPagerAdapter<T extends J2WIPresenter> extends J2WVPDefaultPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

	public DefaultIconPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerABActivity j2WIViewViewpagerABActivity) {
		super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerABActivity);
	}

	public DefaultIconPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerActivity j2WIViewViewpagerActivity) {
		super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerActivity);
	}

	@Override public int getPageIconResId(int position) {
		return viewPagerDatas[position].icon;
	}
}