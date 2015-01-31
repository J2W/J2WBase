package j2w.team.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.text.format.Formatter;

import java.util.List;

/**
 * Created by sky on 15/1/29 程序工具包
 */
public final class AppUtils {

	/**
	 * 获取当前App内存使用量
	 * 
	 * @param context
	 *            上下文
	 * @return 使用量 (MB)
	 */

	public static final String getAppMemory(Context context) {
		try {
			// 获取当前PID
			int pid = android.os.Process.myPid();
			// 初始化返回值
			StringBuilder stringBuilderRmm = new StringBuilder();
			// 获取activity管理器
			ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			// 获取系统中所有正在运行的进程
			List<ActivityManager.RunningAppProcessInfo> appProcessInfos = mActivityManager.getRunningAppProcesses();
			// 循环检查-找到自己的进程
			for (ActivityManager.RunningAppProcessInfo appProcess : appProcessInfos) {
				// 检查是否是自己的进程
				if (appProcess.pid == pid) {
					// 从activity管理器中获取当前进程的内存数组
					Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(new int[] { appProcess.pid });
					// 换算
					long releaseMM = memoryInfo[0].getTotalPrivateDirty() * 1000;
					// 格式化显示
					stringBuilderRmm.append(Formatter.formatFileSize(context, releaseMM));
					break;
				}
			}
			return stringBuilderRmm.toString();
		} catch (Exception e) {
			return "无法检测";
		}
	}
}
