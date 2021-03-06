package j2w.team.mvp;

/**
 * Created by sky on 15/2/5. actionbar
 */
public interface J2WIViewABActivity extends J2WIViewActivity {

	/**
	 * 初始化actionbar
	 */
	void initActionBar();

	/**
	 * 设置布局
	 * 
	 * @return 布局ID
	 */
	int actionBarLayoutID();

	/**
	 * 设置标题栏
	 * 
	 * @param value
	 *            数据
	 * @param code
	 *            标记
	 */
	void setActionbarTitle(Object value, int code);
}
