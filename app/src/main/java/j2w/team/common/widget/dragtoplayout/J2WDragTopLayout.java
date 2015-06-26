package j2w.team.common.widget.dragtoplayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import j2w.team.R;

/**
 * @创建人 sky
 * @创建时间 15/6/24 下午5:40
 * @类描述 下拉布局
 */
public class J2WDragTopLayout extends FrameLayout {

	private ViewDragHelper	dragHelper;

	private int				dragRange;

	private View			dragContentView;

	private View			topView;

	private int				contentTop;

	private int				topViewHeight;

	private float			ratio;

	private boolean			isRefreshing;

	private boolean			shouldIntercept					= true;

	private PanelListener	panelListener;

	private float			refreshRatio					= 1.5f;

	private boolean			overDrag						= false;				// 是否添加弹簧效果

	private int				collapseOffset;

	private int				topViewId						= -1;

	private int				dragContentViewId				= -1;

	private boolean			captureTop						= true;

	private boolean			dispatchingChildrenDownFaked	= false;

	private boolean			dispatchingChildrenContentView	= false;

	private float			dispatchingChildrenStartedAtY	= Float.MAX_VALUE;

	private PanelState		panelState						= PanelState.EXPANDED;

	private int				hightSpace;											// 预留空间

	public enum PanelState {

		COLLAPSED(0), EXPANDED(1), SLIDING(2);

		private int	asInt;

		PanelState(int i) {
			this.asInt = i;
		}

		static PanelState fromInt(int i) {
			switch (i) {
				case 0:
					return COLLAPSED;
				case 2:
					return SLIDING;
				default:
				case 1:
					return EXPANDED;
			}
		}

		public int toInt() {
			return asInt;
		}
	}

	public J2WDragTopLayout(Context context) {
		this(context, null);
	}

	public J2WDragTopLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public J2WDragTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		dragHelper = ViewDragHelper.create(this, 1.0f, callback);

		// init from attrs
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.J2WDragTopLayout);
		setCollapseOffset(a.getDimensionPixelSize(R.styleable.J2WDragTopLayout_dtlCollapseOffset, collapseOffset));
		overDrag = a.getBoolean(R.styleable.J2WDragTopLayout_dtlOverDrag, overDrag);
		dragContentViewId = a.getResourceId(R.styleable.J2WDragTopLayout_dtlDragContentView, -1);
		topViewId = a.getResourceId(R.styleable.J2WDragTopLayout_dtlTopView, -1);
		initOpen(a.getBoolean(R.styleable.J2WDragTopLayout_dtlOpen, true));
		captureTop = a.getBoolean(R.styleable.J2WDragTopLayout_dtlCaptureTop, true);
		a.recycle();
	}

	private void initOpen(boolean initOpen) {
		if (initOpen) {
			panelState = PanelState.EXPANDED;
		} else {
			panelState = PanelState.COLLAPSED;
		}
	}

	@Override public void onFinishInflate() {
		super.onFinishInflate();

		if (getChildCount() < 2) {
			throw new RuntimeException("Content view must contains two child views at least.");
		}

		if (topViewId != -1 && dragContentViewId == -1) {
			throw new IllegalArgumentException("You have set \"dtlTopView\" but not \"dtlDragContentView\". Both are required!");
		}

		if (dragContentViewId != -1 && topViewId == -1) {
			throw new IllegalArgumentException("You have set \"dtlDragContentView\" but not \"dtlTopView\". Both are required!");
		}

		if (dragContentViewId != -1 && topViewId != -1) {
			bindId(this);
		} else {
			topView = getChildAt(0);
			dragContentView = getChildAt(1);
		}
	}

	private void bindId(View view) {
		topView = view.findViewById(topViewId);
		dragContentView = view.findViewById(dragContentViewId);

		if (topView == null) {
			throw new IllegalArgumentException("\"dtlTopView\" with id = \"@id/" + getResources().getResourceEntryName(topViewId) + "\" has NOT been found. Is a child with that id in this "
					+ getClass().getSimpleName() + "?");
		}

		if (dragContentView == null) {
			throw new IllegalArgumentException("\"dtlDragContentView\" with id = \"@id/" + getResources().getResourceEntryName(dragContentViewId)
					+ "\" has NOT been found. Is a child with that id in this " + getClass().getSimpleName() + "?");
		}
	}

	@Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		dragRange = getHeight();

		// In case of resetting the content top to target position before
		// sliding.
		int contentTopTemp = contentTop;
		resetTopViewHeight();
		resetContentHeight();

		topView.layout(left, Math.min(topView.getPaddingTop(), contentTop - topViewHeight), right, contentTop);
		dragContentView.layout(left, contentTopTemp, right, contentTopTemp + dragContentView.getHeight());
	}

	private void resetTopViewHeight() {
		int newTopHeight = topView.getHeight();
		// Top layout is changed
		if (topViewHeight != newTopHeight) {
			if (panelState == PanelState.EXPANDED) {
				contentTop = newTopHeight;
				handleSlide(newTopHeight);
			} else if (panelState == PanelState.COLLAPSED) {
				// update the drag content top when it is collapsed.
				contentTop = collapseOffset;
			}
			topViewHeight = newTopHeight;
		}
	}

	private void resetContentHeight() {
		if (dragContentView != null && dragContentView.getHeight() != 0) {
			ViewGroup.LayoutParams layoutParams = dragContentView.getLayoutParams();
			layoutParams.height = getHeight() - collapseOffset;
			dragContentView.setLayoutParams(layoutParams);
		}
	}

	private void handleSlide(final int top) {
		new Handler().post(new Runnable() {

			@Override public void run() {
				dragHelper.smoothSlideViewTo(dragContentView, getPaddingLeft(), top);
				postInvalidate();
			}
		});
	}

	private void resetDragContent(boolean anim, int top) {
		contentTop = top;
		if (anim) {
			dragHelper.smoothSlideViewTo(dragContentView, getPaddingLeft(), contentTop);
			postInvalidate();
		} else {
			requestLayout();
		}
	}

	private void calculateRatio(float top) {
		ratio = (top - collapseOffset) / (topViewHeight - collapseOffset);
		if (dispatchingChildrenContentView) {
			resetDispatchingContentView();
		}

		if (panelListener != null) {
			// Calculate the ratio while dragging.
			panelListener.onSliding(ratio);
			if (ratio > refreshRatio && !isRefreshing) {
				isRefreshing = true;
				panelListener.onRefresh();
			}
		}
	}

	private void updatePanelState() {
		if (contentTop <= getPaddingTop() + collapseOffset) {
			panelState = PanelState.COLLAPSED;
		} else if (contentTop >= topView.getHeight()) {
			panelState = PanelState.EXPANDED;
		} else {
			panelState = PanelState.SLIDING;
		}

		if (panelListener != null) {
			panelListener.onPanelStateChanged(panelState);
		}
	}

	@Override protected Parcelable onSaveInstanceState() {

		Parcelable superState = super.onSaveInstanceState();
		SavedState state = new SavedState(superState);
		state.panelState = panelState.toInt();

		return state;
	}

	@Override protected void onRestoreInstanceState(Parcelable state) {

		if (!(state instanceof SavedState)) {
			// FIX #10
			super.onRestoreInstanceState(BaseSavedState.EMPTY_STATE);
			return;
		}

		SavedState s = (SavedState) state;
		super.onRestoreInstanceState(s.getSuperState());

		this.panelState = PanelState.fromInt(s.panelState);
		if (panelState == PanelState.COLLAPSED) {
			closeTopView(false);
		} else {
			openTopView(false);
		}
	}

	private ViewDragHelper.Callback	callback	= new ViewDragHelper.Callback() {

													@Override public boolean tryCaptureView(View child, int pointerId) {
														if (child == topView && captureTop) {
															dragHelper.captureChildView(dragContentView, pointerId);
															return false;
														}
														return child == dragContentView;
													}

													@Override public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
														super.onViewPositionChanged(changedView, left, top, dx, dy);
														contentTop = top;
														requestLayout();
														calculateRatio(contentTop);
														updatePanelState();
													}

													@Override public int getViewVerticalDragRange(View child) {
														return dragRange;
													}

													@Override public int clampViewPositionVertical(View child, int top, int dy) {
														if (overDrag) {
															// Drag over the top
															// view height.
															return Math.max(top, getPaddingTop() + collapseOffset);
														} else {
															int collTop = getPaddingTop() + collapseOffset;
															return Math.min(topViewHeight, Math.max(top, collTop <= hightSpace ? hightSpace : collTop));
														}
													}

													@Override public void onViewReleased(View releasedChild, float xvel, float yvel) {
														super.onViewReleased(releasedChild, xvel, yvel);
														int top;
														if (yvel > 0 || contentTop > topViewHeight) {
															top = topViewHeight + getPaddingTop();
														} else {
															top = getPaddingTop() + collapseOffset;
														}
														dragHelper.settleCapturedViewAt(releasedChild.getLeft(), top == 0 ? hightSpace : top);
														postInvalidate();
													}

													@Override public void onViewDragStateChanged(int state) {
														super.onViewDragStateChanged(state);
													}
												};

	@Override public void computeScroll() {
		if (dragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {

			boolean intercept = shouldIntercept && dragHelper.shouldInterceptTouchEvent(ev);
			return intercept;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override public boolean onTouchEvent(MotionEvent event) {

		final int action = MotionEventCompat.getActionMasked(event);

		if (!dispatchingChildrenContentView) {
			try {
				dragHelper.processTouchEvent(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (action == MotionEvent.ACTION_MOVE && ratio == 0.0f) {
			dispatchingChildrenContentView = true;
			if (!dispatchingChildrenDownFaked) {
				dispatchingChildrenStartedAtY = event.getY();
				event.setAction(MotionEvent.ACTION_DOWN);
				dispatchingChildrenDownFaked = true;
			}
			dragContentView.dispatchTouchEvent(event);
		}

		if (dispatchingChildrenContentView && dispatchingChildrenStartedAtY < event.getY()) {
			resetDispatchingContentView();
		}

		if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
			resetDispatchingContentView();
			dragContentView.dispatchTouchEvent(event);
		}

		return true;
	}

	private void resetDispatchingContentView() {
		dispatchingChildrenDownFaked = false;
		dispatchingChildrenContentView = false;
		dispatchingChildrenStartedAtY = Float.MAX_VALUE;
	}

	// ================
	// public
	// ================

	public PanelState getState() {
		return panelState;
	}

	public void openTopView(boolean anim) {
		// Before created
		if (dragContentView.getHeight() == 0) {
			panelState = PanelState.EXPANDED;
			if (panelListener != null) {
				panelListener.onSliding(1.0f);
			}
		} else {
			resetDragContent(anim, topViewHeight);
		}
	}

	public void closeTopView(boolean anim) {
		if (dragContentView.getHeight() == 0) {
			panelState = PanelState.COLLAPSED;
			if (panelListener != null) {
				panelListener.onSliding(0.0f);
			}
		} else {
			resetDragContent(anim, getPaddingTop() + collapseOffset);
		}
	}

	public void updateTopViewHeight(int height) {
		ViewGroup.LayoutParams layoutParams = topView.getLayoutParams();
		layoutParams.height = height;
		topView.setLayoutParams(layoutParams);
	}

	public void toggleTopView() {
		toggleTopView(false);
	}

	public void toggleTopView(boolean touchMode) {
		switch (panelState) {
			case COLLAPSED:
				openTopView(true);
				if (touchMode) {
					setTouchMode(true);
				}
				break;
			case EXPANDED:
				closeTopView(true);
				if (touchMode) {
					setTouchMode(false);
				}
				break;
		}
	}

	public J2WDragTopLayout setTouchMode(boolean shouldIntercept) {
		this.shouldIntercept = shouldIntercept;
		return this;
	}

	/**
	 * Setup the drag listener.
	 *
	 * @return SetupWizard
	 */
	public J2WDragTopLayout listener(PanelListener panelListener) {
		this.panelListener = panelListener;
		return this;
	}

	/**
	 * Set the refresh position while dragging you want. The default value is
	 * 1.5f.
	 *
	 * @return SetupWizard
	 */
	public J2WDragTopLayout setRefreshRatio(float ratio) {
		this.refreshRatio = ratio;
		return this;
	}

	/**
	 * Set enable drag over. The default value is true.
	 *
	 * @return SetupWizard
	 */
	public J2WDragTopLayout setOverDrag(boolean overDrag) {
		this.overDrag = overDrag;
		return this;
	}

	/**
	 * 设置预留高度
	 * 
	 * @param hight
	 * @return
	 */
	public J2WDragTopLayout setHightSpace(int hight) {
		this.hightSpace = hight;
		return this;
	}

	/**
	 * Set the content view. Pass the id of the view (R.id.xxxxx). This one will
	 * be set as the content view and will be dragged together with the topView
	 *
	 * @param id
	 *            The id (R.id.xxxxx) of the content view.
	 * @return
	 */
	public J2WDragTopLayout setDragContentViewId(int id) {
		this.dragContentViewId = id;
		return this;
	}

	/**
	 * Set the top view. The top view is the header view that will be dragged
	 * out. Pass the id of the view (R.id.xxxxx)
	 *
	 * @param id
	 *            The id (R.id.xxxxx) of the top view
	 * @return
	 */
	public J2WDragTopLayout setTopViewId(int id) {
		this.topViewId = id;
		return this;
	}

	public boolean isOverDrag() {
		return overDrag;
	}

	/**
	 * Get refresh state
	 */
	public boolean isRefreshing() {
		return isRefreshing;
	}

	public void setRefreshing(boolean isRefreshing) {
		this.isRefreshing = isRefreshing;
	}

	/**
	 * Complete refresh and reset the refresh state.
	 */
	public void onRefreshComplete() {
		isRefreshing = false;
	}

	/**
	 * Set the collapse offset
	 *
	 * @return SetupWizard
	 */
	public J2WDragTopLayout setCollapseOffset(int px) {
		collapseOffset = px;
		resetContentHeight();
		return this;
	}

	public int getCollapseOffset() {
		return collapseOffset;
	}

	// ---------------------

	public interface PanelListener {

		/**
		 * Called while the panel state is changed.
		 */
		void onPanelStateChanged(PanelState panelState);

		/**
		 * Called while dragging. ratio >= 0.
		 */
		void onSliding(float ratio);

		/**
		 * Called while the ratio over refreshRatio.
		 */
		void onRefresh();
	}

	public static class SimplePanelListener implements PanelListener {

		@Override public void onPanelStateChanged(PanelState panelState) {

		}

		@Override public void onSliding(float ratio) {

		}

		@Deprecated @Override public void onRefresh() {

		}
	}

	/**
	 * 保存状态
	 */
	private static class SavedState extends BaseSavedState {

		int	panelState;

		SavedState(Parcelable superState) {
			super(superState);
		}

	}
}