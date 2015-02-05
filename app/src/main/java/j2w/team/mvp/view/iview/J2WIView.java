package j2w.team.mvp.view.iview;

import android.os.Bundle;

/**
 * Created by sky on 15/2/1.MVP模式 VIEW 接口
 */
 interface J2WIView {
    /** 获取TAG标记**/
    public String initTag();
    /** 获取布局ID */
    public int layoutId();
	/** 初始化视图 **/
	public void initView(Bundle savedInstanceState);
}
