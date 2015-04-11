package j2w.team.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @创建人 sky
 * @创建时间 15/4/11 下午9:03
 * @类描述 正方形Item layout
 */
public class J2WSquareLayout extends RelativeLayout {

	public J2WSquareLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public J2WSquareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public J2WSquareLayout(Context context) {
		super(context);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

		int childWidthSize = getMeasuredWidth();
		int childHeightSize = getMeasuredHeight();
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
