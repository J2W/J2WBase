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
	String initTag();

	/**
	 * 获取View层引用
	 * 
	 * @return view实例
	 */
	T getView();

	/**
	 * 判断View层需要不需要回调
	 * 
	 * @return true 需要 false 不需要
	 */
	boolean isCallBack();

	/**
	 * 消除View层引用
	 */
	void detach();

	/**
	 * 异常捕捉
	 * 
	 * @param methodName
	 *            方法名称
	 * @param throwable
	 *            异常
	 */
	void methodError(String methodName, Throwable throwable);

	/**
	 * 网络异常
	 * 
	 * @param methodName
	 *            方法名称
	 * @param j2WError
	 *            网络异常
	 */
	void methodHttpError(String methodName, J2WError j2WError);

	void errorNetWork(); // 发送请求前错误

	void errorHttp(); // 请求得到响应后错误

	void errorUnexpected();// 请求或者响应 意外错误

	void errorCancel();// 取消请求

	/** 编码异常 **/
	/**
	 * 编码异常
	 * 
	 * @param methodName
	 *            方法名称
	 * @param throwable
	 *            异常
	 */
	void methodCodingError(String methodName, Throwable throwable);

	/**
	 * 读数据
	 * 
	 * @param bundle
	 */
	void readData(Bundle bundle);

	/**
	 * 获取状态
	 */
	int getState();
}
