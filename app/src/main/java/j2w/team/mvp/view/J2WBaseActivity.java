package j2w.team.mvp.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.common.log.L;
import j2w.team.mvp.view.iview.J2WActivityIView;

/**
 * Created by sky on 15/1/26. activity 视图
 */
public abstract class J2WBaseActivity extends FragmentActivity implements J2WActivityIView {

	/** 初始化视图 **/
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("initData()");
	}

	/** onCreate 无法重写 **/
	@Override protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 是否固定竖屏 **/
		if (isFixedVerticalScreen()) {
			/** 竖屏显示 **/
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		L.tag(initTag());
		L.i("onCreate()");
		/** 初始化视图 **/
		setContentView(layoutId());
        /** 初始化所有组建 **/
        ButterKnife.inject(this);
		/** 添加到堆栈 **/
		J2WHelper.getScreenHelper().pushActivity(this);
		/** 初始化视图组建 **/
        initData(savedInstanceState);
	}

	@Override protected void onStart() {
		super.onStart();
		L.tag(initTag());
		L.i("onStart()");
	}

	@Override protected void onResume() {
		super.onResume();
		L.tag(initTag());
		L.i("onResume()");
	}

	@Override protected void onPause() {
		super.onPause();
		L.tag(initTag());
		L.i("onPause()");
	}

	@Override protected void onRestart() {
		super.onRestart();
		L.tag(initTag());
		L.i("onRestart()");
	}

	@Override protected void onStop() {
		super.onStop();
		L.tag(initTag());
		L.i("onStop()");
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		L.tag(initTag());
		L.i("onDestroy()");
		/** 从堆栈里移除 **/
		J2WHelper.getScreenHelper().popActivity(this);
	}

	/**
	 * 是否固定竖屏
	 */
	@Override public boolean isFixedVerticalScreen() {
		return true;
	}
}
