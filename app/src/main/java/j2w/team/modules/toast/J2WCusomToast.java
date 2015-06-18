package j2w.team.modules.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import j2w.team.mvp.presenter.J2WHelper;

/**
 * @创建人 sky
 * @创建时间 15/6/18 下午4:03
 * @类描述 自定义Toast
 */
public abstract class J2WCusomToast {

	private View	v;

	protected Toast	mToast	= null;

	/**
	 * 布局ID
	 * 
	 * @return
	 */
	public abstract int layoutId();

	public abstract void init(View view, String msg);

	/**
	 * 位置
	 * 
	 * @return 默认 居中
	 */
	public int getGravity() {
		return Gravity.CENTER;
	}

	/**
	 * 设置显示时间
	 * 
	 * @return
	 */
	public int getDuration() {
		return Toast.LENGTH_SHORT;
	}

	/**
	 * 显示
	 * 
	 * @param msg
	 */
	public void show(String msg) {
		if (mToast == null) {
			mToast = new Toast(J2WHelper.getScreenHelper().currentActivity());
			LayoutInflater inflate = (LayoutInflater) J2WHelper.getScreenHelper().currentActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflate.inflate(layoutId(), null);
			mToast.setView(v);
			init(v, msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
			mToast.setGravity(getGravity(),0,0);
		} else {
			mToast.setDuration(Toast.LENGTH_SHORT);
			mToast.setGravity(getGravity(),0,0);
			init(v, msg);
		}
		mToast.show();
	}
}
