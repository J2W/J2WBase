package j2w.team.mvp;

import android.content.Context;
import android.os.Bundle;

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
	 * 销毁当前页面
	 */
	public void activityFinish();
}
