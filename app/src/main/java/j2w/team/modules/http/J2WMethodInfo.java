package j2w.team.modules.http;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Types;
import java.util.regex.Pattern;

/**
 * Created by sky on 15/2/13.
 */
final class J2WMethodInfo {

	/**
	 * 枚举类型
	 */
	enum ExecutionType {
		ASYNC, SYNC
	}

	private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
	private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);
	private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");

	// 方法属性
	final Method method;
	// 枚举类型
	final ExecutionType executionType;
    // 响应类型
    Type responseObjectType;

	J2WMethodInfo(Method method) {
		this.method = method;
		executionType = parseResponseType();

		// parseMethodAnnotations();
		// parseParameters();
	}

	/**
	 * 获取返回类型
	 * 
	 * @return
	 */
	private ExecutionType parseResponseType() {
		Type returnType = method.getGenericReturnType(); // 获取方法的返回类型

		Type lastArgType = null; // 参数类型

		Class<?> lastArgClass = null;

		Type[] parameterTypes = method.getGenericParameterTypes(); // 获得一个方法参数数组

		if (parameterTypes.length > 0) {
			Type typeToCheck = parameterTypes[parameterTypes.length - 1]; // 获取最后一个参数类型
			lastArgType = typeToCheck;
			if (typeToCheck instanceof ParameterizedType) { // 判断是否是普通参数
				typeToCheck = ((ParameterizedType) typeToCheck).getRawType();// 返回类型参数
			}
			if (typeToCheck instanceof Class) { // 判断是否是实现类-回调接口的实现类
				lastArgClass = (Class<?>) typeToCheck;//返回类
			}
		}

		boolean hasReturnType = returnType != void.class; //返回类型 是否为NULL
		boolean hasCallback = lastArgClass != null && Callback.class.isAssignableFrom(lastArgClass);//判断是否是回调接接口

		if (hasReturnType && hasCallback) {
			throw methodError("返回类型和回调接口不能同时存在！");
		}
		if (!hasReturnType && !hasCallback) {
			throw methodError("返回类型和回调接口必须要有一个！");
		}

		if (hasReturnType) {//如果有返回类型 - 同步类型
			responseObjectType = returnType;
			return ExecutionType.SYNC;
		}

//		lastArgType = J2WTypes.getSupertype(lastArgType, J2WTypes.getRawType(lastArgType), Callback.class);
//		if (lastArgType instanceof ParameterizedType) {
//			responseObjectType = getParameterUpperBound((ParameterizedType) lastArgType);
//			return ExecutionType.ASYNC;
//		}

		throw methodError("Last parameter must be of type Callback<X> or Callback<? super X>.");
	}

    /**
     * 自定义异常
     * @param message
     * @param args
     * @return
     */
    private RuntimeException methodError(String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        return new IllegalArgumentException(
                method.getDeclaringClass().getSimpleName() + "." + method.getName() + ": " + message);
    }
}
