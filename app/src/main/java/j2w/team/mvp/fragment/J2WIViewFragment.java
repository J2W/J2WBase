package j2w.team.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import j2w.team.mvp.J2WIView;

/**
 * Created by sky on 15/2/5. fragment 视图接口
 */
public interface J2WIViewFragment extends J2WIView {

	/**
	 * 初始化视图
	 * 
	 * @param inflater
	 *            布局加载器
	 * @param container
	 *            父容器
	 */
	public void initLayout(LayoutInflater inflater, ViewGroup container);

	/**
	 * 获取主视图
	 * 
	 * @return
	 */
	public View getContentView();

	/**
	 * 布局 - 进度条
	 */
	public void showLoading();

	/**
	 * 布局 - 内容
	 */
	public void showContent();

	/**
	 * 布局 - 空
	 */
	public void showEmpty();

	/**
	 * 布局 - 错误
	 */
	public void showError();

	/**
	 * fragment防止事件穿透
	 * 
	 * @return true 禁止 false 开房
	 */
	public boolean isTouch();

	/**
	 * fragment 刷新标题栏
	 */
	public void onActionBar();
}