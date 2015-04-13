package j2w.team.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @创建人 sky
 * @创建时间 15/4/11 下午10:01
 * @类描述 没有滑动的gridview
 */
public class J2WNoScrollGridView extends GridView {

	public J2WNoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public J2WNoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public J2WNoScrollGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
