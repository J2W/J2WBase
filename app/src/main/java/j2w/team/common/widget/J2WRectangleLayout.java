package j2w.team.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @创建人 sky
 * @创建时间 15/4/11 下午10:01
 * @类描述 长方形 16 : 9
 */
public class J2WRectangleLayout extends RelativeLayout {

	public J2WRectangleLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public J2WRectangleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public J2WRectangleLayout(Context context) {
		super(context);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

		int childWidthSize = getMeasuredWidth();
		int childHeightSize = getMeasuredHeight();
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);

		int height = (childWidthSize / 16) * 9;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec - height);
	}
}
