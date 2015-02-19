package j2w.team.mvp.presenter;

import j2w.team.common.log.L;
import j2w.team.common.utils.AppUtils;
import j2w.team.common.utils.proxy.DynamicProxyUtils;
import j2w.team.mvp.view.iview.J2WIView;

/**
 * Created by sky on 15/2/18.业务工具类
 */
public final class J2WPresenterUtils {
	/**
	 * 创建业务类
	 * 
	 * @param paramClazz
	 * @param iView
	 * @return
	 */
	public static final <T extends J2WIPresenter,D extends J2WPresenter,V extends J2WIView> T createPresenter(Class paramClazz,V iView) {
		L.tag("J2WPresenterUtils");
		L.i("createPresenter()");
		T interfacePresenter = null;
		D implPresenter = null;
		// 获取当前类的泛型类-接口
		Class<Object> interfaceClass = AppUtils.getSuperClassGenricType(paramClazz, 0);
        // 获取当前类的泛型类-实现类
        Class<Object> implClass = AppUtils.getSuperClassGenricType(paramClazz, 1);
        //检查
        DynamicProxyUtils.validateServiceClass(interfaceClass);
		Class clazz;
		try {
            /** 加载类 **/
			clazz = Class.forName(implClass.getName());
            /** 创建类 **/
            implPresenter = (D) clazz.newInstance();
            /** 初始化业务类 **/
            implPresenter.initPresenter(iView);
            /** 赋值给接口**/
            interfacePresenter = (T) implPresenter;
            /** 动态代理 - 线程系统 **/
            interfacePresenter = DynamicProxyUtils.newProxySyncSystem(interfacePresenter);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (java.lang.InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return interfacePresenter;
	}


}
