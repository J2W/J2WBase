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
	String initTag();

	/**
	 * 获取布局ID
	 * 
	 * @return 布局ID
	 */
	int layoutId();

	/**
	 * 初始化数据
	 * 
	 * @param savedInstanceState
	 *            数据
	 */
	void initData(Bundle savedInstanceState);

	/**
	 * 获取Presenter
	 * 
	 * @return 业务
	 */
	T getPresenter();

	/**
	 * 是否打开EventBus
	 * 
	 * @return true 打开 false 关闭
	 */
	boolean isOpenEventBus();

	/**
	 * 跳转
	 * 
	 * @param clazz
	 *            activity.class
	 */
	void intent2Activity(Class clazz);

	/**
	 * 跳转
	 * 
	 * @param clazz
	 *            activity.class
	 * @param bundle
	 *            数据
	 */
	void intent2Activity(Class clazz, Bundle bundle);

	/**
	 * 跳转
	 * 
	 * @param clazz
	 *            activity.class
	 * @param requestCode
	 *            请求编号
	 */
	void intent2Activity(Class clazz, int requestCode);

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
	void intent2Activity(Class clazz, Bundle bundle, int requestCode);

	/**
	 * 获取上下文
	 * 
	 * @return 上下文
	 */
	Context getContext();

	/**
	 * 获取碎片管理器
	 * 
	 * @return
	 */
	FragmentManager getFManager();

	/**
	 * 提交fragment
	 *
	 * @param fragment
	 *            实例
	 */
	void commitFragment(Fragment fragment);

	/**
	 * 提交fragment
	 * 
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	void commitFragment(Fragment fragment, String tag);

	/**
	 * 提交fragment
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 */
	void commitFragment(int layoutId, Fragment fragment);

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
	void commitFragment(int layoutId, Fragment fragment, String tag);

	/**
	 * 提交fragment
	 *
	 * @param old
	 *            需要销毁的fragment
	 * @param fragment
	 *            实例
	 */
	void commitFragment(Fragment old, Fragment fragment);

	/**
	 * 提交fragment
	 *
	 * @param old
	 *            需要销毁的fragment
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	void commitFragment(Fragment old, Fragment fragment, String tag);

	/**
	 * 提交fragment
	 * 
	 * @param old
	 *            需要销毁的fragment
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 */
	void commitFragment(Fragment old, int layoutId, Fragment fragment);

	/**
	 * 提交fragment
	 *
	 * @param old
	 *            需要销毁的fragment
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	void commitFragment(Fragment old, int layoutId, Fragment fragment, String tag);

	/**
	 * 提交fragment - 压栈
	 *
	 * @param fragment
	 *            实例
	 */
	void commitBackStackFragment(Fragment fragment);

	/**
	 * 提交fragment - 压栈
	 *
	 * @param fragment
	 *            实例
	 * @param tag
	 *            标记
	 */
	void commitBackStackFragment(Fragment fragment, String tag);

	/**
	 * 提交fragment - 压栈
	 *
	 * @param layoutId
	 *            布局ID
	 * @param fragment
	 *            实例
	 */
	void commitBackStackFragment(int layoutId, Fragment fragment);

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
	void commitBackStackFragment(int layoutId, Fragment fragment, String tag);

	/**
	 * 弹框进度条
	 */
	void loading();

	/**
	 * 弹框进度条
	 * 
	 * @param cancel
	 */
	void loading(boolean cancel);

	/**
	 * 弹框进度条
	 * 
	 * @param value
	 */
	void loading(String value);

	/**
	 * 弹框进度条
	 * 
	 * @param value
	 * @param cancel
	 */
	void loading(String value, boolean cancel);

	/**
	 * 替换进度条文案
	 * 
	 * @param value
	 */
	void replaceLoading(String value);

	/**
	 * 弹框进度条
	 */
	void loadingClose();

	/**
	 * 销毁当前页面
	 */
	void activityFinish();

	/**
	 * 布局 - 进度条
	 */
	void showLoading();

	/**
	 * 布局 - 内容
	 */
	void showContent();

	/**
	 * 布局 - 空
	 */
	void showEmpty();

	/**
	 * 布局 - 错误
	 */
	void showError();
}
