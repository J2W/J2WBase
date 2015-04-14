package j2w.team.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by sky on 15/2/1.MVP模式 VIEW 接口
 */
public interface J2WIView<T> {

	/**
	 * 获取TAG标记
	 * 
	 * @return tag
	 */
	public String initTag();

	/**
	 * 获取布局ID
	 * 
	 * @return 布局ID
	 */
	public int layoutId();

	/**
	 * 初始化数据
	 * 
	 * @param savedInstanceState
	 *            数据
	 */
	public void initData(Bundle savedInstanceState);

	/**
	 * 获取Presenter
	 * 
	 * @return 业务
	 */
	public T getPresenter();

	/**
	 * 是否打开EventBus
	 * 
	 * @return true 打开 false 关闭
	 */
	public boolean isOpenEventBus();

	/**
	 * 跳转
	 * 
	 * @param clazz
	 *            activity.class
	 */
	public void intent2Activity(Class clazz);

	/**
	 * 跳转
	 * 
	 * @param clazz
	 *            activity.class
	 * @param bundle
	 *            数据
	 */
	public void intent2Activity(Class clazz, Bundle bundle);

	/**
	 * 跳转
	 * 
	 * @param clazz
	 *            activity.class
	 * @param requestCode
	 *            请求编号
	 */
	public void intent2Activity(Class clazz, int requestCode);

	/**
	 * 跳转
	 * 
	 * @param clazz
	 *            acitivity.class
	 * @param bundle
	 *            数据
	 * @param requestCode
	 *            请求编号
	 */
	public void intent2Activity(Class clazz, Bundle bundle, int requestCode);

	/**
	 * 获取上下文
	 * 
	 * @return 上下文
	 */
	public Context getContext();

	/**
	 * 获取碎片管理器
	 * 
	 * @return
	 */
	public FragmentManager getFManager();

	/**
	 * 提交fragment
	 *
	 * @param fragment
	 *            实例
	 */
	public void commitFragment(Fragment fragment);

	/**
	 * 提交fragment
	 * 
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	public void commitFragment(Fragment fragment, String tag);

	/**
	 * 提交fragment
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 */
	public void commitFragment(int layoutId, Fragment fragment);

	/**
	 * 提交fragment
	 * 
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	public void commitFragment(int layoutId, Fragment fragment, String tag);

	/**
	 * 提交fragment - 压栈
	 *
	 * @param fragment
	 *            实例
	 */
	public void commitBackStackFragment(Fragment fragment);

	/**
	 * 提交fragment - 压栈
	 *
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	public void commitBackStackFragment(Fragment fragment, String tag);

	/**
	 * 提交fragment - 压栈
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 */
	public void commitBackStackFragment(int layoutId, Fragment fragment);

	/**
	 * 提交fragment - 压栈
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	public void commitBackStackFragment(int layoutId, Fragment fragment, String tag);

	/**
	 * 弹框进度条
	 */
	public void loading();

	/**
	 * 弹框进度条
	 * 
	 * @param value
	 */
	public void loading(String value);

	/**
	 * 弹框进度条
	 */
	public void loadingClose();
    
	/**
	 * 销毁当前页面
	 */
	public void activityFinish();
}
