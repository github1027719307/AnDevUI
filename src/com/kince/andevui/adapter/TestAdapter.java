/**
 * 
 */
package com.kince.andevui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author Administrator
 * 
 */
public class TestAdapter extends PagerAdapter {

	private List<ImageView> imageViews;
	private Activity mActivity;
	private Bitmap[] bmArray;
	private int[] imageId;

	/**
	 * 
	 */
	public TestAdapter(int[] imageId, List<ImageView> imageViews,
			Activity activity) {
		// TODO Auto-generated constructor stub
		this.imageId = imageId;
		this.imageViews = imageViews;
		this.mActivity = activity;
		bmArray = new Bitmap[imageId.length];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageId.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
	 * java.lang.Object)
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
		container.removeView(imageViews.get(position));
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(imageViews.get(position));
		return imageViews.get(position);
	}

}
