package com.kince.andevui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.kince.andevui.AndApplication;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.adapter.MyGridPicAdapter;
import com.kince.andevui.entity.CodeClass;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 */
@SuppressLint("ValidFragment")
public class GirlFragment extends Fragment implements View.OnClickListener,
		AbsListView.OnScrollListener {

	private RelativeLayout progressView;
	private Bundle bundle;
	private Typeface typeface, chineseTypeface;
	private Activity a;
	private StaggeredGridView mGridView;
	private MyGridPicAdapter mAdapter;
	private CodeClass[] listData;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private String str;

	public GirlFragment(Activity a) {
		this.a = a;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		typeface = AndApplication.englishTypeface;
		chineseTypeface = AndApplication.chineseTypeface;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_girl, container, false);
		bundle = a.getIntent().getExtras();
		str = bundle.getString("Key");
		initView(rootView, str);
		initMoreShots(rootView);
		a.getActionBar().setTitle("The Beauty");
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				progressView.setVisibility(View.GONE);
				mGridView.setVisibility(View.VISIBLE);
			}
		}, 2000);
		return rootView;
	}

	private void initView(ViewGroup view, String str) {
		// TODO Auto-generated method stub

		ImageView imageView = (ImageView) view.findViewById(R.id.player_avatar);
		TextView name = (TextView) view.findViewById(R.id.player_name);
		TextView likes = (TextView) view.findViewById(R.id.player_likes_count);
		TextView followers = (TextView) view.findViewById(R.id.player_ff);
		TextView shortLaber = (TextView) view.findViewById(R.id.shots_label);

		shortLaber.setTypeface(chineseTypeface);
		likes.setTypeface(typeface);
		followers.setTypeface(typeface);
		name.setTypeface(typeface);
		if (str != null) {
			if (str.equals("xiuxian")) {
				imageView.setBackgroundResource(R.drawable.photo);
				name.setText("都叫兽");
				likes.setText("488 likes and 375 likes received");
				followers.setText("3370 Following and 3370 follower");
				listData = Constants.JXX_IMAGES_CODECLASS;
			}
		}
		name.setTypeface(chineseTypeface);
	}

	private void initMoreShots(View view) {
		mGridView = (StaggeredGridView) view
				.findViewById(R.id.player_more_shots);
		mAdapter = new MyGridPicAdapter(a, listData, str, imageLoader);
		mAdapter.notifyDataSetChanged();
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				mAdapter);
		swingBottomInAnimationAdapter.setAbsListView(mGridView);
		swingBottomInAnimationAdapter.setInitialDelayMillis(450);

		mGridView.setAdapter(swingBottomInAnimationAdapter);
		mGridView.setOnScrollListener(this);
		mGridView.setVisibility(View.GONE);
		progressView = (RelativeLayout) view.findViewById(R.id.progress_bar);
		progressView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
