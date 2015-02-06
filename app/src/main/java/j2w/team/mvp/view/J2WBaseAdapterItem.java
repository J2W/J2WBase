package j2w.team.mvp.view;

import android.view.View;

/**
 * Created by sky on 15/2/6. 适配器
 */
public abstract class J2WBaseAdapterItem<T> {

	/** 设置布局文件 **/
	public abstract int getItemLayout();

	/** 初始化控件 **/
	public abstract void init(View contentView);

	/** 绑定数据 **/
	public abstract void bindData(T t);
}
