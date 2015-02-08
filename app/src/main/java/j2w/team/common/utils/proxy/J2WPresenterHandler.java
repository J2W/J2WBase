package j2w.team.common.utils.proxy;

import java.lang.reflect.Method;

import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WPresenterBean;

/**
 * Created by sky on 15/2/7.动态代理 - 业务层
 */
public final class J2WPresenterHandler<T> extends BaseHandler<T> {

    J2WPresenterBean j2WPresenterBean;

	public J2WPresenterHandler(T t,J2WPresenterBean j2WPresenterBean) {
		super(t);
        this.j2WPresenterBean = j2WPresenterBean;
	}
	@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		L.tag("J2W-Method");
        StringBuffer stringBuffer = new StringBuffer();
        if (!j2WPresenterBean.isCallBack) {
            stringBuffer.append("View层被销毁-");
            stringBuffer.append(method.getName());
            stringBuffer.append("回调取消,并返回NULL");
            L.i(stringBuffer.toString());
            return null;
		} else {
			stringBuffer.append("执行:");
			stringBuffer.append(method.getName());
			L.i(stringBuffer.toString());
			return method.invoke(t, args);
		}
	}
}
