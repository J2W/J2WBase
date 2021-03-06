package j2w.team.common.utils.proxy;

import android.os.Looper;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WHelper;
import j2w.team.mvp.presenter.J2WPresenter;

/**
 * Created by sky on 15/2/7.动态代理 - 业务层
 */
public final class J2WPresenterHandler<T> extends BaseHandler<T> {

	J2WPresenter	j2WPresenter;

	Object			methodReturn;

	CountDownLatch	countDownLatch;

	public J2WPresenterHandler(T t, J2WPresenter j2WPresenter) {
		super(t);
		this.j2WPresenter = j2WPresenter;
	}

	@Override public synchronized Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();
		if (isMainLooper) {
			this.countDownLatch = new CountDownLatch(1);
			J2WHelper.getMainLooper().execute(new Runnable() {

				@Override public void run() {
					try {
						methodReturn = runMethod(method, args);
					} catch (Throwable throwable) {
						L.tag("J2W-Method");
						L.i("方法执行失败");
					} finally {
						countDownLatch.countDown();
					}
				}
			});
			countDownLatch.await();
			return methodReturn;
		} else {
			return runMethod(method, args);
		}
	}

	private Object runMethod(Method method, Object[] args) throws Throwable {
		StringBuffer stringBuffer = new StringBuffer();
		if (!j2WPresenter.isCallBack()) {
			stringBuffer.append("View层被销毁-");
			stringBuffer.append(method.getName());
			stringBuffer.append("回调取消,并返回NULL");
			L.i(stringBuffer.toString());
			// 取消关联
			t = null;
			return null;
		} else {
			stringBuffer.append("主线程执行:");
			stringBuffer.append(method.getName());
			L.i(stringBuffer.toString());
			return method.invoke(t, args);
		}
	}

}
