package j2w.team.common.widget.infiniteviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import j2w.team.common.log.L;
import j2w.team.mvp.view.J2WBaseFragment;

/**
 * Created by sky on 15/1/22.
 */
public abstract class InfiniteStatePagerAdapter extends PagerAdapter{

	FragmentManager manager;

    List<Fragment> fragments;
    public InfiniteCirclePageIndicator autoScrollViewPager;
	public InfiniteStatePagerAdapter(InfiniteCirclePageIndicator autoScrollViewPager,FragmentManager fm) {
		manager = fm;
        this.autoScrollViewPager = autoScrollViewPager;
        fragments = getItemFragments();
	}

    public abstract List<Fragment> getItemFragments();

	public FragmentManager getManager() {
		return manager;
	}

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        L.i("destroyItem:" + position);
        container.removeView(fragments.get(position).getView()); // 移出viewpager两边之外的page布局
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        L.i("instantiateItem:" + position);
        container.removeView(fragments.get(position).getView());
        Fragment fragment = fragments.get(position);
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            manager.executePendingTransactions();
        }

        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView()); // 为viewpager增加布局
        }

        return fragment.getView();
    }

    private int				currentPageIndex	= 0;

    public void onPageSelected(int position) {
        L.i("onPageSelected() currentPageIndex : "+currentPageIndex+" position : "+position);
        fragments.get(currentPageIndex).onPause(); // 调用切换前Fargment的onPause()
        // 调用切换前Fargment的onStop()
        if (fragments.get(position).isAdded()) {
            fragments.get(position).onResume(); // 调用切换后Fargment的onResume()
            ((J2WBaseFragment) fragments.get(position)).isDelayedData(); // 调用延迟加载
        }
        currentPageIndex = position;
    }
}
