package j2w.team.mvp.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import butterknife.ButterKnife;
import j2w.team.R;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WIPresenter;
import j2w.team.mvp.presenter.J2WPresenter;
import j2w.team.mvp.presenter.J2WPresenterUtils;
import j2w.team.mvp.presenter.Presenter;
import j2w.team.mvp.view.iview.J2WActionBarIView;

/**
 * Created by sky on 15/2/5. actionbarActivity 视图
 */
public abstract class J2WBaseActoinBarActivity<T extends J2WIPresenter> extends ActionBarActivity implements J2WActionBarIView {

	/** 标题栏 **/
    ActionBar actionBar;

	/** 业务逻辑对象 **/
	private T			presenter	= null;

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

	/**
	 * 是否固定竖屏
	 */
	@Override public boolean isFixedVerticalScreen() {
		return true;
	}

	/** onCreate 无法重写 **/
	@Override protected void onCreate(Bundle savedInstanceState) {
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
        /** 初始化标题栏**/
        initActionBar();
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
		/** 切断关联 **/
		if (presenter != null) {
			presenter.detach();
		}
		/** 从堆栈里移除 **/
		J2WHelper.getScreenHelper().popActivity(this);
	}

	@Override public final void initActionBar() {
		L.tag(initTag());
		L.i("initActionBar()");

		actionBar = getSupportActionBar();
		actionBar.setCustomView(actionBarLayoutID()); // 设置actionbar布局ID
		actionBar.setDisplayShowCustomEnabled(true); // 使自定义的普通View能在title栏显示
		actionBar.setDisplayShowHomeEnabled(false);// 使左上角图标是否显示
		actionBar.setDisplayHomeAsUpEnabled(false);// 是否显示返回图标
		actionBar.setDisplayShowTitleEnabled(false);// 隐藏/显示Tittle true 显示 false
	}
}
