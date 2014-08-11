/**
 * 
 */
package com.kince.andevui.fragment;

import com.kince.andevui.R;
import com.kince.andevui.widget.shimmer.Shimmer;
import com.kince.andevui.widget.shimmer.ShimmerTextView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @category 关于界面 程序的一个简要介绍 包括开发人员 
 * @author kince
 * @version 1.0.0
 * @since 2014.5.16 2014.7.1
 * 
 */
public class AboutFragment extends BaseFragment {

	
	public static final String ARG_UI_NUMBER = "ui_number";

	private ShimmerTextView mShimmerTextView;
	private Shimmer shimmer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View aboutView=inflater.inflate(R.layout.fragment_about, null);
		mShimmerTextView=(ShimmerTextView) aboutView.findViewById(R.id.about_tv_shimmer);
		shimmer = new Shimmer();
		shimmer.setDuration(5000);
		shimmer.start(mShimmerTextView);
		return aboutView;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}

	@Override
	public void initView(View v) {
		// TODO Auto-generated method stub
		super.initView(v);
	}


	
	
}
