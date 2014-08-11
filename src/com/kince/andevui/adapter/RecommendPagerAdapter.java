package com.kince.andevui.adapter;

import java.util.List;

import com.kince.andevui.R;
import com.kince.andevui.activity.DetailsActivity;

import android.support.v4.view.ViewPager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @author kince
 * @category 各个推荐部分的适配器 
 * @since 2014.5.4 2014.5.23 2014.7.1
 * @version v1.0.0
 */

public class RecommendPagerAdapter extends PagerAdapter {
	// private int[] imageRes;
	private List<ImageView> imageViews;
	//
	private Activity mActivity;
	// 保留
	private int flag;
	//
	private Bitmap[] bmArray;
	//
	private int[] imageId;

	public RecommendPagerAdapter(int[] imageId, List imageViews,
			Activity activity) {
		this.imageId = imageId;
		this.imageViews = imageViews;
		this.mActivity = activity;

		bmArray = new Bitmap[imageId.length];
	};

	@Override
	public int getCount() {
		return imageId.length;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {

		ImageView imageView = imageViews.get(arg1);
		bmArray[arg1] = BitmapFactory.decodeResource(mActivity.getResources(),
				imageId[arg1]);

		imageView.setImageBitmap(bmArray[arg1]);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, DetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("Key", "xiuxian");
				intent.putExtras(bundle);
				mActivity.startActivity(intent);
				mActivity
						.overridePendingTransition(R.anim.alpha, R.anim.alpha2);
			}
		});

		try {
			if (imageViews.get(arg1).getParent() == null) {
				((ViewPager) arg0).addView(imageViews.get(arg1), 0);
			} else {
				((ViewPager) imageViews.get(arg1).getParent())
						.removeView(imageViews.get(arg1));
				((ViewPager) arg0).addView(imageViews.get(arg1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imageViews.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		ImageView image = (ImageView) arg2;
		image.setImageBitmap(null);
		if (bmArray[arg1] != null) {
			bmArray[arg1].recycle();
		}
		// ((ViewPager) arg0).removeView(image);
		((ViewPager) arg0).removeView((View) arg2);

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}