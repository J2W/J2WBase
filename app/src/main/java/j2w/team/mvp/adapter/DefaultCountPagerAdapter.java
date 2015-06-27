package j2w.team.mvp.adapter;

/**
 * @创建人 sky
 * @创建时间 15/6/26 下午9:37
 * @类描述 一句话说明这个类是干什么的
 */

import android.support.v4.app.FragmentManager;

import org.apache.commons.lang.StringUtils;

import j2w.team.common.widget.J2WViewPager;
import j2w.team.common.widget.PagerSlidingTabStrip;
import j2w.team.mvp.J2WIViewViewpagerABActivity;
import j2w.team.mvp.J2WIViewViewpagerActivity;
import j2w.team.mvp.model.ModelPager;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * 默认带数量标题的
 */
public final class DefaultCountPagerAdapter<T extends J2WIPresenter> extends J2WVPDefaultPagerAdapter implements PagerSlidingTabStrip.TitleCountTabProvider {

	public DefaultCountPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerABActivity j2WIViewViewpagerABActivity) {
		super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerABActivity);
	}

	public DefaultCountPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, J2WViewPager pager, J2WIViewViewpagerActivity j2WIViewViewpagerActivity) {
		super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerActivity);
	}

	@Override public String getPageCount(int position) {
		String count = viewPagerDatas[position].count;
		if (StringUtils.isEmpty(count)) {
			return null;
		}
		return count;
	}

	public void setTitleCount(int position, String count) {
		if (j2WIViewViewpagerABActivity.getPagerAdapter() instanceof DefaultCountPagerAdapter) {
			DefaultCountPagerAdapter defaultCountPagerAdapter = (DefaultCountPagerAdapter) j2WIViewViewpagerABActivity.getPagerAdapter();
			ModelPager modelPager = defaultCountPagerAdapter.getData(position);
			modelPager.count = count;
			tabs.notifyDataSetChanged();
		}
	}
}