package j2w.team.mvp.presenter;

/**
 * Created by sky on 15/2/7. 业务
 */
public interface J2WIPresenter<T> {

    /** 获取TAG标记**/
    public String initTag();
    
    public T getView();
 
	/** 消除 VIEW层 引用 **/
	public void detach();
}
