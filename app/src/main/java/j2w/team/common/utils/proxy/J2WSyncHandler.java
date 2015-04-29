package j2w.team.common.utils.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

import j2w.team.common.log.L;
import j2w.team.modules.http.J2WMethodInfo;
import j2w.team.modules.threadpool.BackgroundType;
import j2w.team.modules.threadpool.Background;
import j2w.team.modules.threadpool.J2WAsyncCall;
import j2w.team.modules.threadpool.J2WStack;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 15/2/18.动态代理-线程系统
 */
public class J2WSyncHandler<T> extends BaseHandler<T> {

	Class			aClass;

	Stack<String>	stack;

	public J2WSyncHandler(T t, Class clazz) {
		super(t);
		aClass = clazz;
		stack = new Stack<>();
	}

	@Override public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
		Object returnObject = null;
		// 获得错误处理方法
		final Method methodError = aClass.getMethod("methodError", new Class[] { String.class, Throwable.class });

		Method oldMethod = aClass.getMethod(method.getName(), method.getParameterTypes());

		// 获得注解数组
		final J2WStack j2WStack = oldMethod.getAnnotation(J2WStack.class);
		Background background = oldMethod.getAnnotation(Background.class);
        final String key = J2WMethodInfo.getMethodString(method, method.getParameterTypes());
        if (background == null) {
			L.tag("J2W-Method");
			L.i("主线程执行: " + method.getName());
            try {
				if (j2WStack == null || !j2WStack.value()) { // 拦截
					// 搜索
					if (stack.search(key) != -1) {// 如果存在什么都不做
						L.tag("J2W-Method");
						L.i("正在执行该方法 : " + key);
						return returnObject;
					}
					L.tag("J2W-Method");
					L.i("Stack  入栈 : " + key);
					stack.push(key); // 入栈
					returnObject = method.invoke(t, args);// 执行
					L.tag("J2W-Method");
					L.i("Stack  出栈 : " + key);
					stack.remove(key);// 出栈
					return returnObject;
				} else {
					return method.invoke(t, args);
				}

			} catch (Throwable throwable) {
                stack.remove(key);// 出栈
                try {
					return methodError.invoke(t, new Object[] { method.getName(), throwable });
				} catch (IllegalAccessException e1) {
					L.e(e1.toString());
				} catch (InvocationTargetException e1) {
					L.e("方法内部异常:" + e1.toString());
				}
			}
        }
		BackgroundType backgroundType = background.value();
		// 搜索
		if (j2WStack == null || !j2WStack.value()) { // 拦截
			if (stack.search(key) != -1) { // 如果存在什么都不做
				L.tag("J2W-Method");
				L.i("正在执行该方法 : " + key);
				return returnObject;
			}
		}
		// 打印
		L.tag("J2W-Method");
		L.i("Stack  入栈 : " + key);
		switch (backgroundType) {
			case HTTP:
				L.tag("J2W-Method");
				L.i("网络线程池-并行执行: " + method.getName());

				J2WHelper.getThreadPoolHelper().getHttpExecutorService().execute(new J2WAsyncCall(method.getName()) {

					@Override protected void execute() {
						try {
							if (j2WStack == null || !j2WStack.value()) { // 拦截
								stack.push(key); // 入栈
								method.invoke(t, args);// 执行
								L.tag("J2W-Method");
								L.i("Stack  出栈 : " + key);
								stack.remove(key);// 出栈
							} else {
								method.invoke(t, args);// 执行
							}
						} catch (Throwable e) {
							try {
								L.tag("J2W-Method");
								L.i("Stack  出栈 : " + key);
								stack.remove(key);// 出栈
								methodError.invoke(t, new Object[] { method.getName(), e });
							} catch (IllegalAccessException e1) {
								L.e(e1.toString());
							} catch (InvocationTargetException e1) {
								L.e("方法内部异常:" + e1.toString());
							}
						}
					}
				});
				break;
			case SINGLEWORK:
				L.tag("J2W-Method");
				L.i("工作线程池-串行执行: " + method.getName());
				J2WHelper.getThreadPoolHelper().getSingleWorkExecutorService().execute(new J2WAsyncCall(method.getName()) {

					@Override protected void execute() {
						try {
                            if (j2WStack == null || !j2WStack.value()) { // 拦截
                                stack.push(key); // 入栈
                                method.invoke(t, args);// 执行
                                L.tag("J2W-Method");
                                L.i("Stack  出栈 : " + key);
                                stack.remove(key);// 出栈
                            } else {
                                method.invoke(t, args);// 执行
                            }
						} catch (Throwable e) {
							try {
								L.tag("J2W-Method");
								L.i("Stack  出栈 : " + key);
								stack.remove(key);// 出栈
								methodError.invoke(t, new Object[] { method.getName(), e });
							} catch (IllegalAccessException e1) {
								L.e(e1.toString());
							} catch (InvocationTargetException e1) {
								L.e("方法内部异常:" + e1.toString());
							}
						}
					}
				});
				break;
			case WORK:
				L.tag("J2W-Method");
				L.i("工作线程池-并行执行: " + method.getName());
				J2WHelper.getThreadPoolHelper().getWorkExecutorService().execute(new J2WAsyncCall(method.getName()) {

					@Override protected void execute() {
						try {
                            if (j2WStack == null || !j2WStack.value()) { // 拦截
                                stack.push(key); // 入栈
                                method.invoke(t, args);// 执行
                                L.tag("J2W-Method");
                                L.i("Stack  出栈 : " + key);
                                stack.remove(key);// 出栈
                            } else {
                                method.invoke(t, args);// 执行
                            }
						} catch (Throwable e) {
							try {
								L.tag("J2W-Method");
								L.i("Stack  出栈 : " + key);
								stack.remove(key);// 出栈
								methodError.invoke(t, new Object[] { method.getName(), e });
							} catch (IllegalAccessException e1) {
								L.e(e1.toString());
							} catch (InvocationTargetException e1) {
								L.e("方法内部异常:" + e1.toString());
							}
						}
					}
				});
				break;
		}
		return null;
	}
}
