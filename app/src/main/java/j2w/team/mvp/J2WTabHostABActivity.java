package j2w.team.mvp;

import j2w.team.R;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/3/6.
 */
public abstract class J2WTabHostABActivity<T extends J2WIPresenter> extends J2WViewpagerABActivity<T> implements J2WIViewTabHostABActivity {

	/**
	 * 获取布局ID
	 *
	 * @return 布局ID
	 */
	@Override public int layoutId() {
		return R.layout.j2w_acitvity_tabhost;
	}

	/**
	 * 设置Item样式 -设置Tab Indicator 指示灯的颜色
	 */
	@Override public int getTabsIndicatorColor() {
		return 0;
	}

	/**
	 * 初始化 ViewPager - adapter
	 *
	 * @return 适配器
	 */
	@Override public final DefaultPagerAdapter getPagerAdapter() {
		return new CustomPagerAdapter();
	}

	/**
	 * 初始化 TabHost - item 样式
	 *
	 * @return　 布局ID
	 */
	@Override public final int getViewPagerItemLayout() {
		return getTabsContentLayout();
	}

}
