package j2w.team.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.text.format.Formatter;

import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


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

	/**
	 * 通过反射, 获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. 1.因为获取泛型类型-所以增加逻辑判定
	 */
	public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

		// 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		Type[] genType = clazz.getGenericInterfaces();
		Type[] params = null;
		Type baseType = clazz.getGenericSuperclass();
		// 父类
		if (baseType != null && (baseType instanceof ParameterizedType)) {
			params = ((ParameterizedType) baseType).getActualTypeArguments();
			if (index >= params.length || index < 0) {
				return Object.class;
			}
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}

			return (Class<Object>) params[index];
		}
		// 接口
		if (genType == null || genType.length < 1) {
			Type testType = clazz.getGenericSuperclass();
			if (!(testType instanceof ParameterizedType)) {
				return Object.class;
			}
			// 返回表示此类型实际类型参数的 Type 对象的数组。
			params = ((ParameterizedType) testType).getActualTypeArguments();
		} else {
			if (!(genType[index] instanceof ParameterizedType)) {
				return Object.class;
			}
			// 返回表示此类型实际类型参数的 Type 对象的数组。
			params = ((ParameterizedType) genType[index]).getActualTypeArguments();
		}

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}

		return (Class<Object>) params[index];
	}

	/**
	 * 判断SDCard状态是否可以读写
	 */
	public static boolean isSDCardState() {
		final String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED);
	}
}
