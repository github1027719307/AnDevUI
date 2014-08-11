package com.kince.andevui.adapter;

import com.kince.andevui.R;
import com.kince.andevui.activity.DetailsActivity;
import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.fragment.AnimationFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SGVOwnPicAdapter extends BaseAdapter {

	private static String[] strArray = new String[] {
			"自定义漂亮ProgressBar",
			"扫描动画",
			"火车票出票动画",
			"优酷菜单动画",
			"漂亮进度条",
			"场景时钟" };
	private static String[] colorArray = new String[] { "#FF467283",
			"#FF997283", "#FF008B45", "#FF338B45" };
	private Context context;
	private LayoutInflater mInflater;
	CodeClass[] imageUrls;
	private ImageLoader imageLoader;
	DisplayImageOptions options;

	public SGVOwnPicAdapter(AnimationFragment fg, Context context,
			CodeClass[] codeclass, ImageLoader imageLoader) {

		this.context = context;

		this.mInflater = LayoutInflater.from(context);
		this.imageUrls = codeclass;

		this.imageLoader = imageLoader;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.pci_bg)
				.showImageForEmptyUri(R.drawable.pci_bg_bad)
				.showImageOnFail(R.drawable.pci_bg_bad).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(false)
				.displayer(new FadeInBitmapDisplayer(400))
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		PicModelHodler holder = null;

		if (convertView == null) {
			holder = new PicModelHodler();
			convertView = mInflater.inflate(R.layout.layout_own_pic, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image);
			holder.textViewShow = (TextView) convertView
					.findViewById(R.id.textview_show);
			convertView.setTag(holder);
		} else {
			holder = (PicModelHodler) convertView.getTag();
		}
		final int po = position % imageUrls.length;

		imageLoader.displayImage(imageUrls[position].getKey(),
				holder.imageView, options, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// holder.progressBar.setProgress(0);
						// holder.progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// holder.progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// holder.progressBar.setVisibility(View.GONE);
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
						// holder.progressBar.setProgress(Math.round(100.0f *
						// current / total));
					}
				});
		
		holder.textViewShow.setText(strArray[position % strArray.length]);
		holder.textViewShow.setTextColor(Color.parseColor(colorArray[position
				% colorArray.length]));

		holder.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(context, DetailsActivity.class);
				 Bundle bundle = new Bundle();
				 bundle.putString("Key", imageUrls[po].getValue());
				 intent.putExtras(bundle);
				 context.startActivity(intent);
				 ((Activity) context).overridePendingTransition(R.anim.alpha,
				 R.anim.alpha2);
			}
		});
		return convertView;
	}

	private final class PicModelHodler {
		public ImageView imageView;
		public TextView textViewShow;
	}
	
}
