package j2w.team.mvp.adapter;

import android.view.View;

/**
 * Created by sky on 15/2/6. 适配器
 */
public abstract class J2WAdapterItem<T> {

	/**
	 * 设置布局
	 * 
	 * @return 布局ID
	 */
	public abstract int getItemLayout();

	/**
	 * 初始化控件
	 * 
	 * @param contentView
	 *            ItemView
	 */
	public abstract void init(View contentView);

	/**
	 * 绑定数据
	 * 
	 * @param t
	 *            数据类型泛型
	 * @param position
	 *            下标
	 */
	public abstract void bindData(T t, int position);
}
