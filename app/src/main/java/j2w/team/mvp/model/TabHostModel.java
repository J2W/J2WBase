package j2w.team.mvp.model;

import android.support.v4.app.Fragment;

/**
 * Created by sky on 15/3/6. TabHostModel
 */
public class TabHostModel {

	/** TAG **/
	public String						tag;

	/** Tab图标 **/
	public int							tabIcon;

	/** Tab名称 **/
	public String						tabName;

	/** Tab类型 **/
	public Class<? extends Fragment>	aClass;

	/** 碎片 **/
	public Fragment						fragment;
}
