package j2w.team.mvp.presenter;

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
}
