package j2w.team.mvp.presenter;

import j2w.team.modules.http.J2WError;

/**
 * Created by sky on 15/3/21.业务层公共错误处理接口
 */
public interface J2WICommonPresenter {

	/** 请求发送前异常 **/
	public void errorNetWork();

	/** 请求响应后异常 **/
	public void errorHttp(J2WError j2WError);

	/** 意外异常 **/
	public void errorUnexpected(J2WError j2WError);

	/** 编码异常 **/
	public void errorCodingError(Throwable throwable);
}
