package com.kince.andevui.fragment;

import com.kince.andevui.AndApplication;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.activity.MainActivity;
import com.kince.andevui.adapter.MyGridPicAdapter;
import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.util.BitmapPicUtils;
import com.kince.andevui.widget.notboringactionbar.KenBurnsView;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author kince
 * @category 数据详情界面
 * @since 2014.7.3
 * @version v1.0.0
 */

@SuppressLint("ValidFragment")
public class DetailFragment extends Fragment implements View.OnClickListener,
		AbsListView.OnScrollListener {

	// 当前fragment依附的activity
	private Activity mActivity;
	// 根view
	private View rootView;
	//
	private KenBurnsView detailPicture;
	// 图片缩放
	private double picScale;
	// 猜你喜欢适配器
	private MyGridPicAdapter mAdapter;
	//
	private Bundle bundle;
	// 字体
	private Typeface typeface;
	//
	private RelativeLayout relativeLayout;
	//
	private String value;
	// 数据源
	private CodeClass[] listData;
	// 图片下载器
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	// 弹出分享选择框
	private PopupWindow popupWindow;

	private LinearLayout layout_like;
	private LinearLayout layout_comment;

	public DetailFragment(Activity activity) {
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Log.i("DetailFragment", "onCreate");

		bundle = getActivity().getIntent().getExtras();

		picScale = BitmapPicUtils.getPicScale(getResources(),
				R.drawable.indicator_ciecle);

		typeface = AndApplication.englishTypeface;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_shots_detail, container,
				false);
		initView(rootView);
		initMoreShots(rootView);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				relativeLayout.setVisibility(View.GONE);
				
			}
		}, 2000);
		return rootView;
	}

	/**
	 * @category param view
	 */
	private void initView(View view) {
		//
		detailPicture = (KenBurnsView) view.findViewById(R.id.detail_image);
		//
		RelativeLayout.LayoutParams viewPaLayoutParams = new RelativeLayout.LayoutParams(
				MainActivity.screenWidthDip,
				(int) (MainActivity.screenWidthDip * picScale));
		//
		viewPaLayoutParams.addRule(RelativeLayout.BELOW, R.id.detail_avatar);
		detailPicture.setLayoutParams(viewPaLayoutParams);

		layout_like = (LinearLayout) view.findViewById(R.id.detail_layout_like);
		layout_like.setOnClickListener(this);
		layout_comment = (LinearLayout) view
				.findViewById(R.id.detail_layout_comment);
		layout_comment.setOnClickListener(this);

		ImageView playerAvatar = (ImageView) view
				.findViewById(R.id.detail_avatar);
		playerAvatar.setOnClickListener(this);
		TextView title = (TextView) view.findViewById(R.id.detail_title);
		title.setTypeface(typeface);
		TextView player = (TextView) view.findViewById(R.id.detail_player);
		player.setOnClickListener(this);
		TextView views = (TextView) view.findViewById(R.id.detail_views);
		TextView likes = (TextView) view.findViewById(R.id.detail_likes);
		TextView comments = (TextView) view.findViewById(R.id.detail_commentss);
		TextView emptyText = (TextView) view.findViewById(R.id.empty_text);
		emptyText.setTypeface(typeface);

		if (bundle != null) {
			value = bundle.getString("Key");
			if (value.equals("xiuxian")) {
				title.setText("动画");
				player.setText("kince");
				views.setText("浏览 1127");
				likes.setText("喜欢 868");
				comments.setText("分享 126");
				// 用户头像
				playerAvatar.setBackgroundResource(R.drawable.photo);
				listData = Constants.JXX_IMAGES_CODECLASS;
				detailPicture.setResourceIds(R.drawable.indicator_serch,
						R.drawable.indicator_train);
			}
		}
		initPopWindow();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//
		case R.id.detail_avatar:

			break;

		case R.id.detail_layout_like:

			break;

		// 弹出分享
		case R.id.detail_layout_comment:
			Log.i("弹出分享", "弹出分享");
			new popAction().onClick(v);
			break;
		default:
			break;
		}
	}

	/**
	 * @category 猜你喜欢
	 * @param view
	 */
	private void initMoreShots(View view) {
		mAdapter = new MyGridPicAdapter(mActivity, listData, value, imageLoader);
		mAdapter.notifyDataSetChanged();

		relativeLayout = (RelativeLayout) view
				.findViewById(R.id.gridview_empty);
		relativeLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化弹出的pop
	 * 
	 */
	private void initPopWindow() {
		View popView = LayoutInflater.from(mActivity).inflate(
				R.layout.list_item_pop, null);
		ImageButton shotCollect = (ImageButton) popView
				.findViewById(R.id.shot_collect);
		ImageButton weibo = (ImageButton) popView.findViewById(R.id.weibo);
		ImageButton twitter = (ImageButton) popView.findViewById(R.id.twitter);
		shotCollect.setOnClickListener(this);
		weibo.setOnClickListener(this);
		twitter.setOnClickListener(this);
		popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, 50);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		// 设置popwindow出现和消失动画
		popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
	}

	/**
	 * 显示popWindow
	 * 
	 */
	public void showPop(View parent, int x, int y) {
		// 设置popwindow显示位置
		popupWindow.showAtLocation(parent, 0, x, y);
		// 获取popwindow焦点
		popupWindow.setFocusable(true);
		// 设置popwindow如果点击外面区域，便关闭。
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		if (popupWindow.isShowing()) {

		}

	}

	/**
	 * 每个ITEM中more按钮对应的点击动作
	 * 
	 */
	public class popAction implements OnClickListener {

		@Override
		public void onClick(View v) {
			int[] arrayOfInt = new int[2];
			// 获取点击按钮的坐标
			v.getLocationOnScreen(arrayOfInt);
			int x = arrayOfInt[0];
			int y = arrayOfInt[1];
			Log.i("x", x + "");
			Log.i("y", y + "");
			showPop(v, x, y);
		}
	}

}
