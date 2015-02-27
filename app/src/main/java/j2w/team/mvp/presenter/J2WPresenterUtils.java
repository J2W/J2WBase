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
    public static final <T extends J2WIPresenter, D extends J2WPresenter, V extends J2WIView> T createPresenter(Class paramClazz, V iView) {
        String className = paramClazz.getSimpleName();
        L.tag(className);
        L.i("createPresenter()");
        T interfacePresenter;
        D implPresenter;
        // 获取当前类的泛型类-接口
        Class<Object> interfaceClass = AppUtils.getSuperClassGenricType(paramClazz, 0);
        //获取注解
        Presenter presenter = (Presenter) paramClazz.getAnnotation(Presenter.class);
        if (presenter == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(paramClazz);
            stringBuilder.append("，该类没有注入Presenter！");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        
        //检查
        DynamicProxyUtils.validateServiceClass(interfaceClass);

        Class clazz;
        try {
            /** 加载类 **/
            clazz = Class.forName(presenter.value().getName());
            /** 打印信息 **/
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("注入:");
            stringBuilder.append(clazz.getName());
            stringBuilder.append(" implements ");
            for(Class<?> iclazz : clazz.getInterfaces()){
                stringBuilder.append(iclazz.getName());
                stringBuilder.append(",");
            }
            int length = stringBuilder.length();
            stringBuilder.delete(length - 1,length);
            L.tag(className);
            L.i(stringBuilder.toString());
            /** 创建类 **/
            implPresenter = (D) clazz.newInstance();
            /** 初始化业务类 **/
            implPresenter.initPresenter(iView);
            /** 赋值给接口**/
            interfacePresenter = (T) implPresenter;
            /** 动态代理 - 线程系统 **/
            interfacePresenter = DynamicProxyUtils.newProxySyncSystem(interfacePresenter);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.valueOf(paramClazz) + "，没有找到业务类！");
        } catch (java.lang.InstantiationException e) {
            throw new IllegalArgumentException(String.valueOf(paramClazz) + "，实例化异常！");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.valueOf(paramClazz) + "，访问权限异常！");
        }
        //清除
        className = null;
        return interfacePresenter;
    }


}
