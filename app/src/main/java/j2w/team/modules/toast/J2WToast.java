package j2w.team.modules.toast;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by wungko on 15/3/17. 弱交互Tost 消息弹窗
 */
public class J2WToast {

	private static Toast	mToast	= null;

	/**
	 * 简单Toast 消息弹出
	 * 
	 * @param msg
	 */
	public static void show(final String msg) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			J2WHelper.getMainLooper().execute(new Runnable() {

				@Override public void run() {
					showToast(J2WHelper.getScreenHelper().currentActivity(), msg, Toast.LENGTH_SHORT);
				}
			});
		} else {
			showToast(J2WHelper.getScreenHelper().currentActivity(),msg,Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 弹出提示
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	protected static void showToast(Context context, String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}

		mToast.show();
	}
}
