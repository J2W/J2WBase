package j2w.team.modules.screen;

import android.support.v4.app.FragmentActivity;

import com.squareup.picasso.Picasso;

import java.util.Stack;

import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 15/1/26. fragmentactivity管理器
 */
// 禁止继承
public class J2WScreenManager implements J2WIScreenManager {
	// TAG标签
	public static final String TAG = "ScreenManager";

	// 禁止创建
	private J2WScreenManager() {
	}

	/**
	 * ScreenManager 单例模式
	 */
	private static final J2WScreenManager instance = new J2WScreenManager();

	public static J2WScreenManager getInstance() {
		return instance;
	}

	/**
	 * FragmentActivity堆栈 单例模式
	 */
	private static final Stack<FragmentActivity> fragmentActivities = new Stack<FragmentActivity>();

	/**
	 * 获取当前活动的activity
	 *
	 * @return
	 */
	@Override public FragmentActivity currentActivity() {
		if (fragmentActivities.size() == 0) {
			L.i("FragmentActivity堆栈 size = 0");
			return null;
		}
		FragmentActivity fragmentActivity = fragmentActivities.peek();
		L.i("获取当前FragmentActivity堆栈名称:" + fragmentActivity.getClass().getSimpleName());
		return fragmentActivity;
	}

	/**
	 * 入栈
	 *
	 * @param activity
	 */
	@Override public void pushActivity(FragmentActivity activity) {
		if (activity == null) {
			L.e("传入的参数为空!");
			return;
		}
		fragmentActivities.add(activity);
		L.m("入栈:" + activity.getClass().getSimpleName());
	}

	/**
	 * 出栈
	 *
	 * @param activity
	 */
	@Override public void popActivity(FragmentActivity activity) {
		if (activity == null) {
			L.e("传入的参数为空!");
			return;
		}
		activity.finish();
		fragmentActivities.remove(activity);
        if(fragmentActivities.size() < 1){
            /** 清空内存缓存picasso **/
            L.i("清空内存缓存-J2WHelper.getPicassoHelper().clearCache()");
            J2WHelper.getPicassoHelper().clearCache();
        }
		L.m("出栈:" + activity.getClass().getSimpleName());
		activity = null;
	}

	/**
	 * 退出堆栈中所有Activity, 当前的Activity除外
	 *
	 * @param clazz
	 *            当前活动窗口
	 */
	@Override public void popAllActivityExceptMain(Class clazz) {
		while (true) {
			FragmentActivity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(clazz)) {
				break;
			}

			popActivity(activity);
		}
	}
}
