package com.kince.andevui.adapter;

import com.kince.andevui.AndApplication;
import com.kince.andevui.R;
import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.fragment.HotFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BigImageAdapter extends BaseAdapter {

	// private Context context;
	private LayoutInflater mInflater;
	DisplayImageOptions options;
	private ImageLoader imageLoader;
	CodeClass[] imageUrls;

	private String[] str = { "我只对你说一句话：谢了、够哥们", "生活是天籁，需要凝神静听",
			"从前幸福很简单，现在简单很幸福", "人生是湛蓝的天空", "把握住自己的心", "朋友，是在最后可以给你力量的人",
			"你的温暖弥漫了这个世界", "梦自己想梦的，做自己想做的", "感谢你们一直都在", "你们是我最宝贵的财富",
			"快乐着就是幸福的，适合自己的就是最好的", "自己要过得精彩", "千万次地问" };

	// int[] imagetIds = { R.drawable.j_1, R.drawable.j_2, R.drawable.j_3,
	// R.drawable.j_4, R.drawable.j_5, R.drawable.j_6, R.drawable.a_1,
	// R.drawable.a_2, R.drawable.a_3, R.drawable.a_4, R.drawable.a_5,
	// R.drawable.a_6, R.drawable.a_7, R.drawable.a_8, R.drawable.a_9,
	// R.drawable.a_10, R.drawable.a_11, R.drawable.a_12, R.drawable.a_13,
	// R.drawable.a_14, R.drawable.a_15, R.drawable.a_16, R.drawable.a_17,
	// R.drawable.a_18, R.drawable.a_19, R.drawable.a_20, R.drawable.a_21,
	// R.drawable.a_22, R.drawable.a_23 };

	public BigImageAdapter(HotFragment fg, CodeClass[] imageUrls,
			ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
		// this.listData = PicModel.getInstance();
		this.mInflater = LayoutInflater.from(fg.getActivity());

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.big_bg)
				.showImageForEmptyUri(R.drawable.big_bg_bad)
				.showImageOnFail(R.drawable.big_bg_bad).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(false)
				.displayer(new FadeInBitmapDisplayer(400))
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
		this.imageUrls = imageUrls;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls.length * 2;
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
			convertView = mInflater.inflate(R.layout.image_item, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image);
			holder.textView = (TextView) convertView
					.findViewById(R.id.textview);
			convertView.setTag(holder);

		} else {
			holder = (PicModelHodler) convertView.getTag();
		}
		holder.textView.setText(str[position % str.length]);
		holder.textView.setTypeface(AndApplication.chineseTypeface);
		imageLoader.displayImage(
				imageUrls[position % imageUrls.length].getKey(),
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
		return convertView;
	}

	private final class PicModelHodler {
		public ImageView imageView;
		public TextView textView;
	}
}
