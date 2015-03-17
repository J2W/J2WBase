package j2w.team.modules.toast;

import android.widget.Toast;

import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by wungko on 15/3/17. 弱交互Tost 消息弹窗
 */
public class J2WToast {

	/**
	 * 简单Toast 消息弹出
	 * 
	 * @param msg
	 */
	public static void show(String msg) {
		Toast.makeText(J2WHelper.getScreenHelper().currentActivity(), msg, Toast.LENGTH_SHORT).show();
	}
}
