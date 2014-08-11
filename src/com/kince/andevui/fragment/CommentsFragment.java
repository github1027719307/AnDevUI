package com.kince.andevui.fragment;

import java.util.List;

import com.kince.andevui.AndApplication;
import com.kince.andevui.R;
import com.kince.andevui.adapter.CommentsAdapter;
import com.kince.andevui.entity.CommentFactory;
import com.kince.andevui.entity.CommentModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author kince
 * @category 评论详情界面
 * @since 2014.7.3
 * @version v1.0.0
 */
@SuppressLint("ValidFragment")
public class CommentsFragment extends Fragment {

	// private ShotsData data;
	private ListView mListView;
	
	private CommentsAdapter mAdapter;
	
	private Activity a;
	
	private List<CommentModel> list;

	public CommentsFragment(Activity a) {
		this.a = a;
		this.list = CommentFactory.getComment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_comments, container,
				false);
		TextView commentsLabel = (TextView) rootView
				.findViewById(R.id.comments_label);
		commentsLabel.setTypeface(AndApplication.englishTypeface);
		mAdapter = new CommentsAdapter(a, list);
		mListView = (ListView) rootView.findViewById(R.id.comments_listview);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
		mAdapter.notifyDataSetChanged();
		return rootView;
	}
}
