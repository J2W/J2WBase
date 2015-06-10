package j2w.team.common.widget.infiniteviewpager.iconindicator;

public interface IconPagerAdapter {
    /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter
    int getCount();
    
    int getIconResIdDefault();
}
