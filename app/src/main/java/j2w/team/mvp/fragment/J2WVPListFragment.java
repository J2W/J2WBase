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
import j2w.team.mvp.presenter.J2WIPresenter;

/**
 * Created by sky on 15/3/26.
 */
public abstract class J2WVPListFragment <T extends J2WIPresenter> extends J2WVPFragment<T> implements J2WIViewListFragment, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    /**
     * ListView adapter
     */
    protected ListAdapter	mListAdapter;

    /**
     * 获取布局ID
     *
     * @return 布局ID
     */
    @Override public int layoutId() {
        return R.layout.j2w_fragment_list;
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
        mViewAnimator.addView(inflater.inflate(j2WIViewActivity.fragmentLoadingLayout(), null, false));

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
        /** 初始化适配器 **/
        mListAdapter = new ListAdapter();
        listView.setAdapter(mListAdapter);
        // 空布局-初始化
        mViewAnimator.addView(inflater.inflate(j2WIViewActivity.fragmentEmptyLayout(), null, false));
        // 错误布局-初始化
        mViewAnimator.addView(inflater.inflate(j2WIViewActivity.fragmentErrorLayout(), null, false));

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
        L.tag(initTag());
        L.i("Fragment-isAdapterNotNull()");
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
            return;
        }
        if (isAdapterNotNull()) {
            mListAdapter.setData(list);
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
            mListAdapter.addData(list);
            updateAdapter();
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
        return isAdapterNotNull() ? mListAdapter.getData() : null;
    }

    /**
     * 更新适配器 0 显示 进度, 1 显示 内容 2 显示 空 3 显示 错误,
     */
    @Override public void updateAdapter() {
        L.tag(initTag());
        L.i("Fragment-updataList()");
        if (isAdapterNotNull()) {
            int state = mViewAnimator.getDisplayedChild();
            List list = getData();
            if ((state == 1 || state == 0) && list.isEmpty()) {
                showEmpty();
            } else if (state != 1 && !list.isEmpty()) {
                showContent();
            }
            mListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 内部类-列表适配器
     */
    final class ListAdapter extends BaseAdapter {

        /** 数据集合 */
        private List	mList;

        /** 构造函数 **/
        public ListAdapter() {
            /** 初始化空数据源 **/
            mList = new ArrayList();
        }

        /** 设置数据源 **/
        private void setData(List list) {
            L.tag(initTag());
            L.i("ListAdapter.setData()" + list);
            mList = list;
        }

        /** 追加数据源 **/
        private void addData(List list) {
            L.tag(initTag());
            L.i("addData()" + list);
            mList.addAll(list);
        }

        private List getData() {
            L.tag(initTag());
            L.i("adapter - getData()" + mList);
            return mList;
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
            item.bindData(mList.get(position), position);
            return convertView;
        }
    }
}