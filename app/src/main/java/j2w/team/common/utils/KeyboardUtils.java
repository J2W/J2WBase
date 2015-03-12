package j2w.team.common.utils;

/**
 * Created by sky on 15/3/12.
 */
import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils {

	/***
	 * 隐藏键盘
	 *
	 * @param acitivity
	 */
	public static void hideSoftInput(Activity acitivity) {
		InputMethodManager imm = (InputMethodManager) acitivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(acitivity.getWindow().getDecorView().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/***
	 * 显示键盘
	 *
	 * @param acitivity
	 * @param et
	 */
	public static void showSoftInput(Activity acitivity, EditText et) {
		if (et == null) return;
		et.requestFocus();
		InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et, InputMethodManager.RESULT_UNCHANGED_SHOWN);

	}

	/***
	 * 延迟300毫秒-显示键盘 说明：延迟会解决 有时弹不出键盘的问题
	 *
	 * @param acitivity
	 * @param et
	 */
	public static void showSoftInputDelay(final Activity acitivity, final EditText et) {
		et.postDelayed(new Runnable() {

			@Override public void run() {
				showSoftInput(acitivity, et);
			}
		}, 300);
	}

}