package j2w.team.mvp.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import j2w.team.base.J2WHelper;
import j2w.team.common.log.L;

/**
 * Created by sky on 15/1/26.
 */
public abstract class J2WBaseActivity extends ActionBarActivity implements J2WIView {

	/**
	 * TAG标记
	 */
	public abstract String getTag();

	/** 初始化视图 **/
	@Override public void initView() {
		L.tag(getTag());
		L.i("initView()");
	}

	@Override protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.tag(getTag());
		L.i("onCreate()");
        /** 添加到堆栈 **/
        J2WHelper.getScreenHelper().pushActivity(this);
        /** 初始化视图 **/
		initView();
	}

    @Override protected void onStart() {
		super.onStart();
		L.tag(getTag());
		L.i("onStart()");
	}

	@Override protected void onResume() {
		super.onResume();
		L.tag(getTag());
		L.i("onResume()");
	}

	@Override protected void onPause() {
		super.onPause();
		L.tag(getTag());
		L.i("onPause()");
	}

	@Override protected void onRestart() {
		super.onRestart();
		L.tag(getTag());
		L.i("onRestart()");
	}

	@Override protected void onStop() {
		super.onStop();
		L.tag(getTag());
		L.i("onStop()");
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		L.tag(getTag());
		L.i("onDestroy()");
        /** 从堆栈里移除 **/
        J2WHelper.getScreenHelper().popActivity(this);
	}
}
