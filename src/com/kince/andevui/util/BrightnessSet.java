package com.kince.andevui.util;

import com.kince.andevui.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class BrightnessSet {

	private static BrightnessSet mBrightnessSet;
	private static Context mContext;

	private BrightnessSet(Context context) {
		init(context);
	};

	public static BrightnessSet getInstance() {
		if (mBrightnessSet == null) {
			mBrightnessSet = new BrightnessSet(mContext);
		}
		return mBrightnessSet;
	}

	public void setScreenBrightness(int brightness) {
		if (brightness < 0 || brightness > 255) {
			// 自动调节亮度 ,先设置为150

//			SystemSettings.startAutoBrightness(mContext);// 设置亮度为自动
//			Toast.makeText(mContext,
//					R.string.system_settings_screenbrightness_auto,
//					Toast.LENGTH_SHORT).show();
		} else {
//			SystemSettings.stopAutoBrightness(mContext);

			setBrightness(brightness);
//			SystemSettings.saveBrightness(mContext, brightness);// 保存亮度，自定义的方法
			removeFloatView();
		}
	}

	/**
	 * 初始化悬浮窗
	 */
	private void init(Context context) {
		mContext=context;// Application中的一个静态变量，也可以通过初始化传入context
		floatView = new TextView(context.getApplicationContext());
		windowManager = (WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		params = new LayoutParams();
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE
				| LayoutParams.FLAG_NOT_TOUCHABLE;

		params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

		params.width = 0;
		params.height = 0;
	}

	private TextView floatView;
	private WindowManager windowManager;
	private LayoutParams params;

	/**
	 * 创建悬浮窗,通过悬浮窗设置亮度
	 */
	private void setBrightness(int brightness) {
		params.screenBrightness = brightness / 255;
		windowManager.addView(floatView, params);
	}

	private void removeFloatView() {
		windowManager.removeView(floatView);
	}


	
}
