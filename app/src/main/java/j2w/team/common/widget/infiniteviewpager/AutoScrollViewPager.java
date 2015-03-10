package j2w.team.common.widget.infiniteviewpager;

import java.lang.reflect.Field;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

/**
 * create by skyJC
 * 使用方式
 * 1.继承 InfiniteStatePagerAdapter
 * 2.写一个fragment
 * 3.setAdapter
 * 4.startAutoScroll()
 * 5.从布局 找到 InfiniteCirclePageIndicator
 * 6.setSnap(true)
 * 7.setViewPager
 */
public final class AutoScrollViewPager extends ViewPager {

	// 默认时间
	public static final int DEFAULT_INTERVAL = 3000;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	/** 什么也不做，当滑动最后或第一项 **/
	public static final int SLIDE_BORDER_MODE_NONE = 0;
	/** 循环滑动时在最后或第一项 **/
	public static final int SLIDE_BORDER_MODE_CYCLE = 1;
	/** 提供事件母滑动时在最后或第一项 **/
	public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;

	private long interval = DEFAULT_INTERVAL;

	/** 默认往右滑动 **/
	private int direction = RIGHT;

	/**
	 * 自动循环时是否自动滚动到最后或第一项， 默认 true
	 **/
	private boolean isCycle = true;
	/** 是否停止自动滚动接触时，默认 true **/
	private boolean stopScrollWhenTouch = true;
	/**
	 * 如何处理当滑动最后或第一项
	 **/
	private int slideBorderMode = SLIDE_BORDER_MODE_NONE;
	/** 动画时是否自动滚动最后或第一项 **/
	private boolean isBorderAnimation = true;
	/** 自动滚动动画滚动因子 1.0 **/
	private double autoScrollFactor = 1.0;
	/** 滑动滚动动画滚动因子,1.0 **/
	private double swipeScrollFactor = 1.0;

	private Handler handler;
	private boolean isAutoScroll = false;
	private boolean isStopByTouch = false;
	private float touchX = 0f, downX = 0f;
	private CustomDurationScroller scroller = null;

	public static final int SCROLL_WHAT = 0;

	private boolean infinitePagesEnabled = true;

    InfinitePagerAdapter infinitePagerAdapter;

	@Override public void setAdapter(PagerAdapter adapter) {
        infinitePagerAdapter = new InfinitePagerAdapter((InfiniteStatePagerAdapter) adapter);
        super.setAdapter(infinitePagerAdapter);

        ((InfiniteStatePagerAdapter) adapter).autoScrollViewPager.setOnPageChangeListener(infinitePagerAdapter);
		setCurrentItem(0);
	}

	/**
	 * 计算位置
	 * 
	 * @param item
	 */
	@Override public void setCurrentItem(int item) {
		if (infinitePagesEnabled) {
			item = getOffsetAmount() + (item % getAdapter().getCount());
		}
		super.setCurrentItem(item);

	}

	public void enableInfinitePages(boolean enable) {
		infinitePagesEnabled = enable;
		if (getAdapter() instanceof InfinitePagerAdapter) {
			InfinitePagerAdapter infAdapter = (InfinitePagerAdapter) getAdapter();
			infAdapter.enableInfinitePages(enable);
		}
	}

	private int getOffsetAmount() {
		if (getAdapter() instanceof InfinitePagerAdapter) {
			InfinitePagerAdapter infAdapter = (InfinitePagerAdapter) getAdapter();
			return infAdapter.getRealCount() * 100;
		} else {
			return 0;
		}
	}

	public AutoScrollViewPager(Context paramContext) {
		super(paramContext);
		init();
	}

	public AutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	private void init() {
		handler = new MyHandler();
		setViewPagerScroller();
	}

	/**
	 * 执行滚动
	 */
	public void startAutoScroll() {
		isAutoScroll = true;
		sendScrollMessage((long) (interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor));
	}

	/**
	 * 执行滚动
	 * 
	 * @param delayTimeInMills
	 *            间隔时间
	 */
	public void startAutoScroll(int delayTimeInMills) {
		isAutoScroll = true;
		sendScrollMessage(delayTimeInMills);
	}

	/**
	 * 停止滚动
	 */
	public void stopAutoScroll() {
		isAutoScroll = false;
		handler.removeMessages(SCROLL_WHAT);
	}

	/**
	 * 设定因子的滑动动画持续时间会改变
	 */
	public void setSwipeScrollDurationFactor(double scrollFactor) {
		swipeScrollFactor = scrollFactor;
	}

	/**
	 * 设定因子的滑动动画持续时间会改变 在自动滚动
	 */
	public void setAutoScrollDurationFactor(double scrollFactor) {
		autoScrollFactor = scrollFactor;
	}

	private void sendScrollMessage(long delayTimeInMills) {
		handler.removeMessages(SCROLL_WHAT);
		handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
	}

	private void setViewPagerScroller() {
		try {
			Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
			scrollerField.setAccessible(true);
			Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
			interpolatorField.setAccessible(true);

			scroller = new CustomDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
			scrollerField.set(this, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollOnce() {
		PagerAdapter adapter = getAdapter();
		int currentItem = getCurrentItem();
		int totalCount;
		if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
			return;
		}

		int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;
		if (nextItem < 0) {
			if (isCycle) {
				setCurrentItem(totalCount - 1, isBorderAnimation);
			}
		} else if (nextItem == totalCount) {
			if (isCycle) {
				setCurrentItem(0, isBorderAnimation);
			}
		} else {
			setCurrentItem(nextItem, true);
		}
	}

	/**
	 * <ul>
	 * if stopScrollWhenTouch is true
	 * <li>if event is down, stop auto scroll.</li>
	 * <li>if event is up, start auto scroll again.</li>
	 * </ul>
	 */
	@Override public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = MotionEventCompat.getActionMasked(ev);

		if (stopScrollWhenTouch) {
			if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
				isStopByTouch = true;
				stopAutoScroll();
			} else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
				startAutoScroll();
			}
		}

		if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT || slideBorderMode == SLIDE_BORDER_MODE_CYCLE) {
			touchX = ev.getX();
			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
				downX = touchX;
			}
			int currentItem = getCurrentItem();
			PagerAdapter adapter = getAdapter();
			int pageCount = adapter == null ? 0 : adapter.getCount();
			/**
			 * current index is first one and slide to right or current index is
			 * last one and slide to left.<br/>
			 * if slide border mode is to parent, then
			 * requestDisallowInterceptTouchEvent false.<br/>
			 * else scroll to last one when current item is first one, scroll to
			 * first one when current item is last one.
			 */
			if ((currentItem == 0 && downX <= touchX) || (currentItem == pageCount - 1 && downX >= touchX)) {
				if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT) {
					getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					if (pageCount > 1) {
						setCurrentItem(pageCount - currentItem - 1, isBorderAnimation);
					}
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				return super.dispatchTouchEvent(ev);
			}
		}
		getParent().requestDisallowInterceptTouchEvent(true);

		return super.dispatchTouchEvent(ev);
	}

	private class MyHandler extends Handler {

		@Override public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case SCROLL_WHAT:
				scroller.setScrollDurationFactor(autoScrollFactor);
				scrollOnce();
				scroller.setScrollDurationFactor(swipeScrollFactor);
				sendScrollMessage(interval + scroller.getDuration());
			default:
				break;
			}
		}
	}

	/**
	 * 间隔时间
	 * 
	 * @return the interval
	 */
	public long getInterval() {
		return interval;
	}

	/**
	 * set auto scroll time in milliseconds, default is
	 * {@link #DEFAULT_INTERVAL}
	 * 
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}

	/**
	 * get auto scroll direction
	 * 
	 * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
	 */
	public int getDirection() {
		return (direction == LEFT) ? LEFT : RIGHT;
	}

	/**
	 * set auto scroll direction
	 * 
	 * @param direction
	 *            {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * whether automatic cycle when auto scroll reaching the last or first item,
	 * default is true
	 * 
	 * @return the isCycle
	 */
	public boolean isCycle() {
		return isCycle;
	}

	/**
	 * set whether automatic cycle when auto scroll reaching the last or first
	 * item, default is true
	 *
	 * @param isCycle
	 *            the isCycle to set
	 */
	public void setCycle(boolean isCycle) {
		this.isCycle = isCycle;
	}

	/**
	 * whether stop auto scroll when touching, default is true
	 *
	 * @return the stopScrollWhenTouch
	 */
	public boolean isStopScrollWhenTouch() {
		return stopScrollWhenTouch;
	}

	/**
	 * set whether stop auto scroll when touching, default is true
	 *
	 * @param stopScrollWhenTouch
	 */
	public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
		this.stopScrollWhenTouch = stopScrollWhenTouch;
	}

	/**
	 * get how to process when sliding at the last or first item
	 *
	 * @return the slideBorderMode {@link #SLIDE_BORDER_MODE_NONE},
	 *         {@link #SLIDE_BORDER_MODE_TO_PARENT},
	 *         {@link #SLIDE_BORDER_MODE_CYCLE}, default is
	 *         {@link #SLIDE_BORDER_MODE_NONE}
	 */
	public int getSlideBorderMode() {
		return slideBorderMode;
	}

	/**
	 * set how to process when sliding at the last or first item
	 *
	 * @param slideBorderMode
	 *            {@link #SLIDE_BORDER_MODE_NONE},
	 *            {@link #SLIDE_BORDER_MODE_TO_PARENT},
	 *            {@link #SLIDE_BORDER_MODE_CYCLE}, default is
	 *            {@link #SLIDE_BORDER_MODE_NONE}
	 */
	public void setSlideBorderMode(int slideBorderMode) {
		this.slideBorderMode = slideBorderMode;
	}

	/**
	 * whether animating when auto scroll at the last or first item, default is
	 * true
	 *
	 * @return
	 */
	public boolean isBorderAnimation() {
		return isBorderAnimation;
	}

	/**
	 * set whether animating when auto scroll at the last or first item, default
	 * is true
	 *
	 * @param isBorderAnimation
	 */
	public void setBorderAnimation(boolean isBorderAnimation) {
		this.isBorderAnimation = isBorderAnimation;
	}
}
