package j2w.team.mvp.view;

import android.support.v4.view.PagerAdapter;

import j2w.team.R;
import j2w.team.mvp.view.iview.J2WTabHostIView;

/**
 * Created by sky on 15/3/6.
 */
public abstract class J2WBaseTabHostAcitvity extends J2WBaseViewPagerAcitvity implements J2WTabHostIView {

	@Override public final int layoutId() {
		return R.layout.j2w_acitvity_tabhost;
	}

	@Override public int getTabsIndicatorColor() {
		return 0;
	}

	@Override public final PagerAdapter getPagerAdapter() {
		return new CustomPagerAdapter();
	}

	@Override public final int getViewPagerItemLayout() {
		return getTabsContentLayout();
	}
}
