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
		Method methodError = aClass.getMethod("methodError", new Class[] { String.class, Throwable.class });

		Method oldMethod = aClass.getMethod(method.getName(), method.getParameterTypes());

		// 获得注解数组
		J2WStack j2WStack = oldMethod.getAnnotation(J2WStack.class);
		Background background = oldMethod.getAnnotation(Background.class);
		String key = J2WMethodInfo.getMethodString(method, method.getParameterTypes());
		// 搜索
		// 搜索
		if (j2WStack == null || !j2WStack.value()) { // 拦截
			if (stack.search(key) != -1) { // 如果存在什么都不做
				L.tag("J2W-Method");
				L.i("正在执行该方法 : " + key);
				return returnObject;
			}
		}
		// 同步执行
		if (background == null) {
			L.tag("J2W-Method");
			L.i("主线程执行: " + method.getName());
			try {
				if (j2WStack == null || !j2WStack.value()) { // 拦截
					L.tag("J2W-Method");
					L.i("Stack  入栈 : " + key);
					stack.push(key); // 入栈
					returnObject = method.invoke(t, args);// 执行
					return returnObject;
				} else {
					return method.invoke(t, args);
				}
			} catch (Throwable throwable) {
				try {
					return methodError.invoke(t, new Object[] { method.getName(), throwable });
				} catch (IllegalAccessException e1) {
					L.e(e1.toString());
				} catch (InvocationTargetException e1) {
					L.e("方法内部异常:" + e1.toString());
				}
			} finally {
				L.tag("J2W-Method");
				L.i("Stack  出栈 : " + key);
				stack.remove(key);// 出栈
			}
		}
		// 获取后台线程池类型
		BackgroundType backgroundType = background.value();
		// 生成执行任务
		SyncHandlerCall syncHandlerCall = new SyncHandlerCall(key, j2WStack, method, methodError, args);
		switch (backgroundType) {
			case HTTP:
				L.tag("J2W-Method");
				L.i("网络线程池-并行执行: " + key);
				J2WHelper.getThreadPoolHelper().getHttpExecutorService().execute(syncHandlerCall);
				break;
			case SINGLEWORK:

				L.tag("J2W-Method");
				L.i("工作线程池-串行执行: " + key);
				J2WHelper.getThreadPoolHelper().getSingleWorkExecutorService().execute(syncHandlerCall);
				break;
			case WORK:
				L.tag("J2W-Method");
				L.i("工作线程池-并行执行: " + key);
				J2WHelper.getThreadPoolHelper().getWorkExecutorService().execute(syncHandlerCall);
				break;
		}
		return null;
	}

	/**
	 * 执行代码
	 */
	class SyncHandlerCall extends J2WAsyncCall {

		public SyncHandlerCall(String methodName, J2WStack j2WStack, Method method, Method methodError, Object[] args) {
			super(methodName, j2WStack, method, methodError, args);
		}

		@Override protected void execute() {
			try {
				if (super.j2WStack == null || !super.j2WStack.value()) { // 拦截
					L.tag("J2W-Method");
					L.i("Stack  入栈 : " + super.mehtodName);
					stack.push(super.mehtodName); // 入栈
					super.method.invoke(t, args);// 执行
				} else {
					super.method.invoke(t, args);// 执行
				}
			} catch (Throwable e) {
				try {
					super.methodError.invoke(t, new Object[] { method.getName(), e });
				} catch (IllegalAccessException e1) {
					L.e(e1.toString());
				} catch (InvocationTargetException e1) {
					L.e("方法内部异常:" + e1.toString());
				}
			} finally {
				L.tag("J2W-Method");
				L.i("Stack  出栈 : " + super.mehtodName);
				stack.remove(super.mehtodName);// 出栈
			}
		}
	}
}
