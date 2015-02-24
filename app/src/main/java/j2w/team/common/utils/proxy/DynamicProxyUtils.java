package j2w.team.common.utils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import j2w.team.mvp.presenter.J2WPresenter;

/**
 * Created by sky on 15/1/27. 通用动态代理类
 */
public final class DynamicProxyUtils {
	/**
	 * 生产代理类
	 * 
	 * @param loader
	 *            类加载器
	 * @param interfaces
	 *            接口数组
	 * @param invocationHandler
	 *            代理方法
	 * @param <T>
	 *            类型
	 * @return 返回代理类
	 */
	public static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler invocationHandler) {
		return (T) Proxy.newProxyInstance(loader, interfaces, invocationHandler);
	}

	/**
	 * 代理类 - 日志系统
	 * 
	 * @param d
	 * @param <D>
	 * @return
	 */
	public static <D> D newProxyLogSystem(D d) {
		// 获取Classloader
		ClassLoader loader = d.getClass().getClassLoader();
		// 获得接口数组
		Class<?>[] interfaces = d.getClass().getInterfaces();
		// 如果没有实现接口，获取父类接口
		if (interfaces.length == 0) {
			interfaces = d.getClass().getSuperclass().getInterfaces();
		}
		// 获得Handler - 这里可以替换成其他代理方法
		InvocationHandler invocationHandler = new J2WLogSystemHandler<D>(d);
		// 获取代理接口
		D b = newProxyInstance(loader, interfaces, invocationHandler);
		return b;
	}

	/**
	 * 代理类 - 线程系统
	 * 
	 * @param d
	 * @param <D>
	 * @return
	 */
	public static <D> D newProxySyncSystem(D d) {
		// 获取Classloader
		ClassLoader loader = d.getClass().getClassLoader();
		// 获得接口数组
		Class<?>[] interfaces = d.getClass().getInterfaces();
        // 如果没有实现接口，获取父类接口
        if (interfaces.length == 0) {
            interfaces = d.getClass().getSuperclass().getInterfaces();
        }
        // 获得Handler - 这里可以替换成其他代理方法
        InvocationHandler invocationHandler = new J2WSyncHandler<D>(d,d.getClass());
        // 获取代理接口
        D b = newProxyInstance(loader, interfaces, invocationHandler);
		return b;
	}

	/**
	 * 代理类 - 业务
	 *
	 * @param d
	 * @param <D>
	 * @return
	 */
	public static <D> D newProxyPresenter(D d, J2WPresenter j2WPresenter) {
		/** 根据日志 **/
		// 获取Classloader
		ClassLoader loader = d.getClass().getClassLoader();
		// 获得接口数组
		Class<?>[] interfaces = d.getClass().getInterfaces();
		// 如果没有实现接口，获取父类接口
		if (interfaces.length == 0) {
			interfaces = d.getClass().getSuperclass().getInterfaces();
		}
		// 获得Handler - 这里可以替换成其他代理方法
		InvocationHandler invocationHandler = new J2WPresenterHandler<D>(d, j2WPresenter);
		// 获取代理接口
		D b = newProxyInstance(loader, interfaces, invocationHandler);
		return b;
	}


    /**
     * 验证类 - 判断是否是一个接口
     * @param service
     * @param <T>
     */
    public static <T> void validateServiceClass(Class<T> service) {
        if (!service.isInterface()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(service);
            stringBuilder.append("，该类不是接口！");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    /**
     * 验证类 - 判断是否继承其他接口
     * @param service
     * @param <T>
     */
    public static <T> void validateInterfaceServiceClass(Class<T> service) {
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("接口不能继承其它接口");
        }

    }
}
