/**
 * 
 */
package com.kince.andevui.fragment;

import com.kince.andevui.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author kince
 * @category  
 * @since 2014.7.1
 * @version v1.0.0
 */
public class AppstoreFragment extends BaseFragment {

	public static final String ARG_UI_NUMBER = "ui_number";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View appstoreView = inflater.inflate(R.layout.fragment_appstore,
				null);
		return appstoreView;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
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
