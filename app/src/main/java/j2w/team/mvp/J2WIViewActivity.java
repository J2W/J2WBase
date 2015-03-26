package j2w.team.mvp;

/**
 * Created by sky on 15/2/5.activity
 */
public interface J2WIViewActivity extends J2WIView {

	/**
	 * 是否固定竖屏
	 */
	public boolean isFixedVerticalScreen();

	/**
	 * 初始化fragment状态布局 - 进度
	 *
	 * @return
	 */
	public int fragmentLoadingLayout();

	/**
	 * 初始化fragment状态布局 - 空
	 *
	 * @return
	 */
	public int fragmentEmptyLayout();

	/**
	 * 初始化fragment状态布局 - 错误
	 *
	 * @return
	 */
	public int fragmentErrorLayout();
}
