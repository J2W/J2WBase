package j2w.team.mvp.view.iview;

import android.view.View;

/**
 * Created by sky on 15/2/5. actionbar 视图接口
 */
public interface J2WActionBarIView extends J2WIView {

	/**
	 * 初始化actionbar
	 */
	public void initActionBar();

	/**
	 * 设置布局
	 * 
	 * @return 布局ID
	 */
	public int actionBarLayoutID();

	/**
	 * 右边点击事件
	 */
	public void onActionBarRight(View view);

	/**
	 * 左边边点击事件
	 */
	public void onActionBarLeft(View view);

	/**
	 * 自定义 右边按钮样式
	 * 
	 * @return 数据类型 int,view,String
	 */
	public Object getActionBarRightValue();

	/**
	 * 设置标题
	 *
	 * @return
	 */
	public String actionBarTitle();

	/**
	 * 修改标题
	 *
	 * @param value
	 *            值
	 * @return
	 */
	public void setActionBarTitle(String value);
}
