package j2w.team.mvp.view.iview;

import android.os.Bundle;

/**
 * Created by sky on 15/2/1.MVP模式 VIEW 接口
 */
public interface J2WIView<T> {

	/** 获取TAG标记 **/
	public String initTag();

	/** 获取布局ID */
	public int layoutId();

	/** 初始化数据 **/
	public void initData(Bundle savedInstanceState);

	/** 获取Presenter **/
	public T getPresenter();

	/** 是否打开 **/
	public boolean isOpenEventBus();

	/** 跳转 **/
	public void intent2Activity(Class clazz);

	public void intent2Activity(Class clazz, Bundle bundle);

	public void intent2Activity(Class clazz, int requestCode);

	public void intent2Activity(Class clazz, Bundle bundle, int requestCode);

	/** 销毁当前页面 **/
	public void activityFinish();
}
