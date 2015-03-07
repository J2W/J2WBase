package j2w.team.mvp.view.iview;

import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabWidget;

import j2w.team.mvp.model.TabHostModel;
import j2w.team.mvp.view.J2WBaseTabHostAcitvity;

/**
 * Created by sky on 15/3/6. tabhost
 */
public interface J2WTabHostIView extends J2WActivityIView {

	/** 初始化 视图 **/
	public void initView();

	public int getTabItemLayout();

    public int getTabItemBackgroundResource();

	public FragmentTabHost getTabHost();

	public J2WBaseTabHostAcitvity.FragmentViewPagerAdapter getViewPagerAdapter();

	/** 初始化事件 **/
	public void onExtraPageScrolled(int i, float v, int i2);

	public void onExtraPageSelected(int i);

	public void onExtraPageScrollStateChanged(int i);

	/** 初始化 tabitem **/
	public View getTabItemView(View view, TabHostModel tabHostModel);

	/** 获取TabHost内容 **/
	public TabHostModel[] getTabHostModels();

}
