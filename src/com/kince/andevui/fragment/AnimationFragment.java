package com.kince.andevui.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.etsy.android.grid.StaggeredGridView;
import com.kince.andevui.AndApplication;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.activity.MainActivity;
import com.kince.andevui.adapter.RecommendPagerAdapter;
import com.kince.andevui.adapter.SGVOwnPicAdapter;
import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.util.BitmapPicUtils;
import com.kince.andevui.util.FixedSpeedScroller;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

/**
 * @author kince 
 * @category 动画界面 展示流行动画效果 
 * @since 2014.5.23 2014.7.2 
 * @version v1.0.0
 * 
 */
public class AnimationFragment extends BaseFragment implements
		AbsListView.OnItemClickListener {

	public static final String SAVED_DATA_KEY = "SAVED_DATA";

	private Activity activity;

	private String key_text;
    // 显示动画数据
	private StaggeredGridView mStaggeredGridView;
	// 显示动画适配器
	private SGVOwnPicAdapter mAdapter;
	// 
	private ArrayList<String> mData;
	// 上下文
	private Context mContext;
	// 顶部推荐ViewPager
	private ViewPager viewPager;
	// 
	private String[] titles;
	// 
	private List<View> dots;
	// 
	private List<ImageView> imageViews;
	// 
	private TextView tv_title;
	// 
	private int currentItem = 0;
	// 线程池
	private ScheduledExecutorService scheduledExecutorService;
	// 
	private static double picScale;
	// 
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	// 
	public CodeClass[] imageUrls;
    //
	private int[] imageResId;
    //顶部推荐 自动更新
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem, true);
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取传递参数
		Bundle args = getArguments();
		key_text = args != null ? args.getString("key_text") : "";
		// 初始化数据
		initData();
        
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this.activity = activity;
		super.onAttach(activity);
	}

	/** 
	 * 此方法意思为fragment是否可见 ,可见时候加载数据
	 * 
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			// fragment可见时加载数据

		} else {
			// fragment不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View animationView = inflater.inflate(R.layout.fragment_animation,
				container, false);
		mContext = animationView.getContext();
		// mGridView.setSelector(android.R.color.darker_gray);
		// do we have saved data?
		if (savedInstanceState != null) {
			mData = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
		}
		if (mData == null) {
//			mData = SampleData.generateSampleData();
		}
		initView(animationView);
		return animationView;
	}

	/**
	 * 初始化数据
	 * 
	 */
	public void initData() {
		imageUrls = Constants.JXX_IMAGES_CODECLASS;
		//
		picScale = BitmapPicUtils.getPicScale(getResources(),
				R.drawable.indicator_ciecle);
		// 推荐的图片
		imageResId = new int[] { R.drawable.indicator_ciecle,
				R.drawable.indicator_serch, R.drawable.indicator_train,
				R.drawable.indicator_youku };
		// 推荐的标题
		titles = new String[imageResId.length];
		titles[0] = "温度仪表盘";
		titles[1] = "雷达扫描效果";
		titles[2] = "火车票出票动画";
		titles[3] = "优酷导航菜单";

		imageViews = new ArrayList<ImageView>();

		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this.getActivity());
			// imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}
	}

	/**
	 * 初始化View
	 */
	public void initView(View v) {
		mStaggeredGridView = (StaggeredGridView) v.findViewById(R.id.grid_view);
		initGridView();
		mAdapter = new SGVOwnPicAdapter(AnimationFragment.this, mContext, imageUrls,
				imageLoader);
		mAdapter.notifyDataSetChanged();
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				mAdapter);
		swingBottomInAnimationAdapter.setAbsListView(mStaggeredGridView);
		swingBottomInAnimationAdapter.setInitialDelayMillis(450);

		mStaggeredGridView.setAdapter(swingBottomInAnimationAdapter);
		mStaggeredGridView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
		mStaggeredGridView.setOnItemClickListener(this);
	}

	/**
	 * @category 顶部推荐
	 * 
	 */
	private void initGridView() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		View header = layoutInflater.inflate(R.layout.viewpaper_image,
				mStaggeredGridView, false);
		dots = new ArrayList<View>();
		dots.add(header.findViewById(R.id.v_dot0));
		dots.add(header.findViewById(R.id.v_dot1));
		dots.add(header.findViewById(R.id.v_dot2));
		dots.add(header.findViewById(R.id.v_dot3));

		tv_title = (TextView) header.findViewById(R.id.tv_title);
		tv_title.setTypeface(AndApplication.chineseTypeface);
		tv_title.setText(titles[0]);//
		viewPager = (ViewPager) header.findViewById(R.id.vp);
		RelativeLayout.LayoutParams viewPaLayoutParams = new RelativeLayout.LayoutParams(
				MainActivity.screenWidthDip, (int) (MainActivity.screenWidthDip
						* picScale / 2));
		viewPager.setLayoutParams(viewPaLayoutParams);
		setViewPagerScrollSpeed();
		viewPager.setAdapter(new RecommendPagerAdapter(imageResId, imageViews,
				getActivity()));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		mStaggeredGridView.addHeaderView(header);
        
	}

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
	 * @category 用于循环ViewPager
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
	 * @category 顶部推荐ViewPager监听器
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

			Animation animation = new AlphaAnimation(1.0f, 0);
			animation.setDuration(300);
			animation.setInterpolator(new DecelerateInterpolator());
			animation.setAnimationListener(new AnimationListener() {

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
					// TODO Auto-generated method stub
					tv_title.setText(titles[position]);
					Animation animation1 = new AlphaAnimation(0, 1.0f);
					animation1.setDuration(300);
					animation1.setInterpolator(new AccelerateInterpolator());
					tv_title.startAnimation(animation1);
				}
			});
			tv_title.startAnimation(animation);

			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
