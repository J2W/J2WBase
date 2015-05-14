package j2w.team.common.widget.infiniteviewpager;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import j2w.team.common.log.L;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 15/1/22.
 */
public class InfiniteStatePagerAdapter extends PagerAdapter {

	FragmentManager						manager;

	List<String>						fragments	= new ArrayList<>();

	public InfiniteCirclePageIndicator	autoScrollViewPager;

	int									placeholder;						// 默认图片

	IInfiniteStatePagerAdapter			iInfiniteStatePagerAdapter;

	public interface IInfiniteStatePagerAdapter {

		void onImageItemClick(int position);
	}

	public void setiInfiniteStatePagerAdapter(IInfiniteStatePagerAdapter iInfiniteStatePagerAdapter) {
		this.iInfiniteStatePagerAdapter = iInfiniteStatePagerAdapter;
	}

	public void setPlaceholder(int placeholder) {
		this.placeholder = placeholder;
	}

	public InfiniteStatePagerAdapter(InfiniteCirclePageIndicator autoScrollViewPager, FragmentManager fm) {
		manager = fm;
		this.autoScrollViewPager = autoScrollViewPager;
	}

	public FragmentManager getManager() {
		return manager;
	}

	@Override public void destroyItem(ViewGroup container, int position, Object object) {
		L.i("destroyItem:" + position);
		container.removeView((View) object); // 移出viewpager两边之外的page布局
	}

	public void addData(String url) {
		fragments.add(url);
	}

	public void setData(List<String> fragments) {
		this.fragments = fragments;
	}

	public void addData(List<String> fragments) {
		for (String url : fragments) {
			addData(url);
		}
	}

	public void clearData() {
		fragments.clear();
	}

	@Override public int getCount() {
		return fragments.size();
	}

	@Override public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override public Object instantiateItem(ViewGroup container, final int position) {
		L.i("instantiateItem:" + position);
		final ImageView view = new ImageView(J2WHelper.getInstance());
		view.setScaleType(ImageView.ScaleType.CENTER);
		view.setId(position);
		J2WHelper.getPicassoHelper().load(fragments.get(position)).placeholder(placeholder).into(view, new Callback() {

			@Override public void onSuccess() {
				view.setScaleType(ImageView.ScaleType.FIT_XY);
			}

			@Override public void onError() {
				view.setScaleType(ImageView.ScaleType.CENTER);
			}
		});
		view.setOnClickListener(new View.OnClickListener() {

			@Override public void onClick(View v) {
				if(iInfiniteStatePagerAdapter != null){
					iInfiniteStatePagerAdapter.onImageItemClick(position);
				}
			}
		});
		container.addView(view);
		return view;
	}

	private int	currentPageIndex	= 0;

	public void onPageSelected(int position) {
		L.i("onPageSelected() currentPageIndex : " + currentPageIndex + " position : " + position);

		currentPageIndex = position;
	}
}
