package j2w.team.mvp.view.iview;

/**
 * Created by sky on 15/2/5. actionbar 视图接口
 */
public interface J2WActionBarIView extends J2WIView {

	/**
	 * 初始化actionbar
	 */
	public void initActionBar();

	/**
	 * 设置布局
	 * 
	 * @return 布局ID
	 */
	public int actionBarLayoutID();

    /**
     * 是否固定竖屏
     */
    public boolean isFixedVerticalScreen();
}
