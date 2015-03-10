package j2w.team.mvp.view;

import android.os.Bundle;
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
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.presenter.J2WPresenter;
import j2w.team.mvp.view.iview.J2WListFragmentIView;

/**
 * Created by sky on 15/2/6. ListFragment 视图
 */
public abstract class J2WBaseListFragment<T extends J2WIPresenter> extends J2WBaseFragment<T> implements J2WListFragmentIView, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/**
	 * 设置布局ID 禁止重写
	 * 
	 * @return
	 */
	@Override public final int layoutId() {
		return R.layout.j2w_fragment_list;
	}

	/**
	 * 设置头布局
	 * 
	 * @return
	 */
	@Override public int getHeaderLayout() {
		return 0;
	}

	/**
	 * 设置尾布局
	 * 
	 * @return
	 */
	@Override public int getFooterLayout() {
		return 0;
	}

	/**
	 * 设置类型数量
	 * 
	 * @return
	 */
	@Override public int getJ2WViewTypeCount() {
		return 1;
	}


	/**
	 * 返回类型
	 * 
	 * @param position
	 *            当前坐标
	 * @return
	 */
	@Override public int getJ2WViewType(int position) {
		return 0;
	}

	@Override public J2WBaseAdapterItem getJ2WAdapterItem(int type) {
		return null;
	}

	@Override public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-onCreateView()");
		mContentView = inflater.inflate(R.layout.j2w_fragment_main, container, false);

		mViewAnimator = ButterKnife.findById(mContentView, android.R.id.home);

		// 加载布局-初始化
		mViewAnimator.addView(inflater.inflate(initLoadingLayout(), null, false));

		// 内容布局-初始化
		ListView listView = (ListView) inflater.inflate(layoutId(), null, false);

		if (getHeaderLayout() != 0) {
			View headerView = LayoutInflater.from(getActivity()).inflate(getHeaderLayout(), null, false);
			listView.addHeaderView(headerView);
		}

		if (getFooterLayout() != 0) {
			View footerView = LayoutInflater.from(getActivity()).inflate(getFooterLayout(), null, false);
			listView.addFooterView(footerView);
		}
		// 设置点击事件
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		mViewAnimator.addView(listView);
		// 内容布局-设置值
		mListAdapter = new ListAdapter();
		listView.setAdapter(mListAdapter);
		// 空布局-初始化
		mViewAnimator.addView(inflater.inflate(initEmptyLayout(), null, false));
		// 错误布局-初始化
		mViewAnimator.addView(inflater.inflate(initErrorLayout(), null, false));

		ButterKnife.inject(this, mContentView);
		return mContentView;
	}

	@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		L.tag(initTag());
		L.i("Fragment-onItemClick() position: " + position);
	}

	@Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}

	/**
	 * ListView adapter
	 */
	private ListAdapter	mListAdapter;

	/**
	 * adapter是否为空
	 * 
	 * @return true 不为空 false 为空
	 */
	public final boolean isAdapterNotNull() {
		L.tag(initTag());
		L.i("Fragment-isAdapterNotNull()");
		return mListAdapter != null ? true : false;
	}

	/**
	 * 设置数据
	 * 
	 * @param list
	 */
	public final void setData(List list) {
		L.tag(initTag());
		L.i("Fragment-setData(List)");
		if (isAdapterNotNull()) {
			mListAdapter.setData(list);
			updataList();
		}
	}

	/**
	 * 追加数据
	 * 
	 * @param list
	 */
	public final void addData(List list) {
		L.tag(initTag());
		L.i("Fragment-addData(List)");
		if (isAdapterNotNull()) {
			mListAdapter.addData(list);
			updataList();
		}
	}

	/**
	 * 返回数据
	 * 
	 * @return
	 */
	public final List getData() {
		L.tag(initTag());
		L.i("Fragment-getData()");
		return isAdapterNotNull() ? mListAdapter.mList : null;
	}

	/**
	 * 更新列表 0 显示 进度 1 显示 内容 2 显示 空 3 显示 错误
	 */
	public final void updataList() {
		L.tag(initTag());
		L.i("Fragment-updataList()");
		if (isAdapterNotNull()) {
			int state = mViewAnimator.getDisplayedChild();
			List list = getData();
			if (state == 1 && list.isEmpty()) {
				showEmpty();
			} else if (state != 2 && !list.isEmpty()) {
                showContent();
			}
            mListAdapter.notifyDataSetChanged();
        }
	}

	/**
	 * 内部类-列表适配器
	 */
	private final class ListAdapter extends BaseAdapter {

		/** 数据集合 */
		private List	mList;

		/** 构造函数 **/
		private ListAdapter() {
			/** 初始化空数据源 **/
			mList = new ArrayList();
		}

		/** 设置数据源 **/
		private void setData(List list) {
			mList = list;
		}

		/** 追加数据源 **/
		private void addData(List list) {
			mList.addAll(list);
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
			J2WBaseAdapterItem item = null;
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
			item = item == null ? (J2WBaseAdapterItem) convertView.getTag() : item;
			// 绑定数据
			item.bindData(mList.get(position));
			return convertView;
		}
	}
}
