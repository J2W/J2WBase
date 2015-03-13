package j2w.team.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import j2w.team.R;
import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.presenter.J2WPresenter;
import j2w.team.mvp.presenter.J2WPresenterUtils;
import j2w.team.mvp.presenter.Presenter;
import j2w.team.mvp.view.iview.J2WFragmentIView;

/**
 * Created by sky on 15/2/1. fragment 视图
 */
public abstract class J2WBaseFragment<T extends J2WIPresenter> extends Fragment implements J2WFragmentIView, View.OnTouchListener {

	/**
	 * view *
	 */
	View			mContentView;

	/**
	 * view *
	 */
	ViewAnimator	mViewAnimator;

	/**
	 * 业务逻辑对象 *
	 */
	private T		presenter		= null;

	private boolean	isDelayedData	= false;

	@Override public String initTag() {
		return getClass().getSimpleName();
	}

	/**
	 * 初始化视图 *
	 */
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-initData()");
	}

	@Override public final T getPresenter() {
		if (presenter == null) {
			synchronized (this) {
				/** 创建业务类 **/
				presenter = J2WPresenterUtils.createPresenter(getClass(), this);
				L.tag(initTag());
				L.i("Presneter初始化完");
			}
		}
		return presenter;
	}

	@Override public boolean isOpenEventBus() {
		return false;
	}

    @Override public void intent2Activity(Class clazz) {
        J2WHelper.intentTo(clazz);
    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle) {
        J2WHelper.intentTo(clazz,bundle);
    }

    @Override
    public void intent2Activity(Class clazz, int requestCode) {
        J2WHelper.intentTo(clazz,requestCode);
    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle, int requestCode) {
        J2WHelper.intentTo(clazz,bundle,requestCode);
    }

    @Override public void activityFinish() {
        L.tag(initTag());
        L.i("activityFinish()");
        getActivity().finish();
    }

    @Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		L.tag(initTag());
		L.i("Fragment-onAttach()");
	}

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onCreate()");
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-onCreateView()");
		/** 打开开关触发菜单项 **/
		setHasOptionsMenu(true);
		mContentView = inflater.inflate(R.layout.j2w_fragment_main, container, false);

		mViewAnimator = ButterKnife.findById(mContentView, android.R.id.home);

		// 加载布局-初始化
		mViewAnimator.addView(inflater.inflate(initLoadingLayout(), null, false));
		// 内容布局-初始化
		mViewAnimator.addView(inflater.inflate(layoutId(), null, false));
		// 空布局-初始化
		mViewAnimator.addView(inflater.inflate(initEmptyLayout(), null, false));
		// 错误布局-初始化
		mViewAnimator.addView(inflater.inflate(initErrorLayout(), null, false));
		ButterKnife.inject(this, mContentView);

		return mContentView;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onActivityCreated()");
		initData(savedInstanceState);
	}

	@Override public void onStart() {
		super.onStart();
		L.tag(initTag());
		L.i("Fragment-onStart()");
	}

	@Override public void onResume() {
		super.onResume();
		L.tag(initTag());
		L.i("Fragment-onResume()");
		if (isOpenEventBus()) {
			if (!EventBus.getDefault().isRegistered(this)) {
				EventBus.getDefault().register(this);
			}
		}
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		L.tag(initTag());
		L.i("Fragment-onSaveInstanceState()");

	}

	@Override public void onPause() {
		super.onPause();
		L.tag(initTag());
		L.i("Fragment-onSaveInstanceState()");
	}

	@Override public void onStop() {
		super.onStop();
		L.tag(initTag());
		L.i("Fragment-onStop()");
	}

	@Override public void onDestroyView() {
		super.onDestroyView();
		L.tag(initTag());
		L.i("Fragment-onDestroyView()");
		/** 切断关联 **/
		if (presenter != null) {
			presenter.detach();
		}
		ButterKnife.reset(this);// 清空注解view
	}

	@Override public void onDestroy() {
		super.onDestroy();
		L.tag(initTag());
		L.m("Fragment-onDestroy()");
		if (isOpenEventBus()) {
			if (EventBus.getDefault().isRegistered(this)) {
				EventBus.getDefault().unregister(this);
			}
		}
	}

	@Override public void onDetach() {
		super.onDetach();
		L.tag(initTag());
		L.i("Fragment-onDetach()");
	}

	@Override public View getContentView() {
		L.tag(initTag());
		L.i("Fragment-getContentView()");
		return mContentView;
	}

	@Override public final void isDelayedData() {
		if (isDelayedData) {
			return;
		}
		L.tag(initTag());
		L.i("Fragment-isDelayedData()");
		// 为了只初始化一次
		isDelayedData = true;
		initDelayedData();
		// 更新actionbar
		updateActionBar();
	}

	@Override public void initDelayedData() {
		L.tag(initTag());
		L.i("Fragment-initDelayedData()");
	}

	@Override public void updateActionBar() {
		L.tag(initTag());
		L.i("Fragment-updateActionBar()");
	}

	@Override public int initLoadingLayout() {
		return R.layout.j2w_fragment_loading;
	}

	@Override public int initEmptyLayout() {
		return R.layout.j2w_fragment_empty;
	}

	@Override public int initErrorLayout() {
		return R.layout.j2w_fragment_error;
	}

	@Override public void showLoading() {
		L.tag(initTag());
		L.i("Fragment-loading()");
		show(0);
	}

	@Override public void showContent() {
		L.tag(initTag());
		L.i("Fragment-content()");
		show(1);
	}

	@Override public void showEmpty() {
		L.tag(initTag());
		L.i("Fragment-empty()");
		show(2);
	}

	@Override public void showError() {
		L.tag(initTag());
		L.i("Fragment-error()");
		show(3);
	}

	@Override public boolean isTouch() {
		return true;
	}

	/**
	 * 防止事件穿透
	 */

	@Override public boolean onTouch(View v, MotionEvent event) {
		return isTouch();
	}

	/**
	 * 显示 * @param showState 0，1，2，3
	 */
	private final void show(int showState) {
		if (mViewAnimator == null) {
			return;
		}
		int displayedChild = mViewAnimator.getDisplayedChild();
		if (displayedChild == showState) {
			return;
		}
		mViewAnimator.setDisplayedChild(showState);
	}
}
