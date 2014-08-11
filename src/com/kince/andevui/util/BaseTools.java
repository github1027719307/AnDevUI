package com.kince.andevui.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * @author kince
 * @category 常用工具类
 * @since 2014.7.5
 * @version v1.0.0
 */
public class BaseTools {

	public static float density;
	public static float xdpi;
	public static float ydpi;
	public static float screenWidth;
	public static float screenHeight;
	public static float densityDPI;
	public static int screenWidthDip;
	public static int screenHeightDip;

	/** 获取屏幕的宽度 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public void getDisplayDp(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		screenWidthDip = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		screenHeightDip = dm.heightPixels; // 屏幕宽（dip，如：533dip）
		screenWidth = (int) (dm.widthPixels * density + 0.5f); // 屏幕宽（px，如：480px）
		screenHeight = (int) (dm.heightPixels * density + 0.5f); // 屏幕高（px，如：800px）
	};

	/**
	 * dip转为 px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px 转为 dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	private static long lastClickTime;

	/**
	 * 解决的思路如下：
	 * 
	 * 1. 需要定义一个全局变量 lastClickTime, 用来记录最后点击的时间.
	 * 
	 * 2. 每次点击前需要进行判断, 用lastClickTime 和当前时间想比较，并且更新最后点击时间，若小于临界值，则算无效点击，不触发事件
	 * 
	 * if (Utils.isFastDoubleClick()) { return; }else{ //弹出Toast或者Dialog }
	 * 
	 * 这样,两次点击时间相隔小于800ms,便不会触发事件，具体的临界时间可以根据需求自己修改.
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
	
	/**
	 * @category 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context){
		int statusBarHeight = 0;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object o = clazz.newInstance();
			Field field = clazz.getField("status_bar_height");
			int x = (Integer) field.get(o);
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			Log.i("statusBarHeight", statusBarHeight + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

}
