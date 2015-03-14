package j2w.team.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.common.widget.swipeRefresh.SwipeRefreshLayout;
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.view.iview.J2WPullListFragmentIView;

/**
 * Created by sky on 15/3/13.
 */
public abstract class J2WBasePullListFragment<T extends J2WIPresenter> extends J2WBaseListFragment<T> implements J2WPullListFragmentIView {

	private SwipeRefreshLayout	swipe_container;

	@Override public int layoutId() {
		return R.layout.j2w_pull_fragment_list;
	}

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.tag(initTag());
        L.i("Fragment-onCreateView()");
        setHasOptionsMenu(true);
        mContentView = inflater.inflate(R.layout.j2w_fragment_main, container, false);

        mViewAnimator = ButterKnife.findById(mContentView, android.R.id.home);

        // 加载布局-初始化
        mViewAnimator.addView(inflater.inflate(initLoadingLayout(), null, false));

        // 内容布局-初始化
        View layoutView = inflater.inflate(layoutId(), null, false);

        swipe_container = (SwipeRefreshLayout) layoutView.findViewById(R.id.swipe_container);

        swipe_container.setJ2WPullListFragmentIView(this);

        ListView listView = (ListView) layoutView.findViewById(android.R.id.list);

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
        mViewAnimator.addView(layoutView);
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

    @Override public void setLoadingColor(int colorRes1, int colorRes2, int colorRes3, int colorRes4) {
		swipe_container.setColor(colorRes1, colorRes2, colorRes3, colorRes4);
	}

    /**
     * 设置数据
     *
     * @param list
     */

    @Override
    public void setData(List list) {
        super.setData(list);
        swipe_container.setRefreshing(false);
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
