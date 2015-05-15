package j2w.team.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @创建人 sky
 * @创建时间 15/5/5 下午4:43
 * @类描述 ListView滑动 A-Z 字母
 */
public class J2WSideBar extends View {

	// 触摸事件
	private OnTouchingChangedListener	onTouchingChangedListener	= new OnTouchingChangedListener() {

																		@Override public void onTouchingChanged(String s) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub

																		}
																	};

	// 26个字母
	public static String[]				b							= { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z"												};

	private Paint						paint						= new Paint();

	private int							position					= -1;

	// 展示选中的字母
	private TextView					showChooseText;

	/**
	 * 为PinYinSideBar设置显示字母的TextView
	 *
	 * @param showChooseText
	 *            the showChooseText to set
	 */
	public void setShowChooseText(TextView showChooseText) {
		this.showChooseText = showChooseText;
	}

	public J2WSideBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public J2WSideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public J2WSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 获取焦点改变背景颜色
		int height = getHeight();
		int width = getWidth();
		int sigleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(0xFF333333);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			// 选中状态
			if (i == position) {
				paint.setColor(Color.WHITE);
				paint.setTextSize(30);
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = sigleHeight * i + sigleHeight - (i * 2);
			// 增加Y坐标高度
			yPos += 20;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressWarnings("deprecation") @Override public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float y = event.getY() - getY();
		int oldPosition = position;
		// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
		int c = 0;
		if (y >= 0 && y <= getHeight()) {
			c = (int) (y / getHeight() * b.length);
		} else if (y > getHeight()) {
			c = b.length - 1;
		}
		if (action == MotionEvent.ACTION_UP) {
			setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			position = -1;
			if (showChooseText != null) {
				showChooseText.setVisibility(View.INVISIBLE);
			}
		} else {
			setBackgroundDrawable(new ColorDrawable(Color.GRAY));
			setBackgroundColor(Color.GRAY);
			if (oldPosition != c) {
				if (c >= 0 && c < b.length) {
					if (onTouchingChangedListener != null) {
						onTouchingChangedListener.onTouchingChanged(b[c]);
					}
				}
				if (showChooseText != null) {
					showChooseText.setText(b[c]);
					showChooseText.setVisibility(View.VISIBLE);
				}
				position = c;
			}
		}
		invalidate();
		return true;
	}

	/**
	 * 向外公开的方法
	 *
	 * @Title: setOnTouchingChangedListener
	 * @Description: TODO
	 * @param onTouchingChangedListener
	 * @return void
	 */
	public void setOnTouchingChangedListener(OnTouchingChangedListener onTouchingChangedListener) {
		this.onTouchingChangedListener = onTouchingChangedListener;
	}

	/**
	 * 接口
	 *
	 * @ClassName: OnTouchingChangedListener
	 * @Description: TODO
	 * @author: raot 719055805@qq.com
	 * @date: 2014年9月4日 上午10:41:19
	 */
	public interface OnTouchingChangedListener {

		public void onTouchingChanged(String s);
	}
}
