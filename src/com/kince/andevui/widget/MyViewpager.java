package com.kince.andevui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewpager extends ViewPager {

	public static boolean notflag = false;
	public static boolean multiTouchflag = false;

	public MyViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// xDistance = yDistance = 0f;
			// xLast = ev.getX();
			// yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			// final float curX = ev.getX();
			// final float curY = ev.getY();

			// xDistance += Math.abs(curX - xLast);
			// yDistance += Math.abs(curY - yLast);
			// xLast = curX;
			// yLast = curY;
			//
			// if (xDistance > yDistance) {
			// return false;
			// }
			if (notflag || multiTouchflag) {
				return false;
			}
			break;

		}

		return super.onInterceptTouchEvent(ev);
	}

}
