package j2w.team.modules.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
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
     * 获取方法上的注解值
     */
    private void parseMethodAnnotations() {
        for (Annotation methodAnnotation : method.getAnnotations()) {
//            Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
//            if (annotationType == DELETE.class) {
//                parseHttpMethodAndPath("DELETE", ((DELETE) methodAnnotation).value(), false);
//            } else if (annotationType == GET.class) {
//                parseHttpMethodAndPath("GET", ((GET) methodAnnotation).value(), false);
//            } else if (annotationType == HEAD.class) {
//                parseHttpMethodAndPath("HEAD", ((HEAD) methodAnnotation).value(), false);
//            } else if (annotationType == PATCH.class) {
//                parseHttpMethodAndPath("PATCH", ((PATCH) methodAnnotation).value(), true);
//            } else if (annotationType == POST.class) {
//                parseHttpMethodAndPath("POST", ((POST) methodAnnotation).value(), true);
//            } else if (annotationType == PUT.class) {
//                parseHttpMethodAndPath("PUT", ((PUT) methodAnnotation).value(), true);
//            } else if (annotationType == HTTP.class) {
//                HTTP http = (HTTP) methodAnnotation;
//                parseHttpMethodAndPath(http.method(), http.path(), http.hasBody());
//            } else if (annotationType == Headers.class) {
//                String[] headersToParse = ((Headers) methodAnnotation).value();
//                if (headersToParse.length == 0) {
//                    throw methodError("@Headers annotation is empty.");
//                }
//                headers = parseHeaders(headersToParse);
//            } else if (annotationType == Multipart.class) {
//                if (requestType != RequestType.SIMPLE) {
//                    throw methodError("Only one encoding annotation is allowed.");
//                }
//                throw new UnsupportedOperationException("Multipart shall return!");
//                //requestType = RequestType.MULTIPART;
//            } else if (annotationType == FormUrlEncoded.class) {
//                if (requestType != RequestType.SIMPLE) {
//                    throw methodError("Only one encoding annotation is allowed.");
//                }
//                throw new UnsupportedOperationException("Form URL encoding shall return!");
//                //requestType = RequestType.FORM_URL_ENCODED;
//            } else if (annotationType == Streaming.class) {
//                if (responseObjectType != Response.class) {
//                    throw methodError(
//                            "Only methods having %s as data type are allowed to have @%s annotation.",
//                            Response.class.getSimpleName(), Streaming.class.getSimpleName());
//                }
//                isStreaming = true;
//            }
        }
    }
	/**
	 * 获取返回类型
	 * 
	 * @return
	 */
	private ExecutionType parseResponseType() {
		Type returnType = method.getGenericReturnType(); // 获取方法的返回类型

		Type lastArgType = null; // 参数类型

		Class<?> lastArgClass = null; //结果类型

		Type[] parameterTypes = method.getGenericParameterTypes(); // 获得一个方法参数数组

		if (parameterTypes.length > 0) {
			Type typeToCheck = parameterTypes[parameterTypes.length - 1]; // 获取最后一个参数类型
			lastArgType = typeToCheck;
			if (typeToCheck instanceof ParameterizedType) { // 判断是否是普通参数
				typeToCheck = ((ParameterizedType) typeToCheck).getRawType();// 返回类型参数
			}
			if (typeToCheck instanceof Class) { // 判断是否是实现类-回调接口的实现类
				lastArgClass = (Class<?>) typeToCheck;// 返回类
			}
		}

		boolean hasReturnType = returnType != void.class; // 返回类型 是否为NULL
		boolean hasCallback = lastArgClass != null && Callback.class.isAssignableFrom(lastArgClass);// 判断是否是回调接接口

		if (hasReturnType && hasCallback) {
			throw methodError("返回类型和回调接口不能同时存在！");
		}
		if (!hasReturnType && !hasCallback) {
			throw methodError("返回类型和回调接口必须要有一个！");
		}

		if (hasReturnType) {// 如果有返回类型 - 同步类型
			responseObjectType = returnType;
			return ExecutionType.SYNC;
		}
		// 获取父类的类型
		lastArgType = Types.getSupertype(lastArgType, Types.getRawType(lastArgType), Callback.class);
		if (lastArgType instanceof ParameterizedType) { // 判断是否是参数类型
			responseObjectType = getParameterUpperBound((ParameterizedType) lastArgType);// 获取绑定的参数类型
			return ExecutionType.ASYNC;
		}

		throw methodError("最后一个参数必须是Callback<x> or Callback<? super X>");
	}

	/**
	 * 获取参数类型
	 * 
	 * @param type
	 * @return
	 */
	private static Type getParameterUpperBound(ParameterizedType type) {
		Type[] types = type.getActualTypeArguments();// 返回参数数组
		for (int i = 0; i < types.length; i++) {
			Type paramType = types[i];
			if (paramType instanceof WildcardType) { // 接口类型
				types[i] = ((WildcardType) paramType).getUpperBounds()[0];
			}
		}
		return types[0];
	}

	/**
	 * 自定义异常
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	private RuntimeException methodError(String message, Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}
		return new IllegalArgumentException(method.getDeclaringClass().getSimpleName() + "." + method.getName() + ": " + message);
	}
}
