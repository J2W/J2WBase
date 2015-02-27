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
public abstract class J2WBaseCustomActoinBarActivity<T extends J2WIPresenter> extends ActionBarActivity implements J2WActionBarIView, View.OnClickListener {

	/** 标题栏 **/
	private ActionBar actionBar;

    /** 业务逻辑对象 **/
    private T presenter = null;

	/** 初始化视图 **/
	@Override public void initData(Bundle savedInstanceState) {
		L.tag(initTag());
		L.i("initData()");
	}

    @Override public final T getPresenter() {
        if (presenter == null) {
            synchronized (this) {
                /** 创建业务类**/
                presenter = J2WPresenterUtils.createPresenter(getClass(),this);
                L.tag(initTag());
                L.i("Presneter初始化完");
            }
        }
        return presenter;
    }

	@Override public void onClick(View v) {
		switch (v.getId()) {
		case android.R.id.button1:
			L.tag(initTag());
			L.i("onActionBarLeft()");
			onActionBarLeft(v);
			break;
		case android.R.id.button2:
			L.tag(initTag());
			L.i("onActionBarRight()");
			onActionBarRight(v);
			break;
		}
	}

	/** 是否固定竖屏 **/
	protected boolean isFixedVerticalScreen() {
		return true;
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
        /** 切断关联 **/
        if (presenter != null) {
            presenter.detach();
        }
		/** 从堆栈里移除 **/
		J2WHelper.getScreenHelper().popActivity(this);
	}

	@Override public void initActionBar() {
		L.tag(initTag());
		L.i("initActionBar()");
		actionBar = getSupportActionBar();
		actionBar.setCustomView(actionBarLayoutID()); // 设置actionbar布局ID
		actionBar.setDisplayShowCustomEnabled(true); // 使自定义的普通View能在title栏显示
		actionBar.setDisplayShowHomeEnabled(false);// 使左上角图标是否显示
		actionBar.setDisplayHomeAsUpEnabled(false);// 是否显示返回图标
		actionBar.setDisplayShowTitleEnabled(false);// 隐藏/显示Tittle true 显示 false
		// 获取布局ID
		RelativeLayout custom = ButterKnife.findById(actionBar.getCustomView(), android.R.id.custom);
		// 设置点击事件
		ButterKnife.findById(actionBar.getCustomView(), android.R.id.button1).setOnClickListener(this);
		((TextView) ButterKnife.findById(actionBar.getCustomView(), android.R.id.title)).setText(actionBarTitle() != null ? actionBarTitle() : getResources().getString(
				R.string.actoinbar_default_title_name));

		if (getActionBarRightValue() == null) {
			L.tag(initTag());
			L.i("initActionBar():getActionBarRightValue()为空");
			return;
		}

		if (getActionBarRightValue() instanceof Integer) {
			L.tag(initTag());
			L.i("initActionBar():(getActionBarRightValue() instanceof Interger) 初始化:ImageView 设置到 actionbar");
			ImageView imageView = new ImageView(actionBar.getCustomView().getContext());
			imageView.setId(android.R.id.button2);
			int dimen_default = getResources().getDimensionPixelOffset(R.dimen.actoinbar_dimen_default);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			imageView.setImageResource((Integer) getActionBarRightValue());
			imageView.setPadding(dimen_default, dimen_default, dimen_default, dimen_default);
			custom.addView(imageView, layoutParams);
			imageView.setOnClickListener(this);
		} else if (getActionBarRightValue() instanceof String) {
			L.tag(initTag());
			L.i("initActionBar():(getActionBarRightValue() instanceof String) 初始化:TextView 设置到 actionbar");
			TextView textView = new TextView(actionBar.getCustomView().getContext());
			textView.setId(android.R.id.button2);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			textView.setText((String) getActionBarRightValue());
			int dimen_default = getResources().getDimensionPixelOffset(R.dimen.actoinbar_dimen_default);
			int padding_2 = getResources().getDimensionPixelOffset(R.dimen.actoinbar_dimen_hight);
			layoutParams.setMargins(0, 0, dimen_default, 0);
			textView.setPadding(dimen_default, padding_2, 0, padding_2);
			textView.setTextColor(getResources().getColor(android.R.color.white));
			custom.addView(textView, layoutParams);
			textView.setOnClickListener(this);
		} else if (getActionBarRightValue() instanceof View) {
			L.tag(initTag());
			L.i("initActionBar():(getActionBarRightValue() instanceof View) 初始化:View 设置到 actionbar");
			View view = (View) getActionBarRightValue();
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			int dimen_default = getResources().getDimensionPixelOffset(R.dimen.actoinbar_dimen_default);
			layoutParams.setMargins(0, 0, dimen_default, 0);
			custom.addView(view, layoutParams);
			custom.setOnClickListener(this);
		}
	}

	@Override public void onActionBarRight(View view) {

	}

	@Override public void onActionBarLeft(View view) {
		finish();
	}

	@Override public Object getActionBarRightValue() {
		return null;
	}

	@Override public String actionBarTitle() {
		return getResources().getString(R.string.actoinbar_default_title_name);
	}

	@Override public void setActionBarTitle(String value) {
		L.tag(initTag());
		L.i("setActionBarTitle(value)");
		TextView view = ButterKnife.findById(actionBar.getCustomView(), android.R.id.title);
		if (view == null) {
			L.tag(initTag());
			L.i("setActionBarTitle(value) : 没有找到actionbar里的TextView");
			return;
		}
		view.setText(value);
	}
}
