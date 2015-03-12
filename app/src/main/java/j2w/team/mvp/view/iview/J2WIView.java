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
}
