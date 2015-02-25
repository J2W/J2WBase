package j2w.team.common.utils.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import j2w.team.common.log.L;
import j2w.team.modules.threadpool.BackgroundType;
import j2w.team.modules.threadpool.Background;
import j2w.team.modules.threadpool.J2WAsyncCall;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 15/2/18.动态代理-线程系统
 */
public class J2WSyncHandler<T> extends BaseHandler<T> {
	Class aClass;

	public J2WSyncHandler(T t, Class clazz) {
		super(t);
		aClass = clazz;
	}

	@Override public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
		// 获得错误处理方法
		final Method methodError = aClass.getMethod("methodError", new Class[] { String.class, Throwable.class });

		Method oldMethod = aClass.getMethod(method.getName());
		// 获得注解数组
		Background background = oldMethod.getAnnotation(Background.class);

		if (background == null) {
			L.tag("J2W-Method");
			L.i("主线程执行: " + method.getName());
			return method.invoke(t, args);
		}
		BackgroundType backgroundType = background.value();
		switch (backgroundType) {
		case SINGLE:
			L.tag("J2W-Method");
			L.i("线程池-串行执行: " + method.getName());
			J2WHelper.getThreadPoolHelper().getSingleExecutorService().execute(new J2WAsyncCall(method.getName()) {
				@Override protected void execute() {
					try {
						method.invoke(t, args);
					} catch (Throwable e) {
						try {
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
		case MULTI:
			L.tag("J2W-Method");
			L.i("线程池-并行执行: " + method.getName());
			J2WHelper.getThreadPoolHelper().getExecutorService().execute(new J2WAsyncCall(method.getName()) {
				@Override protected void execute() {
					try {
						method.invoke(t, args);
					} catch (Throwable e) {
						try {
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
