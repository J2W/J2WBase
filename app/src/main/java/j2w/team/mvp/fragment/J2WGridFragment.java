package j2w.team.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.ButterKnife;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.mvp.J2WIViewActivity;
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/4/9. J2WGridFragment 视图
 */
public abstract class J2WGridFragment<T extends J2WIPresenter> extends J2WListFragment<T> implements J2WIViewGridFragment, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/**
	 * 列表
	 */
	GridView	gridView;

	/**
	 * 获取布局ID
	 *
	 * @return 布局ID
	 */
	@Override public int layoutId() {
		if (notScroll()) {
			return R.layout.j2w_fragment_noscroll_grid;
		} else {
			return R.layout.j2w_fragment_grid;
		}
	}

	/**
	 * 获取ListView
	 *
	 * @return 获取列表控件
	 */
	@Override public GridView getGridView() {
		return gridView;
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

		if (view instanceof GridView) {
			gridView = (GridView) view;
		} else {
			gridView = (GridView) view.findViewById(R.id.grid);
		}
		// 设置点击事件
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(this);
		/** 初始化适配器 **/
		mListAdapter = new ListAdapter();
		gridView.setAdapter(mListAdapter);
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
		super.initNotState(inflater,container);
		if (mContentView instanceof GridView) {
			gridView = (GridView) mContentView;
		} else {
			gridView = (GridView) mContentView.findViewById(R.id.grid);
		}
		// 设置点击事件
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(this);
		/** 初始化适配器 **/
		mListAdapter = new ListAdapter();
		gridView.setAdapter(mListAdapter);
	}
}
