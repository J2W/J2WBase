package j2w.team.mvp.fragment;

import android.support.v4.app.FragmentManager;
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
	void initLayout(LayoutInflater inflater, ViewGroup container);

	/**
	 * 是否添加Fragment状态布局
	 *
	 * @return true 打开 false 关闭
	 */
	boolean fragmentState();

	/**
	 * 初始化视图 - 无状态
	 */
	void initNotState(LayoutInflater inflater, ViewGroup container);

	/**
	 * 获取主视图
	 * 
	 * @return
	 */
	View getContentView();

	/**
	 * fragment防止事件穿透
	 * 
	 * @return true 禁止 false 开房
	 */
	boolean isTouch();

	/**
	 * fragment 刷新标题栏
	 */
	void onActionBar();

	/**
	 * 获取碎片管理器 - 内部管理器
	 * 
	 * @return
	 */
	FragmentManager getChildFManager();

	/**
	 * 设置Acitvity标题栏
	 */
	void setActivityTitle(Object value, int code);

	/**
	 * 设置Acitvity标题栏
	 */
	void setActivityTitle(Object value);

	/**
	 * 初始化fragment状态布局 - 进度
	 *
	 * @return
	 */
	int fragmentLoadingLayout();

	/**
	 * 初始化fragment状态布局 - 空
	 *
	 * @return
	 */
	int fragmentEmptyLayout();

	/**
	 * 初始化fragment状态布局 - 错误
	 *
	 * @return
	 */
	int fragmentErrorLayout();

	/**
	 * ViewPager切换 是否调用延迟加载
	 */
	void isDelayedData();

	/**
	 * ViewPager切换 延迟数据初始化 - 执行一次
	 */
	void initDelayedData();

	/**
	 * ViewPager 重新执行
	 *
	 * @param index
	 *            下标
	 */
	void onFragmentRestart(int index);

	/**
	 * 是否添加延迟加载
	 */
	boolean isAddDelayedData();
}
