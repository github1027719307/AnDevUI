package com.kince.andevui.util;

import com.kince.andevui.R;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class PopWindowUtil {

	public PopupWindow mPopupWindow;

	/**
	 * 初始化弹出的pop
	 * 
	 */
	public void initPopWindow(View popView) {
		// View popView = inflater.inflate(R.layout.list_item_pop, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
		// 设置popwindow出现和消失动画
		mPopupWindow.setAnimationStyle(R.style.PopMenuAnimation);
		// btn_pop_close = (ImageView) popView.findViewById(R.id.btn_pop_close);
	}

	/**
	 * 显示popWindow
	 * 
	 */
	public void showPop(View parent, int x, int y, int postion) {
		// 设置popwindow显示位置
		mPopupWindow.showAtLocation(parent, 0, x, y);
		// 获取popwindow焦点
		mPopupWindow.setFocusable(true);
		// 设置popwindow如果点击外面区域，便关闭。
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.update();
		if (mPopupWindow.isShowing()) {

		}
		// btn_pop_close.setOnClickListener(new OnClickListener() {
		// public void onClick(View paramView) {
		// mPopupWindow.dismiss();
		// }
		// });
	}

	/**
	 * 每个ITEM中more按钮对应的点击动作
	 * 
	 */
	public class popAction implements OnClickListener {
		int position;

		public popAction(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			int[] arrayOfInt = new int[2];
			// 获取点击按钮的坐标
			v.getLocationOnScreen(arrayOfInt);
			int x = arrayOfInt[0];
			int y = arrayOfInt[1];
			showPop(v, x, y, position);
		}
	}
	
}
