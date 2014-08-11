package com.kince.andevui.adapter;

import java.util.List;

import com.kince.andevui.AndApplication;
import com.kince.andevui.R;
import com.kince.andevui.entity.CommentModel;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapter {

	private List<CommentModel> listData;
	private Activity context;
	private LayoutInflater mInflater;
	private Typeface typeface, chineseTypeface;

	public CommentsAdapter(Activity context, List listData) {
		this.context = context;
		this.listData = listData;
		this.mInflater = LayoutInflater.from(context);
		typeface = AndApplication.englishTypeface;
		chineseTypeface = AndApplication.chineseTypeface;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData != null ? listData.size() : 0;
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_comments, parent,
					false);
			holder = new ViewHolder();
			holder.avatar = (ImageView) convertView
					.findViewById(R.id.item_comment_avatar);
			holder.player = (TextView) convertView
					.findViewById(R.id.item_comment_player);
			holder.body = (TextView) convertView
					.findViewById(R.id.item_comment_body);
			holder.likes = (TextView) convertView
					.findViewById(R.id.item_comment_like);
			holder.createTime = (TextView) convertView
					.findViewById(R.id.item_comment_create_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CommentModel model = listData.get(position);

		if (model != null) {
			holder.avatar.setImageResource(R.drawable.photo);

			holder.player.setText(model.name);
			holder.player.setTypeface(chineseTypeface);
			holder.body.setText(model.comment);
			holder.body.setTypeface(typeface);
			holder.likes.setText(model.likes);
			holder.likes.setTypeface(typeface);
			holder.createTime.setText(model.createDate);
			holder.createTime.setTypeface(typeface);
		}
		return convertView;
	}

}

class ViewHolder {
	ImageView avatar;
	TextView player;
	TextView body;
	TextView likes;
	TextView createTime;
}
