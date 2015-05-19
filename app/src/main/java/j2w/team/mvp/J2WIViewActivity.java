package j2w.team.mvp;

/**
 * Created by sky on 15/2/5.activity
 */
public interface J2WIViewActivity extends J2WIView {

	/**
	 * 初始化视图
	 */
	void initLayout();

	/**
	 * 初始化fragment状态布局 - 进度
	 *
	 * @return
	 */
	int fragmentLoadingLayout();

	/**
	 * 初始化fragment状态布局 - 空
	 *
	 * @return
	 */
	int fragmentEmptyLayout();

	/**
	 * 初始化fragment状态布局 - 错误
	 *
	 * @return
	 */
	int fragmentErrorLayout();

	/**
	 * 是否添加Activity状态布局
	 *
	 * @return true 打开 false 关闭
	 */
	boolean activityState();
}
