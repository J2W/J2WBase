package j2w.team.mvp.view.iview;

import j2w.team.mvp.model.TabHostModel;

/**
 * Created by sky on 15/3/6. tabhost
 */
public interface J2WTabHostIView extends J2WActivityIView {

    public void initView();

    public void initPage();

	/** 获取TabHost内容 **/
	public TabHostModel[] getTabHostModels();
}
