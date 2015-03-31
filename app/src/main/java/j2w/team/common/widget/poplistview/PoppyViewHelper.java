package j2w.team.common.widget.poplistview;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.nineoldandroids.view.ViewPropertyAnimator;

import j2w.team.common.log.L;

/**
 * @创建人 sky
 * @创建时间 15/3/31 下午6:05
 * @类描述 给ListView曾加PopView
 */
public class PoppyViewHelper {

	public enum PoppyViewPosition {
		TOP, BOTTOM
	};

	private static final int	SCROLL_TO_TOP						= -1;

	private static final int	SCROLL_TO_BOTTOM					= 1;

	private static final int	SCROLL_DIRECTION_CHANGE_THRESHOLD	= 5;

	private Activity			mActivity;

	private LayoutInflater		mLayoutInflater;

	private View				mPoppyView;

	private int					mScrollDirection					= 0;

	private int					mPoppyViewHeight					= -1;

	private PoppyViewPosition	mPoppyViewPosition;

	public PoppyViewHelper(Activity activity, PoppyViewPosition position) {
		mActivity = activity;
		mLayoutInflater = LayoutInflater.from(activity);
		mPoppyViewPosition = position;
	}

	public PoppyViewHelper(Activity activity) {
		this(activity, PoppyViewPosition.BOTTOM);
	}

	// for ListView

	public View createPoppyViewOnListView(int listViewId, int poppyViewResId, OnScrollListener onScrollListener) {
		final AbsListView listView = (AbsListView) mActivity.findViewById(listViewId);
		mPoppyView = mLayoutInflater.inflate(poppyViewResId, null);
		initPoppyViewOnListView(listView, onScrollListener);
		return mPoppyView;
	}

    public View createPoppyViewOnListView(ListView listView, int poppyViewResId, OnScrollListener onScrollListener) {
        mPoppyView = mLayoutInflater.inflate(poppyViewResId, null);
        initPoppyViewOnListView(listView, onScrollListener);
        return mPoppyView;
    }

	public View createPoppyViewOnListView(int listViewId, int poppyViewResId) {
		return createPoppyViewOnListView(listViewId, poppyViewResId, null);
	}

	// common

	private void setPoppyViewOnView(View view) {
        L.i("setPoppyViewOnView");
        LayoutParams lp = view.getLayoutParams();
		ViewParent parent = view.getParent();
		ViewGroup group = (ViewGroup) parent;
		int index = group.indexOfChild(view);
		final FrameLayout newContainer = new FrameLayout(mActivity);
		group.removeView(view);
		group.addView(newContainer, index, lp);
		newContainer.addView(view);
		final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = mPoppyViewPosition == PoppyViewPosition.BOTTOM ? Gravity.BOTTOM : Gravity.TOP;
		newContainer.addView(mPoppyView, layoutParams);
		group.invalidate();
	}

	private void onScrollPositionChanged(int oldScrollPosition, int newScrollPosition) {
		int newScrollDirection;

		if (newScrollPosition < oldScrollPosition) {
			newScrollDirection = SCROLL_TO_TOP;
		} else {
			newScrollDirection = SCROLL_TO_BOTTOM;
		}

		if (newScrollDirection != mScrollDirection) {
			mScrollDirection = newScrollDirection;
			translateYPoppyView();
		}
	}

	private void translateYPoppyView() {
		mPoppyView.post(new Runnable() {

			@Override public void run() {
				if (mPoppyViewHeight <= 0) {
					mPoppyViewHeight = mPoppyView.getHeight();
				}

				int translationY = 0;
				switch (mPoppyViewPosition) {
					case BOTTOM:
						translationY = mScrollDirection == SCROLL_TO_TOP ? 0 : mPoppyViewHeight;
						break;
					case TOP:
						translationY = mScrollDirection == SCROLL_TO_TOP ? -mPoppyViewHeight : 0;
						break;
				}

				ViewPropertyAnimator.animate(mPoppyView).setDuration(300).translationY(translationY);
			}
		});
	}

	// for ListView

	private void initPoppyViewOnListView(AbsListView listView, final OnScrollListener onScrollListener) {
		setPoppyViewOnView(listView);
		listView.setOnScrollListener(new OnScrollListener() {

			int	mScrollPosition;

			@Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (onScrollListener != null) {
					onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				}
				View topChild = view.getChildAt(0);

				int newScrollPosition = 0;
				if (topChild == null) {
					newScrollPosition = 0;
				} else {
					newScrollPosition = -topChild.getTop() + view.getFirstVisiblePosition() * topChild.getHeight();
				}

				if (Math.abs(newScrollPosition - mScrollPosition) >= SCROLL_DIRECTION_CHANGE_THRESHOLD) {
					onScrollPositionChanged(mScrollPosition, newScrollPosition);
				}

				mScrollPosition = newScrollPosition;
			}

			@Override public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (onScrollListener != null) {
					onScrollListener.onScrollStateChanged(view, scrollState);
				}
			}
		});
	}
}
