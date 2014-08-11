package com.kince.andevui.fragment;

import com.kince.andevui.R;
import com.kince.andevui.widget.GestureImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

@SuppressLint("ValidFragment")
public class ImageViewFragment extends Fragment {

	private View rootView;
	private String url;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ImageViewFragment(String url, ImageLoader imageLoader,
			DisplayImageOptions options) {
		this.url = url;
		this.imageLoader = imageLoader;
		this.options = options;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		rootView = inflater.inflate(R.layout.fragment_imageview, container,
				false);

		GestureImageView imageView = (GestureImageView) rootView
				.findViewById(R.id.image);

//		imageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				new AlertDialog.Builder(v.getContext())
//						.setMessage("Clicked!")
//						.setTitle("onClickTest")
//						.setPositiveButton("OK",
//								new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										dialog.dismiss();
//									}
//								}).create().show();
//			}
//		});
		// view.setImageResource(list.get(arg1).getKey());
		// view.setImageBitmap(list[arg1).getKey());
		imageLoader.displayImage(url, imageView, options,
				new SimpleImageLoadingListener() {
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
		return rootView;
	}
}
