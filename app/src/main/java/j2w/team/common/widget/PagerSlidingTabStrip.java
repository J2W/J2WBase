package j2w.team.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import j2w.team.R;

/**
 * Created by sky on 14-11-20.ViewPager Tab
 */
public class PagerSlidingTabStrip extends HorizontalScrollView {

	public interface IconTabProvider {

		int getPageIconResId(int position);
	}

	public interface TitleCountTabProvider {

		String getPageCount(int position);

	}

	public interface CustomTabProvider {

		int getCustomTabView();

		void initTabsItem(View view, int position);
	}

	// @formatter:off
	private static final int[]			ATTRS					= new int[] { android.R.attr.textSize, android.R.attr.textColor };

	// @formatter:on

	private LinearLayout.LayoutParams	defaultTabLayoutParams;

	private LinearLayout.LayoutParams	expandedTabLayoutParams;

	private final PageListener			pageListener			= new PageListener();

	public OnPageChangeListener			delegatePageListener;

	public LinearLayout					tabsContainer;

	private ViewPager					pager;

	private int							tabCount;

	private int							currentPosition			= 0;

	private int							selectedPosition		= 0;

	private float						currentPositionOffset	= 0f;

	private Paint						rectPaint;

	private Paint						dividerPaint;

	private int							indicatorColor			= 0xFF666666;

	private int							underlineColor			= 0x1A000000;

	private int							dividerColor			= 0x1A000000;

	private boolean						shouldExpand			= false;

	private boolean						textAllCaps				= true;

	private int							scrollOffset			= 52;

	private int							indicatorHeight			= 8;

	private int							underlineHeight			= 2;

	private int							dividerPadding			= 12;

	private int							tabPadding				= 20;

	private int							tabMargins				= 0;

	private int							dividerWidth			= 1;

	private int							tabTextSize				= 12;

	private int							tabTextColor			= 0xFF666666;

	private int							selectedTabTextColor	= 0xFF666666;

	private Typeface					tabTypeface				= null;

	private int							tabTypefaceStyle		= Typeface.NORMAL;

	private int							lastScrollX				= 0;

	private int							tabBackgroundResId		= R.drawable.j2w_viewpager_background_tabs;

	private Locale						locale;

	private int							tabWidth;

	private int							rectPaintWidth;

	private boolean						isCurrentItemAnimation	= false;

	public PagerSlidingTabStrip(Context context) {
		this(context, null);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setFillViewport(true);
		setWillNotDraw(false);

		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);

		DisplayMetrics dm = getResources().getDisplayMetrics();

		scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
		indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
		underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
		dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
		tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
		dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
		tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

		// get system attrs (android:textSize and android:textColor)

		TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

		tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
		tabTextColor = a.getColor(1, tabTextColor);

		a.recycle();

		// get custom attrs

		a = context.obtainStyledAttributes(attrs, R.styleable.J2WPagerSlidingTabStripStyle);

		indicatorColor = a.getColor(R.styleable.J2WPagerSlidingTabStripStyle_pstsIndicatorColor, indicatorColor);
		underlineColor = a.getColor(R.styleable.J2WPagerSlidingTabStripStyle_pstsUnderlineColor, underlineColor);
		dividerColor = a.getColor(R.styleable.J2WPagerSlidingTabStripStyle_pstsDividerColor, dividerColor);
		indicatorHeight = a.getDimensionPixelSize(R.styleable.J2WPagerSlidingTabStripStyle_pstsIndicatorHeight, indicatorHeight);
		underlineHeight = a.getDimensionPixelSize(R.styleable.J2WPagerSlidingTabStripStyle_pstsUnderlineHeight, underlineHeight);
		dividerPadding = a.getDimensionPixelSize(R.styleable.J2WPagerSlidingTabStripStyle_pstsDividerPadding, dividerPadding);
		tabPadding = a.getDimensionPixelSize(R.styleable.J2WPagerSlidingTabStripStyle_pstsTabPaddingLeftRight, tabPadding);
		tabBackgroundResId = a.getResourceId(R.styleable.J2WPagerSlidingTabStripStyle_pstsTabBackground, tabBackgroundResId);
		shouldExpand = a.getBoolean(R.styleable.J2WPagerSlidingTabStripStyle_pstsShouldExpand, shouldExpand);
		scrollOffset = a.getDimensionPixelSize(R.styleable.J2WPagerSlidingTabStripStyle_pstsScrollOffset, scrollOffset);
		textAllCaps = a.getBoolean(R.styleable.J2WPagerSlidingTabStripStyle_pstsTextAllCaps, textAllCaps);
		selectedTabTextColor = a.getColor(R.styleable.J2WPagerSlidingTabStripStyle_pstsSelectedTextColor, Color.parseColor("#51A3FF"));
		a.recycle();

		rectPaint = new Paint();
		rectPaint.setAntiAlias(true);
		rectPaint.setStyle(Style.FILL);

		dividerPaint = new Paint();
		dividerPaint.setAntiAlias(true);
		dividerPaint.setStrokeWidth(dividerWidth);

		defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
		if (locale == null) {
			locale = getResources().getConfiguration().locale;
		}
	}

	/**
	 * 设置宽度
	 * 
	 * @param width
	 */
	public void setTabWidth(int width) {
		tabWidth = width;
		if (tabWidth != 0) {
			getLayoutParams().width = LayoutParams.WRAP_CONTENT;
		} else {
			getLayoutParams().width = LayoutParams.MATCH_PARENT;
		}
	}

	public void setRectPaintWidth(int width) {
		rectPaintWidth = width;
	}

	public void setViewPager(ViewPager pager) {
		this.pager = pager;

		if (pager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}

		pager.setOnPageChangeListener(pageListener);

		notifyDataSetChanged();
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	public void notifyDataSetChanged() {

		tabsContainer.removeAllViews();

		tabCount = pager.getAdapter().getCount();

		for (int i = 0; i < tabCount; i++) {
			if (pager.getAdapter() instanceof TitleCountTabProvider) {
				addTextIconTab(i, pager.getAdapter().getPageTitle(i).toString(), ((TitleCountTabProvider) pager.getAdapter()).getPageCount(i));
			} else if (pager.getAdapter() instanceof IconTabProvider) {
				addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
			} else if (pager.getAdapter() instanceof CustomTabProvider) {
				addCustomTab(i);
			} else {
				addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
			}

		}

		updateTabStyles();

		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override public void onGlobalLayout() {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				currentPosition = pager.getCurrentItem();
				scrollToChild(currentPosition, 0);
				if (delegatePageListener != null) {
					delegatePageListener.onPageSelected(currentPosition);
				}
			}
		});

	}

	private void addCustomTab(final int position) {
		View tab = LayoutInflater.from(getContext()).inflate(((CustomTabProvider) pager.getAdapter()).getCustomTabView(), null);
		((CustomTabProvider) pager.getAdapter()).initTabsItem(tab, position);
		tab.setFocusable(true);
		tab.setOnClickListener(new OnClickListener() {

			@Override public void onClick(View v) {
				pager.setCurrentItem(position, isCurrentItemAnimation);
			}
		});
		tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
	}

	private void addTextIconTab(final int position, String title, String count) {

		TextView tab = new TextView(getContext());
		if (count != null) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(title);
			stringBuilder.append("(");
			stringBuilder.append(count);
			stringBuilder.append(")");
			tab.setText(stringBuilder.toString());
		} else {
			tab.setText(title);
		}

		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();
		addTab(position, tab);
	}

	private void addTextTab(final int position, String title) {
		TextView tab = new TextView(getContext());
		tab.setText(title);
		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();
		addTab(position, tab);
	}

	private void addIconTab(final int position, int resId) {

		ImageButton tab = new ImageButton(getContext());
		tab.setImageResource(resId);

		addTab(position, tab);

	}

	private void addTab(final int position, View tab) {
		tab.setFocusable(true);
		tab.setOnClickListener(new OnClickListener() {

			@Override public void onClick(View v) {
				pager.setCurrentItem(position, isCurrentItemAnimation);
			}
		});

		tab.setPadding(tabPadding, 0, tabPadding, 0);

		tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
	}

	private void updateTabStyles() {

		for (int i = 0; i < tabCount; i++) {

			View v = tabsContainer.getChildAt(i);

			v.setBackgroundResource(tabBackgroundResId);

			if (v instanceof TextView) {

				TextView tab = (TextView) v;
				tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
				tab.setTypeface(tabTypeface, tabTypefaceStyle);
				tab.setTextColor(tabTextColor);

				// setAllCaps() is only available from API 14, so the upper case
				// is made manually if we are on a
				// pre-ICS-build
				if (textAllCaps) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
						tab.setAllCaps(true);
					} else {
						tab.setText(tab.getText().toString().toUpperCase(locale));
					}
				}
				if (i == selectedPosition) {
					tab.setTextColor(selectedTabTextColor);
				}
			}
		}

	}

	private void scrollToChild(int position, int offset) {

		if (tabCount == 0) {
			return;
		}

		int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

		if (position > 0 || offset > 0) {
			newScrollX -= scrollOffset;
		}

		if (newScrollX != lastScrollX) {
			lastScrollX = newScrollX;
			scrollTo(newScrollX, 0);
		}

	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isInEditMode() || tabCount == 0) {
			return;
		}

		final int height = getHeight();

		// draw indicator line

		rectPaint.setColor(indicatorColor);

		// default: line below current tab
		View currentTab = tabsContainer.getChildAt(currentPosition);
		float lineLeft = currentTab.getLeft() + rectPaintWidth;
		float lineRight = tabWidth == 0 ? currentTab.getRight() - rectPaintWidth : tabWidth;

		// if there is an offset, start interpolating left and right coordinates
		// between current and next tab
		if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

			View nextTab = tabsContainer.getChildAt(currentPosition + 1);
			final float nextTabLeft = nextTab.getLeft() + rectPaintWidth;
			final float nextTabRight = tabWidth == 0 ? nextTab.getRight() - rectPaintWidth : tabWidth;

			lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
			lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
		}

		canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);

		// draw underline

		rectPaint.setColor(underlineColor);
		canvas.drawRect(0, height - underlineHeight, tabWidth == 0 ? tabsContainer.getWidth() : tabWidth, height, rectPaint);

		// draw divider

		dividerPaint.setColor(dividerColor);
		for (int i = 0; i < tabCount - 1; i++) {
			View tab = tabsContainer.getChildAt(i);
			canvas.drawLine(tabWidth == 0 ? tab.getRight() : tabWidth, dividerPadding, tabWidth == 0 ? tab.getRight() : tabWidth, height - dividerPadding, dividerPaint);
		}
	}

	private class PageListener implements OnPageChangeListener {

		@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			currentPosition = position;
			currentPositionOffset = positionOffset;

			scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

			invalidate();

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				scrollToChild(pager.getCurrentItem(), 0);
			}

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrollStateChanged(state);
			}
		}

		@Override public void onPageSelected(int position) {
			selectedPosition = position;
			updateTabStyles();
			if (delegatePageListener != null) {
				delegatePageListener.onPageSelected(position);
			}
		}

	}

	public void setIndicatorColor(int indicatorColor) {
		this.indicatorColor = indicatorColor;
		invalidate();
	}

	public void setIndicatorColorResource(int resId) {
		this.indicatorColor = getResources().getColor(resId);
		invalidate();
	}

	public int getIndicatorColor() {
		return this.indicatorColor;
	}

	public void setIndicatorHeight(int indicatorLineHeightPx) {
		this.indicatorHeight = indicatorLineHeightPx;
		invalidate();
	}

	public int getIndicatorHeight() {
		return indicatorHeight;
	}

	public void setUnderlineColor(int underlineColor) {
		this.underlineColor = underlineColor;
		invalidate();
	}

	public void setUnderlineColorResource(int resId) {
		this.underlineColor = getResources().getColor(resId);
		invalidate();
	}

	public int getUnderlineColor() {
		return underlineColor;
	}

	public void setDividerColor(int dividerColor) {
		this.dividerColor = dividerColor;
		invalidate();
	}

	public void setDividerColorResource(int resId) {
		this.dividerColor = getResources().getColor(resId);
		invalidate();
	}

	public int getDividerColor() {
		return dividerColor;
	}

	public void setUnderlineHeight(int underlineHeightPx) {
		this.underlineHeight = underlineHeightPx;
		invalidate();
	}

	public int getUnderlineHeight() {
		return underlineHeight;
	}

	public void setDividerPadding(int dividerPaddingPx) {
		this.dividerPadding = dividerPaddingPx;
		invalidate();
	}

	public int getDividerPadding() {
		return dividerPadding;
	}

	public void setScrollOffset(int scrollOffsetPx) {
		this.scrollOffset = scrollOffsetPx;
		invalidate();
	}

	public int getScrollOffset() {
		return scrollOffset;
	}

	public void setShouldExpand(boolean shouldExpand) {
		this.shouldExpand = shouldExpand;
	}

	public boolean getShouldExpand() {
		return shouldExpand;
	}

	public boolean isTextAllCaps() {
		return textAllCaps;
	}

	public void setAllCaps(boolean textAllCaps) {
		this.textAllCaps = textAllCaps;
	}

	public void setTextSize(int textSizePx) {
		this.tabTextSize = textSizePx;
		updateTabStyles();
	}

	public int getTextSize() {
		return tabTextSize;
	}

	public void setTextColor(int textColor) {
		this.tabTextColor = textColor;
		updateTabStyles();
	}

	public void setTextColorResource(int resId) {
		this.tabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}

	public int getTextColor() {
		return tabTextColor;
	}

	public void setSelectedTextColor(int textColor) {
		this.selectedTabTextColor = textColor;
		updateTabStyles();
	}

	public void setSelectedTextColorResource(int resId) {
		this.selectedTabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}

	public int getSelectedTextColor() {
		return selectedTabTextColor;
	}

	public void setTypeface(Typeface typeface, int style) {
		this.tabTypeface = typeface;
		this.tabTypefaceStyle = style;
		updateTabStyles();
	}

	public void setTabBackground(int resId) {
		this.tabBackgroundResId = resId;
		updateTabStyles();
	}

	public int getTabBackground() {
		return tabBackgroundResId;
	}

	public void setTabPaddingLeftRight(int paddingPx) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		this.tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingPx, dm);
		updateTabStyles();
	}

	public void setTabMarginsLeftRight(int marginsPx) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		tabMargins = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginsPx, dm);

		defaultTabLayoutParams.setMargins(tabMargins, 0, tabMargins, 0);
		expandedTabLayoutParams.setMargins(tabMargins, 0, tabMargins, 0);
		updateTabStyles();
	}

	public int getTabPaddingLeftRight() {
		return tabPadding;
	}

	@Override public void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		currentPosition = savedState.currentPosition;
		requestLayout();
	}

	@Override public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.currentPosition = currentPosition;
		return savedState;
	}

	/**
	 * 切换是否有动画
	 * 
	 * @param isCurrentItemAnimation
	 */
	public void setIsCurrentItemAnimation(boolean isCurrentItemAnimation) {
		this.isCurrentItemAnimation = isCurrentItemAnimation;
	}

	static class SavedState extends BaseSavedState {

		int	currentPosition;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			currentPosition = in.readInt();
		}

		@Override public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(currentPosition);
		}

		public static final Creator<SavedState>	CREATOR	= new Creator<SavedState>() {

															@Override public SavedState createFromParcel(Parcel in) {
																return new SavedState(in);
															}

															@Override public SavedState[] newArray(int size) {
																return new SavedState[size];
															}
														};
	}

}