package j2w.team.common.widget.dragLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * sky
 */
public class J2WRelativeLayout extends RelativeLayout {

	private J2WDragLayout	dl;

	public J2WRelativeLayout(Context context) {
		super(context);
	}

	public J2WRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public J2WRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setDragLayout(J2WDragLayout dl) {
		this.dl = dl;
	}

	@Override public boolean onInterceptTouchEvent(MotionEvent event) {
		if (dl.getStatus() != J2WDragLayout.Status.Close) {
			return true;
		}
		return super.onInterceptTouchEvent(event);
	}

	@Override public boolean onTouchEvent(MotionEvent event) {
		if (dl.getStatus() != J2WDragLayout.Status.Close) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				dl.close();
			}
			return true;
		}
		return super.onTouchEvent(event);
	}

}
