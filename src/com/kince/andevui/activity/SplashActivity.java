/**
 * 
 */
package com.kince.andevui.activity;

import java.util.ArrayList;

import com.kince.andevui.AndApplication;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.util.BaseTools;
import com.renn.rennsdk.RennClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author kince
 * @category 程序启动界面 使用弹跳的动画方式 使启动界面给人一种印象深刻的感觉
 * @since 2014.7.1 2014.7.8 2014.7.9 2014.8.11
 * @version v1.0.0
 * 
 */
public class SplashActivity extends FragmentActivity {

	/** 人人第三方登陆 */
	private RennClient rennClient;
	private boolean isRenRenLogin = false;

	/** 新浪第三方登陆 */
	private boolean isSinaLogin = false;

	/** QQ第三方登陆 */
	private boolean isQQLogin = false;

	private Rect[] bounds;

	private TextView mTextView1;
	private TextView mTextView2;
	private TextView mTextView3;
	private TextView mTextView4;
	private TextView mTextView5;
	private TextView mTextView6;
	private TextView mTextView7;

	private WindowManager windowManager;
	private WindowManager.LayoutParams params;

	private int firstX;
	private int firstY;

	private int secendX;
	private int secendY;

	private float deltaX;
	private float deltaY;

	private int statusBarHeight;

	private boolean stop = false;

	private ImageView item;

	private int i = 0;

	private int position = 0;

	private ArrayList<TextView> arrayList = new ArrayList<TextView>();

	private Handler handler = new Handler();

	private Runnable jumpRunnable = new Runnable() {
		@Override
		public void run() {

			// if (!stop) {
			// if (firstX != secendX && Math.abs(deltaX) > 0.01F) {
			//
			// Log.i("i", i+"");
			// i++;
			//
			// firstX += deltaX;
			// firstY += deltaY;
			//
			// deltaY += 0.35F;
			// deltaX *= 0.95F;
			//
			// if (firstY >= secendY) {
			// deltaY = -deltaY;
			// deltaY *= 0.6F;
			// deltaX *= 0.6F;
			// }
			//
			// params.x = firstX;
			// params.y = firstY;
			// windowManager.updateViewLayout(item, params);
			//
			// handler.postDelayed(this, 12L);
			// }
			// }

			if (!stop) {
				i++;
				if (position > 12) {
					stop = true;
					/**
					 * 动画结束 开始检测是否是第一次使用本程序 如果是则进入引导页 如果不是第一次使用 判断是否已经登录
					 * 如果没有登录则进入登录界面 如果登录则进入主界面
					 */
					item.setVisibility(View.GONE);
					isRenRenLogin = rennClient.isLogin();
					if (isRenRenLogin || isSinaLogin || isQQLogin) {
						
						AndApplication.isLogin=true;
						Intent intent = new Intent(SplashActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();
					} else {
						Intent intent = new Intent(SplashActivity.this,
								WelcomeActivity.class);
						startActivity(intent);
						finish();
					}
				}

				firstX += deltaX;
				firstY += deltaY;

				if (i - 10 == 1) {
					i -= 10;
					deltaY = -deltaY;
					position++;
					Log.i("position", position + "");
					arrayList.get(position / 2).scrollTo(0, 10);

				}

				params.x = firstX;
				params.y = firstY;
				windowManager.updateViewLayout(item, params);

				handler.postDelayed(this, 17L);
			}
		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);

		initView();
		getWedgitPosition();
		initClient();
		// 获取状态栏高度
		statusBarHeight = BaseTools.getStatusBarHeight(this);
		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		bounds = new Rect[7];

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				//
				stop = false;
				//
				firstY = bounds[0].top - statusBarHeight;
				firstX = bounds[0].left + bounds[0].width() / 2 - 25;

				Log.d("y", firstY + "");
				Log.d("x", firstX + "");

				secendY = bounds[1].top - statusBarHeight;
				secendX = bounds[1].left + bounds[1].width() / 2 - 25;

				Log.d("ty", secendY + "");
				Log.d("tx", secendX + "");

				deltaY = -4;
				deltaX = (secendX - firstX) / 21;

				Log.d("vy", deltaY + "");
				Log.d("vx", deltaX + "");

				if (item != null) {
					windowManager.removeView(item);
					item = null;
				}

				item = new ImageView(SplashActivity.this);
				item.setImageResource(R.drawable.ic_launcher);

				params = new WindowManager.LayoutParams(50, 50);
				params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
				params.gravity = Gravity.LEFT | Gravity.TOP;
				params.type = WindowManager.LayoutParams.TYPE_PHONE;
				params.format = PixelFormat.RGBA_8888;
				params.width = 50;
				params.height = 50;

				windowManager.addView(item, params);

				params.x = firstX;
				params.y = firstY;
				windowManager.updateViewLayout(item, params);

				handler.postDelayed(jumpRunnable, 200L);
			}
		}, 1000);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (item != null) {
			windowManager.removeView(item);
			item = null;
		}
	}

	/**
	 * @category
	 
	 
	 
	 * 
	 */
	private void initClient() {
		rennClient = RennClient.getInstance(this);// 获取实例
		rennClient.init(Constants.RENREN_APP_ID, Constants.RENREN_API_KEY,
				Constants.RENREN_SECRET_KEY);// 设置应用程序信息
		rennClient.setScope("read_user_album read_user_status");
		rennClient.setTokenType("bearer"); // 使用bearer token
	}

	/**
	 * @category
	 
	 
	 
	 * 
	 */
	private void initView() {
		mTextView1 = (TextView) findViewById(R.id.tv_one);
		mTextView2 = (TextView) findViewById(R.id.tv_two);
		mTextView3 = (TextView) findViewById(R.id.tv_three);
		mTextView4 = (TextView) findViewById(R.id.tv_four);
		mTextView5 = (TextView) findViewById(R.id.tv_five);
		mTextView6 = (TextView) findViewById(R.id.tv_six);
		mTextView7 = (TextView) findViewById(R.id.tv_seven);

		arrayList.add(mTextView1);
		arrayList.add(mTextView2);
		arrayList.add(mTextView3);
		arrayList.add(mTextView4);
		arrayList.add(mTextView5);
		arrayList.add(mTextView6);
		arrayList.add(mTextView7);
	}

	/**
	 * @category
	 
	 
	  
	 * 
	 */
	private void getWedgitPosition() {
		// 获取
		ViewTreeObserver vto1 = mTextView1.getViewTreeObserver();
		vto1.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mTextView1.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				int[] location = new int[2];
				mTextView1.getLocationOnScreen(location);
				bounds[0] = new Rect(location[0], location[1], mTextView1
						.getMeasuredWidth() + location[0], mTextView1
						.getMeasuredHeight() + location[1]);
				Log.d("bounds[0]", bounds[0].left + " " + bounds[0].right + " "
						+ bounds[0].top + " " + bounds[0].bottom + " ");

			}
		});

		ViewTreeObserver vto2 = mTextView2.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mTextView2.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				int[] location = new int[2];
				mTextView2.getLocationOnScreen(location);
				bounds[1] = new Rect(location[0], location[1], mTextView2
						.getMeasuredWidth() + location[0], mTextView2
						.getMeasuredHeight() + location[1]);
				Log.d("bounds[1]", bounds[1].left + " " + bounds[1].right + " "
						+ bounds[1].top + " " + bounds[1].bottom + " ");

			}
		});

		ViewTreeObserver vto3 = mTextView3.getViewTreeObserver();
		vto3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mTextView3.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				int[] location = new int[2];
				mTextView3.getLocationOnScreen(location);
				bounds[2] = new Rect(location[0], location[1], mTextView3
						.getMeasuredWidth() + location[0], mTextView3
						.getMeasuredHeight() + location[1]);
				Log.d("bounds[2]", bounds[2].left + " " + bounds[2].right + " "
						+ bounds[2].top + " " + bounds[2].bottom + " ");

			}
		});

		ViewTreeObserver vto4 = mTextView4.getViewTreeObserver();
		vto4.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mTextView4.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				int[] location = new int[2];
				mTextView4.getLocationOnScreen(location);
				bounds[3] = new Rect(location[0], location[1], mTextView4
						.getMeasuredWidth() + location[0], mTextView4
						.getMeasuredHeight() + location[1]);
				Log.d("bounds[3]", bounds[3].left + " " + bounds[3].right + " "
						+ bounds[3].top + " " + bounds[3].bottom + " ");

			}
		});

		ViewTreeObserver vto5 = mTextView4.getViewTreeObserver();
		vto5.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mTextView5.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				int[] location = new int[2];
				mTextView5.getLocationOnScreen(location);
				bounds[4] = new Rect(location[0], location[1], mTextView5
						.getMeasuredWidth() + location[0], mTextView5
						.getMeasuredHeight() + location[1]);
				Log.d("bounds[4]", bounds[4].left + " " + bounds[4].right + " "
						+ bounds[4].top + " " + bounds[4].bottom + " ");

			}
		});

		ViewTreeObserver vto6 = mTextView4.getViewTreeObserver();
		vto6.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mTextView6.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				int[] location = new int[2];
				mTextView6.getLocationOnScreen(location);
				bounds[5] = new Rect(location[0], location[1], mTextView6
						.getMeasuredWidth() + location[0], mTextView6
						.getMeasuredHeight() + location[1]);
				Log.d("bounds[5]", bounds[5].left + " " + bounds[5].right + " "
						+ bounds[5].top + " " + bounds[5].bottom + " ");

			}
		});

		ViewTreeObserver vto7 = mTextView4.getViewTreeObserver();
		vto7.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mTextView7.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);

				int[] location = new int[2];
				mTextView7.getLocationOnScreen(location);
				bounds[6] = new Rect(location[0], location[1], mTextView7
						.getMeasuredWidth() + location[0], mTextView7
						.getMeasuredHeight() + location[1]);
				Log.d("bounds[6]", bounds[6].left + " " + bounds[6].right + " "
						+ bounds[6].top + " " + bounds[6].bottom + " ");

			}
		});
	}

}
