package com.kince.andevui.fragment;

import com.kince.andevui.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that appears in the "content_frame", shows a animal
 * 
 */
public class CourseFragment extends Fragment {

	public static final String ARG_UI_NUMBER = "ui_number";

	public CourseFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_course, container,
				false);
		
		return rootView;
	}
}

