package j2w.team.common.utils.proxy;

import java.lang.reflect.Method;

import j2w.team.common.log.L;


/**
 * Created by sky on 15/2/18.动态代理-线程系统
 */
public class J2WSyncHandler <T> extends BaseHandler<T>{


    public J2WSyncHandler(T t) {
        super(t);
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        /** 执行方法 **/
        L.tag("J2W-Method");
        L.i(method.getName());
        Object object = method.invoke(t, args);
   
        return object;
    }
}
