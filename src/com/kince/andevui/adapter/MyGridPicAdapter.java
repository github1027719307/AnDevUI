package com.kince.andevui.adapter;

import com.kince.andevui.R;
import com.kince.andevui.activity.BeautifulImageViewActivity;
import com.kince.andevui.entity.CodeClass;
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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyGridPicAdapter extends BaseAdapter {

	private CodeClass[] listData;
	private String value;
	// private int[] drawableArray = new int[] { R.drawable.aa, R.drawable.ab,
	// R.drawable.ac, R.drawable.ad, R.drawable.ae };
	private Context context;
	private LayoutInflater mInflater;
	DisplayImageOptions options;
	private ImageLoader imageLoader;

	public MyGridPicAdapter(Context context, CodeClass[] listData,
			String value, ImageLoader imageLoader) {
		this.context = context;
		this.listData = listData;
		this.mInflater = LayoutInflater.from(context);
		this.value = value;
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
		// return drawableArray != null ? drawableArray.length : 0;
		return listData != null ? listData.length : 0;
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
		PicHodler holder = null;

		if (convertView == null) {
			holder = new PicHodler();
			convertView = mInflater.inflate(R.layout.layout_own_gird, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (PicHodler) convertView.getTag();
		}
		// holder.imageView.setImageDrawable(context.getResources().getDrawable(
		// listData.get(position).getKey()));

		imageLoader.displayImage(listData[position].getKey(), holder.imageView,
				options, new SimpleImageLoadingListener() {
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
		// holder.imageView.setImageBitmap(listData[].getKey());
		final int po = position;
		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,
						BeautifulImageViewActivity.class);
				Bundle bundle = new Bundle();
				if (value == null) {
					bundle.putString("Key", listData[po].getValue());
				} else {
					bundle.putString("Key", value);
				}

				bundle.putInt("position", po);
				intent.putExtras(bundle);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.alpha,
						R.anim.alpha2);

			}
		});
		return convertView;
	}

	private final class PicHodler {
		public ImageView imageView;
	}

}
