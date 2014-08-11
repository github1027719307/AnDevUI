package com.kince.andevui.activity;

import java.io.File;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.animation.Rotate3DAnimation;
import com.kince.andevui.util.ActivityStackControlUtil;
import com.kince.andevui.widget.ProgressWheel;
import com.kince.andevui.widget.SlidingUpPanel;
import com.kince.andevui.widget.SlidingUpPanel.OnPanelCloseListener;
import com.kince.andevui.widget.SlidingUpPanel.OnPanelOpenListener;
import com.kince.andevui.widget.SlidingUpPanel.OnPanelScrollListener;
import com.kince.andevui.widget.blur.Blur;
import com.kince.andevui.widget.blur.ImageUtils;
import com.kince.andevui.widget.shimmer.Shimmer;
import com.kince.andevui.widget.shimmer.ShimmerTextView;
import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennClient.LoginListener;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @author kince
 * @category 登陆注册界面
 * 
 * @since 2014.5.4 2014.7.1 2014.7.9
 * @version v1.0.0
 */
public class WelcomeActivity extends FragmentActivity implements
		OnClickListener {
	//
	private static final String BLURRED_IMG_PATH = "blurred_image.png";

	private SlidingUpPanel mSlidingUpPanel;

	private ShimmerTextView mCoverHint;
	private Shimmer shimmer;

	private ImageView iv_bj;
	private ImageView mBlurredImage;

	private int screenWidth;
	private int screenHeight;

	private boolean isFirst = false;
	//
	private RelativeLayout linear;
	//
	private BootstrapCircleThumbnail image;

	private Animation alphaAnimation;
	private RelativeLayout imageRelativeLayout;

	private Animator mCurrentAnimator;

	private ProgressWheel progress;

	private static final int GO_HOME = 3;
	private static final int INIT_ANIMATION = 1;
	private static final int GO_BACK = 2;

	private Shader shader;
	//
	private BootstrapButton bb_lookaround;
	//
	private LinearLayout ll_nest;
	// 3D翻转效果
	private Animation animation;

	/** 第三方登陆按钮 */
	private ImageButton ib_login_qq;
	private ImageButton ib_login_sina;
	private ImageButton ib_login_renren;
	/** 人人 */
	private RennClient rennClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityStackControlUtil.add(this);

		setContentView(R.layout.activity_welcome);

		ib_login_qq = (ImageButton) findViewById(R.id.ib_login_qq);
		ib_login_qq.setOnClickListener(this);
		ib_login_sina = (ImageButton) findViewById(R.id.ib_login_sina);
		ib_login_sina.setOnClickListener(this);
		ib_login_renren = (ImageButton) findViewById(R.id.ib_login_renren);
		ib_login_renren.setOnClickListener(this);
		
		initClient();
		initDrawable();
		initMain();
		initBackgroungImage();
		initSlidingUpPanel();
		initProgressBar();
		initBlur();
		initAnimation();
		initEditText();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	protected void onResume() {
		//
		super.onResume();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mSlidingUpPanel.openPanel();
			}
		}, 3000);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void initMain() {

		screenWidth = ImageUtils.getScreenWidth(this);
		screenHeight = ImageUtils.getScreenHeight(this);

		SharedPreferences preferences = getSharedPreferences("first_pref",
				MODE_PRIVATE);
		isFirst = preferences.getBoolean("isFirst", true);

		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// if (!isFirst) {
		// goGuide();
		// } else {
		// goHome();
		// }
		// }
		// }, 2000);

	}

	/**
	 * @category 初始化第三方登陆
	 * 
	 */
	private void initClient() {
		rennClient = RennClient.getInstance(this);// 获取实例
		rennClient.init(Constants.RENREN_APP_ID, Constants.RENREN_API_KEY,
				Constants.RENREN_SECRET_KEY);// 设置应用程序信息
		rennClient.setScope("read_user_album read_user_status");
		rennClient.setTokenType("bearer"); // 使用bearer token
	}

	private void initBackgroungImage() {
		iv_bj = (ImageView) findViewById(R.id.login_iv_bj);

	}

	private void initDrawable() {
		ShapeDrawable bg = new ShapeDrawable(new RectShape());
		int[] pixels = new int[] { 0xFF8E8E8E, 0xFF8E8E8E, 0xFF8E8E8E,
				0xFF8E8E8E, 0xFF8E8E8E, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF };
		Bitmap bm = Bitmap.createBitmap(pixels, 8, 1, Bitmap.Config.ARGB_8888);
		shader = new BitmapShader(bm, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);
	}

	private void initSlidingUpPanel() {
		mSlidingUpPanel = (SlidingUpPanel) findViewById(R.id.sliding_up_panel);
		mCoverHint = (ShimmerTextView) findViewById(R.id.shimmer_tv);
		shimmer = new Shimmer();
		shimmer.setDuration(5000);
		shimmer.start(mCoverHint);
		mSlidingUpPanel.setOnPanelOpenListener(new OnPanelOpenListener() {
			@Override
			public void onPanelOpened() {
				mHandler.sendEmptyMessageDelayed(INIT_ANIMATION, 200);
				image.setVisibility(View.VISIBLE);
			}
		});
		mSlidingUpPanel.setOnPanelCloseListener(new OnPanelCloseListener() {
			@Override
			public void onPanelClosed() {

			}
		});
		mSlidingUpPanel.setOnPanelScrolledListener(new OnPanelScrollListener() {
			@Override
			public void onPanelScrolled(float offset) {

			}
		});

	}

	private void initBlur() {
		mBlurredImage = (ImageView) findViewById(R.id.blurred_image);
		mBlurredImage.setAlpha(0.95f);
		// Try to find the blurred image
		final File blurredImage = new File(getFilesDir() + BLURRED_IMG_PATH);
		if (!blurredImage.exists()) {
			new Thread(new Runnable() {

				@Override
				public void run() {

					// No image found => let's generate it!
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap image = BitmapFactory.decodeResource(getResources(),
							R.drawable.wp, options);
					Bitmap newImg = Blur.fastblur(WelcomeActivity.this, image,
							12);
					ImageUtils.storeImage(newImg, blurredImage);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							updateView(screenWidth);
						}
					});

				}
			}).start();

		} else {
			// The image has been found. Let's update the view
			updateView(screenWidth);

		}
	}

	private void updateView(final int screenWidth) {
		Bitmap bmpBlurred = BitmapFactory.decodeFile(getFilesDir()
				+ BLURRED_IMG_PATH);
		bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth,
				screenHeight, false);
		mBlurredImage.setImageBitmap(bmpBlurred);
	}

	private void initAnimation() {
		//
		alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
		alphaAnimation.setDuration(400);
		// 3d翻转
		animation = new Rotate3DAnimation(-180, 0);
		animation.setDuration(500);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
	}

	private void startInitAnimator() {
		// TODO Auto-generated method stub
		AnimatorSet set = new AnimatorSet();
		linear.setVisibility(View.VISIBLE);
		set.play(ObjectAnimator.ofFloat(linear, View.Y, 700, 400));
		set.setDuration(1000);
		set.setInterpolator(new DecelerateInterpolator());
		set.start();
	}

	protected void startLoginAnimation() {
		// TODO Auto-generated method stub

		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(imageRelativeLayout, View.Y, 0, 450))
				.with(ObjectAnimator.ofFloat(linear, View.ALPHA, 1.0f, 0.0f));

		set.setDuration(1000);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				mCurrentAnimator = null;
				progress.setRimShader(shader);
				progress.spin();
				progress.setVisibility(View.VISIBLE);
				// String passWdStr = passWd.getEditText().getText() + "";
				// String userStr = usrName.getEditText().getText() + "";
				String passWdStr = "AndDevUI";
				String userStr = "pass";
				// if (userStr.equals("AndDevUI") && passWdStr.equals("love")) {
				// mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
				// } else if (userStr.equals("user") &&
				// passWdStr.equals("pass")) {
				// mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
				// } else {
				// mHandler.sendEmptyMessageDelayed(GO_BACK, 2000);
				// }
				goHome();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;
	}

	private void initProgressBar() {
		// TODO Auto-generated method stub
		imageRelativeLayout = (RelativeLayout) findViewById(R.id.image_relative);
		progress = (ProgressWheel) findViewById(R.id.login_progressBar);
		image = (BootstrapCircleThumbnail) findViewById(R.id.login_iv_headportrait);

	}

	private void initEditText() {
		// TODO Auto-generated method stub
		linear = (RelativeLayout) findViewById(R.id.linear);

		bb_lookaround = (BootstrapButton) findViewById(R.id.lookaround);
		bb_lookaround.setOnClickListener(this);

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_BACK:
				goBack();
				break;
			case INIT_ANIMATION:
				startInitAnimator();
				break;
			}
		}
	};

	/**
	 * 进入主界面
	 */
	protected void goHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		// String usrNameStr = usrName.getEditText().getText() + "";
		String usrNameStr = "";
		if (usrNameStr.equals("AndDevUI")) {
			intent.putExtra("Key", "AndDevUI");
		} else {
			intent.putExtra("Key", "user");
		}
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		finish();
	}

	protected void goBack() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "密码不正确！登入失败", Toast.LENGTH_SHORT).show();
		progress.setVisibility(View.GONE);
		progress.stopSpinning();

		startBackAnimation();

	}

	private void startBackAnimation() {
		// TODO Auto-generated method stub

		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(imageRelativeLayout, View.Y, 450, 0))
				.with(ObjectAnimator.ofFloat(linear, View.ALPHA, 0f, 1.0f))
				.with(ObjectAnimator.ofFloat(bb_lookaround, View.ALPHA, 0f,
						1.0f));

		set.setDuration(1000);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				mCurrentAnimator = null;
				progress.setVisibility(View.VISIBLE);
				String passWdStr = "";
				if (passWdStr.equals("1992")) {
					mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
				} else {
					mHandler.sendEmptyMessageDelayed(GO_BACK, 2000);
				}

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;
	}

	private void goGuide() {
		Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		this.finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.lookaround:
			goHome();
			break;

		case R.id.ib_login_qq:

			break;
		case R.id.ib_login_sina:

			break;
		case R.id.ib_login_renren:

			//
			rennClient.setLoginListener(new LoginListener() {
				@Override
				public void onLoginSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(WelcomeActivity.this, "登录成功",
							Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}

				@Override
				public void onLoginCanceled() {
					
				}
			});
			rennClient.login(this);
			break;
		default:
			break;
		}
	}

}
