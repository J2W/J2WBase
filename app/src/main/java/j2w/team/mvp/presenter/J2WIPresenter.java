package j2w.team.mvp.presenter;

import j2w.team.modules.http.J2WError;

/**
 * Created by sky on 15/2/7. 业务
 */
public interface J2WIPresenter<T> {

	/** 获取TAG标记 **/
	public String initTag();

	/** 获取视图 **/
	public T getView();

	/** 是否回调 **/
	public boolean isCallBack();

	/** 消除 VIEW层 引用 **/
	public void detach();

	/** 方法异常 **/
	public void methodError(String methodName, Throwable throwable);

	/** 网络异常 **/
	public void methodHttpError(String methodName, J2WError j2WError);

	public void errorNetWork(); // 发送请求前错误

	public void errorHttp(); // 请求得到响应后错误

	public void errorUnexpected();// 请求或者响应 意外错误

	/** 编码异常 **/
	public void methodCodingError(String methodName, Throwable throwable);
}
