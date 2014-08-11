package com.kince.andevui.fragment;

import com.kince.andevui.R;
import com.kince.andevui.widget.scrollbarpanellib.Clock;
import com.kince.andevui.widget.scrollbarpanellib.ExtendedListView;
import com.kince.andevui.widget.scrollbarpanellib.ExtendedListView.OnPositionChangedListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * @author kince
 * @category  
 * @since 2014.7.1
 * @version v1.0.0
 */
public class CollectFragment extends BaseFragment implements
		OnPositionChangedListener {

	public static final String ARG_UI_NUMBER = "ui_number";
	private ExtendedListView mListView;
	private Context mContext;

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
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_collect, null);
		initView(v);
		mContext = getActivity();
		return v;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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
		mListView = (ExtendedListView) v.findViewById(android.R.id.list);
		mListView.setAdapter(new DummyAdapter());
		mListView.setCacheColorHint(Color.TRANSPARENT);
		mListView.setOnPositionChangedListener(this);
	}

	private class DummyAdapter extends BaseAdapter {

		private int mNumDummies = 100;

		@Override
		public int getCount() {
			return mNumDummies;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fragment_list_item, parent, false);
			}
			return convertView;
		}
	}

	@Override
	public void onPositionChanged(ExtendedListView listView, int position,
			View scrollBarPanel) {
		Clock analogClockObj = (Clock) scrollBarPanel
				.findViewById(R.id.analogClockScroller);

		TextView tv = (TextView) scrollBarPanel.findViewById(R.id.timeTextView);
		tv.setText("" + position);

		Time timeObj = new Time();
		analogClockObj.setSecondHandVisibility(false);
		analogClockObj.setVisibility(View.VISIBLE);
		timeObj.set(position + 3, position, 5, 0, 0, 0);
		analogClockObj.onTimeChanged(timeObj);

	}

}
