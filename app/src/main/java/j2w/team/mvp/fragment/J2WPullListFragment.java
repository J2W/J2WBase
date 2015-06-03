package j2w.team.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.widget.swipeRefresh.SwipeRefreshLayout;
import j2w.team.mvp.J2WIViewActivity;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/3/13.
 */
public abstract class J2WPullListFragment<T extends J2WIPresenter> extends J2WListFragment<T> implements J2WIViewPullListFragment {

	/**
	 * 进度条控件
	 */
	SwipeRefreshLayout	swipe_container;

	/**
	 * 获取布局ID
	 *
	 * @return 布局ID
	 */
	@Override public int layoutId() {
		if (notScroll()) {
			return R.layout.j2w_pull_fragment_noscroll_list;
		} else {
			return R.layout.j2w_pull_fragment_list;
		}
	}

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
		View layoutView = inflater.inflate(layoutId(), mViewAnimator, true);

		swipe_container = (SwipeRefreshLayout) layoutView.findViewById(R.id.swipe_container);

		swipe_container.setJ2WPullListFragmentIView(this);

		listView = (ListView) layoutView.findViewById(android.R.id.list);

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
		// 内容布局-设置值
		mListAdapter = new ListAdapter();
		listView.setAdapter(mListAdapter);
		// 空布局-初始化
		inflater.inflate(fragmentEmptyLayout() == 0 ? j2WIViewActivity.fragmentEmptyLayout() : fragmentEmptyLayout(), mViewAnimator, true);
		// 错误布局-初始化
		inflater.inflate(fragmentErrorLayout() == 0 ? j2WIViewActivity.fragmentErrorLayout() : fragmentErrorLayout(), mViewAnimator, true);
	}

	/**
	 * 初始化视图 - 无状态
	 *
	 */
	@Override public void initNotState(LayoutInflater inflater, ViewGroup container) {
		super.initNotState(inflater, container);
		// 内容布局-初始化
		swipe_container = (SwipeRefreshLayout) mContentView.findViewById(R.id.swipe_container);
		swipe_container.setJ2WPullListFragmentIView(this);
	}

	/**
	 * 获取下拉刷新控件
	 */
	@Override public SwipeRefreshLayout getSwipeRefreshLayout() {
		return swipe_container;
	}

	/**
	 * 设置头部进度条 - 显示和隐藏
	 *
	 * @param bool
	 *            true 显示 false 隐藏
	 */
	@Override public void setRefreshing(boolean bool) {
		swipe_container.setRefreshing(bool);
	}

	/**
	 * 设置尾部进度条 - 显示和隐藏
	 *
	 * @param bool
	 *            true 显示 false 隐藏
	 */
	@Override public void setLoading(boolean bool) {
		swipe_container.setLoading(bool);
	}

	/**
	 * 打开头部进度条
	 */
	@Override public void openPullRefreshing() {
		swipe_container.openRefreshing();
	}

	/**
	 * 关闭头部进度条
	 */
	@Override public void closePullRefreshing() {
		swipe_container.closeRefreshing();
	}

	/**
	 * 打开尾部进度条
	 */
	@Override public void openPullLoading() {
		swipe_container.openLoading();
	}

	/**
	 * 关闭尾部进度条
	 */
	@Override public void closePullLoading() {
		swipe_container.closeLoading();
	}

	/**
	 * 设置数据
	 *
	 * @param list
	 */
	@Override public void setData(List list) {
		super.setData(list);
		swipe_container.setRefreshing(false);
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
		super.setData(list, bool);
		swipe_container.setRefreshing(false);
	}

	@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		super.onItemClick(parent, view, position, id);
	}

	@Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return super.onItemLongClick(parent, view, position, id);
	}

	/**
	 * 追加数据
	 *
	 * @param list
	 */
	public void addData(List list) {
		super.addData(list);
		swipe_container.setLoading(false);
	}

}
