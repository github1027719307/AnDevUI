/*
 * Copyright 2013 Priboi Tiberiu
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kince.andevui.fragment;

import java.util.ArrayList;

import com.kince.andevui.AndApplication;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.activity.ChannelActivity;
import com.kince.andevui.adapter.MainUIFragmentAdapter;
import com.kince.andevui.entity.ChannelItem;
import com.kince.andevui.entity.ChannelManage;
import com.kince.andevui.util.BaseTools;
import com.kince.andevui.widget.ColumnHorizontalScrollView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author kince
 * @category UI主Fragment界面 侧滑 viewpager实现内容切换 仿网易的动画效果 栏目编辑
 * @since 2014.5.4 2014.5.23 2014.7.1
 * @version v1.0.0
 */
public class MainUiFragment extends Fragment {

	public static final String ARG_UI_NUMBER = "ui_number";

	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	// 栏目视图
	private LinearLayout mRadioGroup_content;
	// 栏目视图
	private LinearLayout ll_more_columns;
	// 栏目视图
	private RelativeLayout rl_column;
	// 切换视图
	private ViewPager mViewPager;
	// 编辑栏目按钮
	private ImageButton imageButton_more;
	/** 分类列表 */
	private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	/** 左阴影部分 */
	public ImageView shade_left;
	/** 右阴影部分 */
	public ImageView shade_right;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	// 切换fragment
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	// fragment依附的activity
	private Activity mActivity;
	/** 请求CODE */
	public final static int CHANNELREQUEST = 1;
	/** 调整返回的RESULTCODE */
	public final static int CHANNELRESULT = 10;
	// 是否在进行动画
	private boolean isAnimate;
	

	public MainUiFragment() {
		// Empty constructor required for fragment subclasses
	}

	// 单例
	private static MainUiFragment instance = null;

	public static synchronized MainUiFragment getInstance() {
		if (instance == null) {
			instance = new MainUiFragment();
		}
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_ui, container, false);

		mActivity = getActivity();
		mScreenWidth = BaseTools.getWindowsWidth(mActivity);
		mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
		initView(rootView);
		return rootView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 初始化layout控件
	 * 
	 */
	private void initView(View v) {
		mColumnHorizontalScrollView = (ColumnHorizontalScrollView) v
				.findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) v
				.findViewById(R.id.mRadioGroup_content);
		ll_more_columns = (LinearLayout) v.findViewById(R.id.ll_more_columns);
		rl_column = (RelativeLayout) v.findViewById(R.id.rl_column);
		mViewPager = (ViewPager) v.findViewById(R.id.mViewPager);
		shade_left = (ImageView) v.findViewById(R.id.shade_left);
		shade_right = (ImageView) v.findViewById(R.id.shade_right);
		imageButton_more = (ImageButton) v
				.findViewById(R.id.button_more_columns);
		imageButton_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击更多的按钮
				Intent intent_channel = new Intent(mActivity,
						ChannelActivity.class);
				startActivityForResult(intent_channel, CHANNELREQUEST);
				mActivity.overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				columnSelectIndex = 0;
			}
		});
		setChangelView();
	}

	/**
	 * 当栏目项发生变化时候调用
	 * 
	 */
	private void setChangelView() {
		initColumnData();
		initTabColumn();
		initFragment();
	}

	/**
	 * 获取Column栏目 数据
	 * 
	 */
	private void initColumnData() {
		userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(
				AndApplication.getAppInstance().getSQLHelper())
				.getUserChannel());

	}

	/**
	 * 初始化Column栏目项
	 * 
	 */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count = userChannelList.size();
		mColumnHorizontalScrollView.setParam(mActivity, mScreenWidth,
				mRadioGroup_content, shade_left, shade_right, ll_more_columns,
				rl_column);
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					mItemWidth, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;

			TextView columnTextView = new TextView(mActivity);
			columnTextView.setTextAppearance(mActivity,
					R.style.top_category_scroll_view_item_text);
			columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			columnTextView.setGravity(Gravity.CENTER);
			columnTextView.setPadding(5, 5, 5, 5);
			columnTextView.setId(i);
			columnTextView.setText(userChannelList.get(i).getName());
			Log.i("userChannelList.get(i).getName()", userChannelList.get(i)
					.getName() + "");
			columnTextView.setTextColor(getResources().getColorStateList(
					R.color.top_category_scroll_text_color_day));

			if (columnSelectIndex == i) {
				Log.i("columnSelectIndex", columnSelectIndex + "");
				columnTextView.setSelected(true);
			}
			columnTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
						View localView = mRadioGroup_content.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							mViewPager.setCurrentItem(i);
						}
					}
					Toast.makeText(mActivity,
							userChannelList.get(v.getId()).getName(),
							Toast.LENGTH_SHORT).show();
				}
			});
			mRadioGroup_content.addView(columnTextView, i, params);
		}
	}

	/**
	 * 选择的Column里面的Tab
	 * 
	 */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
			View checkView = mRadioGroup_content.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			if (isAnimate == false) {
				animateDown(imageButton_more);
			}
		}
		// 判断是否选中
		for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
			View checkView = mRadioGroup_content.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}

	/**
	 * @category param v
	 * 
	 */
	private void animateDown(final View v) {
		TranslateAnimation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
		animation.setDuration(1000);
		AccelerateDecelerateInterpolator i = new AccelerateDecelerateInterpolator();
		animation.setInterpolator(i);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

				isAnimate = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				TranslateAnimation as = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, 0,
						Animation.RELATIVE_TO_SELF, -1,
						Animation.RELATIVE_TO_SELF, 0);
				as.setDuration(1000);
				as.setFillAfter(true);
				AccelerateDecelerateInterpolator i = new AccelerateDecelerateInterpolator();
				as.setInterpolator(i);
				as.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// 网络数据全部加载完毕后替换图片
						isAnimate = false;
					}
				});
				v.startAnimation(as);
			}
		});
		v.startAnimation(animation);
	}

	/**
	 * @category 从数据库查询用户已经选择的频道 初始化Fragment
	 * 
	 */
	private void initFragment() {
		fragments.clear();// 清空
		int count = userChannelList.size();
		for (int i = 0; i < count; i++) {
			// Bundle data = new Bundle();
			// data.putString("key_text", userChannelList.get(i).getName());
			String channel = userChannelList.get(i).getName();
			Log.i("userChannelList.get(i).getName()1", userChannelList.get(i)
					.getName() + "");
			if (channel.equals(Constants.RECOMMEND_FRAGMENT)) {
				Fragment animation = new RecommendFragment();
				Log.i("RECOMMEND_FRAGMENT", "RECOMMEND_FRAGMENT");
				// animation.setArguments(data);
				fragments.add(animation);
			} else if (channel.equals(Constants.HOT_FRAGMENT)) {
				Fragment imitate = HotFragment.getInstance();
				Log.i("HOT_FRAGMENT", "HOT_FRAGMENT");
				// imitate.setArguments(data);
				fragments.add(imitate);
			} else if (channel.equals(Constants.POPULAR_FRAGMENT)) {
				Fragment idea = new PopularFragment();
				Log.i("POPULAR_FRAGMENT", "POPULAR_FRAGMENT");
				// idea.setArguments(data);
				fragments.add(idea);
			} else if (channel.equals(Constants.IMITATE_FRAGMENT)) {
				Fragment popular = new ImitateFragment();
				Log.i("IMITATE_FRAGMENT", "IMITATE_FRAGMENT");
				// popular.setArguments(data);
				fragments.add(popular);
			} else if (channel.equals(Constants.IDEA_FRAGMENT)) {
				Fragment requirement = new IdeaFragment();
				Log.i("IDEA_FRAGMENT", "IDEA_FRAGMENT");
				// requirement.setArguments(data);
				fragments.add(requirement);
			} else if (channel.equals(Constants.ANIMATION_FRAGMENT)) {
				Fragment design = new AnimationFragment();
				Log.i("ANIMATION_FRAGMENT", "ANIMATION_FRAGMENT");
				// design.setArguments(data);
				fragments.add(design);
			} else if (channel.equals(Constants.REQUIREMENT_FRAGMENT)) {
				Fragment design = new RequirementFragment();
				Log.i("REQUIREMENT_FRAGMENT", "REQUIREMENT_FRAGMENT");
				// design.setArguments(data);
				fragments.add(design);
			} else if (channel.equals(Constants.DESIGN_FRAGMENT)) {
				Fragment design = new DesignFragment();
				Log.i("DESIGN_FRAGMENT", "DESIGN_FRAGMENT");
				// design.setArguments(data);
				fragments.add(design);
			} else if (channel.equals(Constants.COURSE_FRAGMENT)) {
				Fragment design = new CourseFragment();
				Log.i("COURSE_FRAGMENT", "COURSE_FRAGMENT");
				// design.setArguments(data);
				fragments.add(design);
			}
		}
		for (int j = 0; j < fragments.size(); j++) {
			Log.i("fragments", fragments.get(j).toString()+"");
		}
//		SubUIFragmentPagerAdapter mAdapetr = new SubUIFragmentPagerAdapter(getFragmentManager(),
//				fragments);fucking code!!!
		MainUIFragmentAdapter mAdapetr=new MainUIFragmentAdapter(getFragmentManager(), fragments);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(pageListener);
	}

	/**
	 * @category ViewPager切换监听方法
	 * 
	 */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
			Log.i("position", position + "");
			selectTab(position);
		}
	};

	/**
	 * @category 接受从栏目界面返回的数据
	 * 
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CHANNELREQUEST:
			if (resultCode == CHANNELRESULT) {
				// 重新加载栏目
				setChangelView();
			
			}
			break;

		default:
			break;
		}
	}

}
