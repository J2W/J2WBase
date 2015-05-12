package j2w.team.mvp.fragment;

import android.widget.ListView;

import java.util.List;

import j2w.team.mvp.adapter.J2WAdapterItem;

/**
 * Created by sky on 15/2/6. listfragment 视图接口
 */
public interface J2WIViewListFragment extends J2WIViewFragment {

	/**
	 * 获取ListView
	 * 
	 * @return 获取列表控件
	 */
	ListView getListView();

	/**
	 * 设置 - ListView 头布局
	 * 
	 * @return 布局ID
	 */
	int getHeaderLayout();

	/**
	 * 设置 - ListView 尾布局
	 * 
	 * @return 布局ID
	 */
	int getFooterLayout();

	/**
	 * 设置 - Item类型数量
	 *
	 * @return 类型数量
	 */
	int getJ2WViewTypeCount();

	/**
	 * 设置 - Item类型布局
	 * 
	 * @param position
	 *            下标
	 * @return 布局ID
	 */
	int getJ2WViewType(int position);

	/**
	 * 设置 - 适配器
	 * 
	 * @return 适配器
	 */
	J2WAdapterItem getJ2WAdapterItem();

	/**
	 * 设置 - 设置陪
	 * 
	 * @param type
	 *            类型
	 * @return 适配器
	 */
	J2WAdapterItem getJ2WAdapterItem(int type);

	/**
	 * 判断适配器是否为空
	 * 
	 * @return true 空 false 不为空
	 */
	boolean isAdapterNotNull();

	/**
	 * 设置数据
	 * 
	 * @param list
	 *            数据集合
	 */
	void setData(List list);

	/**
	 * 设置数据
	 *
	 * @param list
	 *            数据集合
	 * @param bool
	 *            是否展示空布局
	 */
	void setData(List list, boolean bool);

	/**
	 * 追加数据
	 * 
	 * @param list
	 *            数据集合
	 */
	void addData(List list);

	/**
	 * 删除
	 * 
	 * @param position
	 */
	void delete(int position);

	/**
	 * 删除所有
	 */
	void deleteAll();

	/**
	 * 获取数据
	 * 
	 * @return 数据集合
	 */
	List getData();

	/**
	 * 重新创建适配器
	 */
	void resetAdapter();

	/**
	 * 更新适配器 0 显示 进度, 1 显示 内容 2 显示 空 3 显示 错误,
	 */
	void updateAdapter();

	/**
	 * 取消滑动
	 * 
	 * @return true 不滑动 false 滑动
	 */
	boolean notScroll();

	/**
	 * 添加底部布局
	 */
	void addFooterItem();

	/**
	 * 删除底部布局
	 */
	void removeFooterItem();
}
