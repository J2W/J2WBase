package j2w.team.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import butterknife.ButterKnife;
import j2w.team.common.log.L;
import j2w.team.mvp.view.iview.J2WFragmentIView;

/**
 * Created by sky on 15/2/1.
 */
public abstract class J2WBaseFragment extends Fragment implements J2WFragmentIView {

    /** view **/
	private View mContentView;
    /** view **/
    private ViewAnimator mViewAnimator;

    /** 初始化视图 **/
	@Override public void initView(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-initView()");
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

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("Fragment-onCreateView()");
		mContentView = inflater.inflate(layoutId(), container, false);

        mViewAnimator = ButterKnife.findById(mContentView,android.R.id.home);

        //加载布局-初始化

        //内容布局-初始化

        //空布局-初始化

        //错误布局-初始化


		ButterKnife.inject(this, mContentView);
		return mContentView;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		L.tag(initTag());
		L.i("Fragment-onActivityCreated()");
		initView(savedInstanceState);
	}

	@Override public void onStart() {
		super.onStart();
		L.tag(initTag());
		L.i("Fragment-onStart()");
	}

	@Override public void onResume() {
		super.onResume();
		L.tag(initTag());
		L.m("Fragment-onResume()");
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
		ButterKnife.reset(this);// 清空注解view
	}

	@Override public void onDestroy() {
		super.onDestroy();
		L.tag(initTag());
		L.m("Fragment-onDestroy()");
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

    @Override
    public int initLoadingLayout() {
        return 0;
    }

    @Override
    public int initContentLayout() {
        return 0;
    }

    @Override
    public int initEmptyLayout() {
        return 0;
    }

    @Override
    public int initErrorLayout() {
        return 0;
    }

    @Override
    public void onLoadingLayout(View view) {

    }

    @Override
    public void onContentLayout(View view) {

    }

    @Override
    public void onEmptyLayout(View view) {

    }

    @Override
    public void onErrorLaout(View view) {

    }

    @Override
    public void onRefresh() {

    }
}
