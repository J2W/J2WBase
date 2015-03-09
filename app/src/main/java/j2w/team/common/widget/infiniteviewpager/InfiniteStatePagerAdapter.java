package j2w.team.common.widget.infiniteviewpager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sky on 15/1/22.
 */
public abstract class InfiniteStatePagerAdapter extends FragmentPagerAdapter {

	FragmentManager manager;

	public InfiniteStatePagerAdapter(FragmentManager fm) {
		super(fm);
		manager = fm;
	}

	public FragmentManager getManager() {
		return manager;
	}
}
