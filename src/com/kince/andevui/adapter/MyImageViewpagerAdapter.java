package com.kince.andevui.adapter;

import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.fragment.ImageViewFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyImageViewpagerAdapter extends FragmentStatePagerAdapter {
	private Context context;
	// private String Title[] = { "      DEBUT     ", "   POPULAR  ",
	// "   EVERYONE   " };
	private String Title[] = { "All", "for", "You" };
	private CodeClass[] urls;
	private ImageLoader imageLoader;
	DisplayImageOptions options;

	public MyImageViewpagerAdapter(FragmentManager fm, Context context,
			CodeClass[] urls, ImageLoader imageLoader) {
		super(fm);
		this.context = context;
		this.urls = urls;
		this.imageLoader = imageLoader;

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.finishUpdate(container);
	}

	@Override
	public Object instantiateItem(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1 == 1) {

		}
		return super.instantiateItem(arg0, arg1);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return super.isViewFromObject(view, object);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.setPrimaryItem(container, position, object);
	}

	@Override
	public void startUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		//
		// if (arg0 == 0) {
		// return new MainFragment(context, Constants.JIU_IMAGES_CODECLASS, 1);
		// } else if (arg0 == 1) {
		// return new ListViewFragment(context, Constants.BIG_IMAGES);
		// } else {
		// return new MainFragment(context, Constants.JXX_IMAGES_CODECLASS, 2);
		// }
		return new ImageViewFragment(urls[arg0].getKey(), imageLoader, options);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urls.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return Title[position];
	}

}