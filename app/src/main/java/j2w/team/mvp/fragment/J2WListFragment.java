package j2w.team.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.mvp.J2WIViewActivity;
import j2w.team.mvp.adapter.J2WAdapterItem;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/2/6. ListFragment 视图
 */
public abstract class J2WListFragment<T extends J2WIPresenter> extends J2WFragment<T> implements J2WIViewListFragment, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/** 数据集合 */
	private List			mList;

	/**
	 * 列表
	 */
	ListView				listView;

	/**
	 * ListView adapter
	 */
	protected ListAdapter	mListAdapter;

	/**
	 * 底部布局
	 */
	View					mFooterView;

	/**
	 * 获取布局ID
	 *
	 * @return 布局ID
	 */
	@Override public int layoutId() {
		if (notScroll()) {
			return R.layout.j2w_fragment_noscroll_list;
		} else {
			return R.layout.j2w_fragment_list;
		}
	}

	/**
	 * 设置 - ListView 头布局
	 *
	 * @return 布局ID
	 */
	@Override public int getHeaderLayout() {
		return 0;
	}

	/**
	 * 获取ListView
	 *
	 * @return 获取列表控件
	 */
	@Override public ListView getListView() {
		return listView;
	}

	/**
	 * 设置 - ListView 尾布局
	 *
	 * @return 布局ID
	 */
	@Override public int getFooterLayout() {
		return 0;
	}

	/**
	 * 设置 - Item类型数量
	 *
	 * @return 类型数量
	 */
	@Override public int getJ2WViewTypeCount() {
		return 1;
	}

	/**
	 * 设置 - Item类型布局
	 *
	 * @param position
	 *            下标
	 * @return 布局ID
	 */
	@Override public int getJ2WViewType(int position) {
		return 0;
	}

	/**
	 * 设置 - 设置陪
	 *
	 * @param type
	 *            类型
	 * @return 适配器
	 */
	@Override public J2WAdapterItem getJ2WAdapterItem(int type) {
		return null;
	}

	/**
	 * 初始化视图
	 *
	 * @param inflater
	 *            布局加载器
	 * @param container
	 *            父容器
	 */
	@Override public void initLayout(LayoutInflater inflater, ViewGroup container) {
		L.tag(initTag());
		L.i("Fragment-initLayout()");
		mContentView = inflater.inflate(R.layout.j2w_fragment_main, container, false);

		mViewAnimator = ButterKnife.findById(mContentView, android.R.id.home);

		// 获取View层接口
		J2WIViewActivity j2WIViewActivity = (J2WIViewActivity) getActivity();

		// 加载布局-初始化
		inflater.inflate(fragmentLoadingLayout() == 0 ? j2WIViewActivity.fragmentLoadingLayout() : fragmentLoadingLayout(), mViewAnimator, true);

		// 内容布局-初始化
		View view = inflater.inflate(layoutId(), mViewAnimator, true);
		if (view instanceof ListView) {
			listView = (ListView) view;
		} else {
			listView = (ListView) view.findViewById(android.R.id.list);
		}

		if (getHeaderLayout() != 0) {
			View headerView = LayoutInflater.from(getActivity()).inflate(getHeaderLayout(), null, false);
			listView.addHeaderView(headerView);
		}
		mFooterView = LayoutInflater.from(getActivity()).inflate(getFooterLayout() == 0 ? J2WHelper.getInstance().listFragmentFooterLayout() : getFooterLayout(), null, false);
		listView.addFooterView(mFooterView);
		// 设置点击事件
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		/** 初始化适配器 **/
		mListAdapter = new ListAdapter();
		listView.setAdapter(mListAdapter);
		// 空布局-初始化
		inflater.inflate(fragmentEmptyLayout() == 0 ? j2WIViewActivity.fragmentEmptyLayout() : fragmentEmptyLayout(), mViewAnimator, true);
		// 错误布局-初始化
		inflater.inflate(fragmentErrorLayout() == 0 ? j2WIViewActivity.fragmentErrorLayout() : fragmentErrorLayout(), mViewAnimator, true);
		listView.removeFooterView(mFooterView);
	}

	/**
	 * 初始化视图 - 无状态
	 */
	@Override public void initNotState(LayoutInflater inflater, ViewGroup container) {
		super.initNotState(inflater, container);
		// 内容布局-初始化
		if (mContentView instanceof ListView) {
			listView = (ListView) mContentView;
		} else {
			listView = (ListView) mContentView.findViewById(android.R.id.list);
		}

		if (getHeaderLayout() != 0) {
			View headerView = LayoutInflater.from(getActivity()).inflate(getHeaderLayout(), null, false);
			listView.addHeaderView(headerView);
		}
		mFooterView = LayoutInflater.from(getActivity()).inflate(getFooterLayout() == 0 ? J2WHelper.getInstance().listFragmentFooterLayout() : getFooterLayout(), null, false);
		listView.addFooterView(mFooterView);
		// 设置点击事件
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		/** 初始化适配器 **/
		mListAdapter = new ListAdapter();
		listView.setAdapter(mListAdapter);
		listView.removeFooterView(mFooterView);

	}

	/**
	 * 是否添加Fragment状态布局
	 *
	 * @return true 打开 false 关闭
	 */
	@Override public boolean fragmentState() {
		return true;
	}

	@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		L.tag(initTag());
		L.i("Fragment-onItemClick() position: " + position);
	}

	@Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}

	/**
	 * 判断适配器是否为空
	 *
	 * @return true 空 false 不为空
	 */
	public final boolean isAdapterNotNull() {
		return mListAdapter != null ? true : false;
	}

	/**
	 * 设置数据
	 *
	 * @param list
	 *            数据集合
	 */
	public void setData(List list) {
		L.tag(initTag());
		L.i("Fragment-setData(List)");
		if (list == null || list.size() < 1) {
			L.tag(initTag());
			L.i("Fragment-setData(List) return null");
			showEmpty();
			return;
		}
		if (isAdapterNotNull()) {
			mList = list;
			updateAdapter();
		}
	}

	/**
	 * 设置数据
	 *
	 * @param list
	 *            数据集合
	 * @param bool
	 *            是否展示空布局
	 */
	public void setData(List list, boolean bool) {
		L.tag(initTag());
		L.i("Fragment-setData(List)");
		if (list == null || list.size() < 1) {
			L.tag(initTag());
			L.i("Fragment-setData(List) return null");
			if (bool) {
				showEmpty();
			} else {
				mList = new ArrayList();
				showContent();
			}
			return;
		}
		if (isAdapterNotNull()) {
			mList = list;
			updateAdapter();
		}
	}

	/**
	 * 追加数据
	 *
	 * @param list
	 *            数据集合
	 */
	public void addData(List list) {
		L.tag(initTag());
		L.i("Fragment-addData(List)");
		if (list == null || list.size() < 1) {
			L.tag(initTag());
			L.i("Fragment-setData(List) return null");
			return;
		}
		if (isAdapterNotNull()) {
			mList.addAll(list);
			updateAdapter();
		}
	}

	/**
	 * 删除
	 *
	 * @param position
	 */
	public void delete(int position) {
		L.tag(initTag());
		L.i("Fragment-delete(position)");
		mList.remove(position);
		updateAdapter();
	}

	/**
	 * 删除所有
	 */
	public void deleteAll() {
		L.tag(initTag());
		L.i("Fragment-deleteAll()");
		mList.clear();
		updateAdapter();
	}

	/**
	 * 返回数据
	 * 
	 * @return
	 */
	public final List getData() {
		return isAdapterNotNull() ? mList : null;
	}

	/**
	 * 重新创建适配器
	 */
	public void resetAdapter() {
		/** 初始化适配器 **/
		mListAdapter = new ListAdapter();
		listView.setAdapter(mListAdapter);
	}

	/**
	 * 更新适配器 0 显示 进度, 1 显示 内容 2 显示 空 3 显示 错误,
	 */
	@Override public void updateAdapter() {
		L.tag(initTag());
		L.i("Fragment-updateAdapter()");
		if (isAdapterNotNull()) {
			List list = getData();
			if (mViewAnimator != null) {
				int state = mViewAnimator.getDisplayedChild();
				if ((state == 1 || state == 0) && list.isEmpty()) {
					showEmpty();
				} else if (state != 1 && !list.isEmpty()) {
					showContent();
				}
			}
			mListAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 添加底部布局
	 */
	public void addFooterItem() {
		if (mFooterView != null) {
			listView.removeFooterView(mFooterView);
			listView.addFooterView(mFooterView);
		}
	}

	/**
	 * 删除底部布局
	 */
	public void removeFooterItem() {
		if (mFooterView != null) {
			listView.removeFooterView(mFooterView);
		}
	}

	/**
	 * 取消滑动
	 *
	 * @return true 不滑动 false 滑动
	 */
	@Override public boolean notScroll() {
		return false;
	}

	/**
	 * 内部类-列表适配器
	 */
	final class ListAdapter extends BaseAdapter {

		/** 构造函数 **/
		public ListAdapter() {
			/** 初始化空数据源 **/
			mList = new ArrayList();
		}

		/** 列表数量 **/
		@Override public int getCount() {
			return mList.size();
		}

		/** item **/
		@Override public Object getItem(int position) {
			if (position > getCount() - 1) {
				return null;
			}
			return mList.get(position);
		}

		/** item id **/
		@Override public long getItemId(int position) {
			return position;
		}

		/** 返回显示类型 **/
		@Override public int getItemViewType(int position) {
			return getJ2WViewType(position);
		}

		/** 返回类型数量 **/
		@Override public int getViewTypeCount() {
			return getJ2WViewTypeCount();
		}

		/** getView **/
		@Override public View getView(int position, View convertView, ViewGroup parent) {
			J2WAdapterItem item = null;
			if (convertView == null) {
				int count = getViewTypeCount();// 获取类型数量
				if (count != 1) {
					int type = getItemViewType(position); // 获取类型
					item = getJ2WAdapterItem(type); // 返回该类型的item
				} else {
					item = getJ2WAdapterItem(); // 直接返回该类型
				}
				// 获取布局
				convertView = LayoutInflater.from(getActivity()).inflate(item.getItemLayout(), null, false);
				// 初始化布局
				item.init(convertView);
				// 设置Tag标记
				convertView.setTag(item);
			}
			// 获取item
			item = item == null ? (J2WAdapterItem) convertView.getTag() : item;
			// 绑定数据
			item.bindData(getItem(position), position, getCount());
			return convertView;
		}
	}
}
