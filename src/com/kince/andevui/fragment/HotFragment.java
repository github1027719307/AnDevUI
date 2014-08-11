/**
 * 
 */
package com.kince.andevui.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.kince.andevui.AndApplication;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.activity.DetailsActivity;
import com.kince.andevui.activity.MainActivity;
import com.kince.andevui.adapter.BigImageAdapter;
import com.kince.andevui.adapter.RecommendPagerAdapter;
import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.entity.ItemModel;
import com.kince.andevui.util.BitmapPicUtils;
import com.kince.andevui.util.FixedSpeedScroller;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

/**
 * @author kince
 * @category 热点UI界面
 * @since 2014.7.2
 * @version v1.0.0
 */
public class HotFragment extends BaseFragment {

	// 
	private View rootView;
	// 
	private Activity mActivity;
	// 
	private ListView listView;
	//
	private Context context;
	//
	private CodeClass[] urls;
	//
	private ViewPager viewPager;
	//
	private String[] titles;
	//
	private int[] imageResId;
	//
	private List<View> dots;
	//
	private List<ImageView> imageViews;
	//
	private TextView tv_title;
	//
	private int currentItem = 0;
	//
	private ScheduledExecutorService scheduledExecutorService;
	//
	private static double picScale;
	//
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	// 
	private BigImageAdapter adpater;
	//
	private Animator mCurrentAnimator;
	//
	private Typeface tf;
	
	public ArrayList<ItemModel> itemModels=new ArrayList<ItemModel>();
	// 单例
	private static HotFragment instance = null;

	public static synchronized HotFragment getInstance() {
		if (instance == null) {
			instance = new HotFragment();
		}
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//
		picScale = BitmapPicUtils.getPicScale(getResources(),
				R.drawable.indicator_youku);
		//
		imageResId = new int[] { R.drawable.indicator_serch,
				R.drawable.indicator_ciecle, R.drawable.indicator_train,
				R.drawable.indicator_train, R.drawable.indicator_ciecle, };
		//
		titles = new String[imageResId.length];
		titles[0] = "扫描动画";
		titles[1] = "天气动画";
		titles[2] = "火车票出票动画";
		titles[3] = "火车票出票动画";
		titles[4] = "天气动画";
		//
		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this.getActivity());
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}
        //
		tf = AndApplication.chineseTypeface;
		//
		urls = Constants.JXX_IMAGES_CODECLASS;
		
		/**
		 * 初始化显示数据
		 */
		ItemModel itemModel=new ItemModel();
		itemModel.setAuthor("kince");
		itemModel.setDescription("");
		itemModel.setTime("2014-7-15 14:19:11");
		itemModel.setTitle("扫描动画");
		itemModel.setProjectUrl("http://m2.img.srcdd.com/farm4/d/2014/0515/11/1A2EEFA26898EA170DCB57DD05B4E961_B500_900_500_400.png");
		itemModel.setGifUrl("http://m2.img.srcdd.com/farm4/d/2014/0515/11/1A2EEFA26898EA170DCB57DD05B4E961_B500_900_500_400.png");
		itemModels.add(itemModel);
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActivity = getActivity();
		context=inflater.getContext();
		rootView = inflater.inflate(R.layout.fragment_hot, container, false);
		initView(rootView);
		return rootView;
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
		listView = (ListView) v.findViewById(R.id.list);
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		View header = layoutInflater
				.inflate(R.layout.viewpaper_listimage, null);
		dots = new ArrayList<View>();
		dots.add(header.findViewById(R.id.v_dot0));
		dots.add(header.findViewById(R.id.v_dot1));
		dots.add(header.findViewById(R.id.v_dot2));
		dots.add(header.findViewById(R.id.v_dot3));
		dots.add(header.findViewById(R.id.v_dot4));
		tv_title = (TextView) header.findViewById(R.id.tv_title);
		tv_title.setTypeface(tf);
		tv_title.setText(titles[0]);//
		viewPager = (ViewPager) header.findViewById(R.id.vp);
		RelativeLayout.LayoutParams viewPaLayoutParams = new RelativeLayout.LayoutParams(
				MainActivity.screenWidthDip,
				(int) (MainActivity.screenWidthDip * picScale));
		viewPager.setLayoutParams(viewPaLayoutParams);
		setViewPagerScrollSpeed();
		viewPager.setAdapter(new RecommendPagerAdapter(imageResId, imageViews,
				getActivity()));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		adpater = new BigImageAdapter(HotFragment.this, urls, imageLoader);
		listView.addHeaderView(header);
		AnimationAdapter animAdapter = new ScaleInAnimationAdapter(adpater);
		animAdapter.setAbsListView(listView);
		animAdapter.setInitialDelayMillis(300);
		listView.setAdapter(animAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, DetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("Key", urls[position % urls.length].getValue());
				intent.putExtras(bundle);
				context.startActivity(intent);
				mActivity
						.overridePendingTransition(R.anim.alpha, R.anim.alpha2);
			}
		});
		listView.setOnScrollListener((new PauseOnScrollListener(imageLoader,
				true, true)));
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * @category 设置ViewPager的滑动速度
	 * 
	 */
	private void setViewPagerScrollSpeed() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					viewPager.getContext());
			mScroller.set(viewPager, scroller);
		} catch (NoSuchFieldException e) {

		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem, true);
		};
	};

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 5,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * @category 定时切换顶部图片
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	/**
	 * @category ViewPager监听器
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(final int position) {
			currentItem = position;

			AnimatorSet set = new AnimatorSet();
			set.play(ObjectAnimator.ofFloat(tv_title, View.ALPHA, 1.0f, 0.0f))
					.with(ObjectAnimator.ofFloat(tv_title, View.SCALE_X, 1.0f,
							0.5f))
					.with(ObjectAnimator.ofFloat(tv_title, View.SCALE_Y, 1.0f,
							0.5f));

			set.setDuration(300);
			set.setInterpolator(new DecelerateInterpolator());
			set.addListener(new AnimatorListener() {

				@Override
				public void onAnimationCancel(Animator arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animator arg0) {
					// TODO Auto-generated method stub
					mCurrentAnimator = null;
					AnimatorSet set = new AnimatorSet();
					tv_title.setText(titles[position]);
					set.play(
							ObjectAnimator.ofFloat(tv_title, View.ALPHA, 0.0f,
									1.0f))
							.with(ObjectAnimator.ofFloat(tv_title,
									View.SCALE_X, 0.5f, 1.0f))
							.with(ObjectAnimator.ofFloat(tv_title,
									View.SCALE_Y, 0.5f, 1.0f));
					set.setDuration(300);
					set.setInterpolator(new DecelerateInterpolator());
					set.start();
				}

				@Override
				public void onAnimationRepeat(Animator arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationStart(Animator arg0) {
					// TODO Auto-generated method stub

				}

			});
			set.start();
			mCurrentAnimator = set;
			dots.get(oldPosition).setBackgroundResource(R.drawable.line_normal);
			dots.get(position).setBackgroundResource(R.drawable.line_focused);
			oldPosition = position;

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

}
