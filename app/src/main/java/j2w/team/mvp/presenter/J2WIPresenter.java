package j2w.team.mvp.presenter;

import android.os.Bundle;

import j2w.team.modules.http.J2WError;

/**
 * Created by sky on 15/2/7. 业务
 */
public interface J2WIPresenter<T> {

	/**
	 * 获取TAG标记
	 *
	 * @return tag
	 */
	public String initTag();

	/**
	 * 获取View层引用
	 * 
	 * @return view实例
	 */
	public T getView();

	/**
	 * 判断View层需要不需要回调
	 * 
	 * @return true 需要 false 不需要
	 */
	public boolean isCallBack();

	/**
	 * 消除View层引用
	 */
	public void detach();

	/**
	 * 异常捕捉
	 * 
	 * @param methodName
	 *            方法名称
	 * @param throwable
	 *            异常
	 */
	public void methodError(String methodName, Throwable throwable);

	/**
	 * 网络异常
	 * 
	 * @param methodName
	 *            方法名称
	 * @param j2WError
	 *            网络异常
	 */
	public void methodHttpError(String methodName, J2WError j2WError);

	public void errorNetWork(); // 发送请求前错误

	public void errorHttp(); // 请求得到响应后错误

	public void errorUnexpected();// 请求或者响应 意外错误

	public void errorCancel();// 取消请求

	/** 编码异常 **/
	/**
	 * 编码异常
	 * 
	 * @param methodName
	 *            方法名称
	 * @param throwable
	 *            异常
	 */
	public void methodCodingError(String methodName, Throwable throwable);

	/**
	 * 读数据
	 * 
	 * @param bundle
	 */
	public void readData(Bundle bundle);
}
