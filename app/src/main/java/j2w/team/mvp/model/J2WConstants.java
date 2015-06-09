package j2w.team.mvp.model;

/**
 * @创建人 sky
 * @创建时间 15/4/9 下午10:56
 * @类描述 常量数据
 */
public class J2WConstants {

	// 错误回调
	public static final int		J2W_ERROR_CODE				= 9999 << 1;

	// 进度条默认回调CODE - activity
	public static final int		J2W_DIALOG_CODE				= 8888 << 1;

	// 进度条默认回调CODE - fragment
	public static final int		J2W_DIALOG_FRAGMENT_CODE	= 7777 << 1;

	/**
	 * 状态 - 进度
	 */
	public static final int		J2W_STATE_LOADING			= 0;

	/**
	 * 状态 - 内容
	 */
	public static final int		J2W_STATE_CONTENT			= 1;

	/**
	 * 状态 - 空
	 */
	public static final int		J2W_STATE_EMPTY				= 2;

	/**
	 * 状态 - 错误
	 */
	public static final int		J2W_STATE_ERROR				= 3;

	/**
	 * 进度条默认TAG
	 */
	public static final String	J2W_DIALOG_PROGRESS			= "J2W_DIALOG_PROGRESS";

	/**
	 *  默认超时
	 */
	public static final int DEFAULT_TIME_OUT = 30;
}
