package j2w.team.mvp.view.iview;

import java.util.List;

import j2w.team.mvp.view.J2WBaseAdapterItem;

/**
 * Created by sky on 15/2/6. listfragment 视图接口
 */
public interface J2WListFragmentIView extends J2WFragmentIView {

	/**
	 * 初始化
	 */
	public int getHeaderLayout();

	public int getFooterLayout();

	public int getJ2WViewTypeCount();

	public int getJ2WViewType(int position);

	public J2WBaseAdapterItem getJ2WAdapterItem();

	public J2WBaseAdapterItem getJ2WAdapterItem(int type);

	/**
	 * 逻辑操作
	 */

	public boolean isAdapterNotNull();

	public void setData(List list);

	public void addData(List list);

	public List getData();

	public void updataList();
}
