package com.kince.andevui.activity;

import com.kince.andevui.R;
import com.kince.andevui.fragment.CommentsFragment;
import com.kince.andevui.fragment.DetailFragment;
import com.kince.andevui.util.ActivityStackControlUtil;
import com.kince.andevui.widget.slidingmenu.SlidingMenu;
import com.kince.andevui.widget.swipebacklayout.SwipeBackActivity;
import com.kince.andevui.widget.swipebacklayout.SwipeBackLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
/**
 * @author kince
 * @category 数据详情界面 
 * @version 1.0.0
 * @since 2014.7.3
 */
public class DetailsActivity extends SwipeBackActivity {

	// 滑动切换activity
	private SwipeBackLayout mSwipeBackLayout;
	// 侧滑菜单
	private SlidingMenu sm;
	// 
	private Fragment commentFragmet, detailFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.detail_view_main);
		super.onCreate(savedInstanceState);
		
		Log.i("DetailsActivity", "onCreate");
		
		ActivityStackControlUtil.add(this);
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		commentFragmet = new CommentsFragment(DetailsActivity.this);
		detailFragment = new DetailFragment(DetailsActivity.this);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, detailFragment).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.comments_list_container, commentFragmet).commit();
		initSlidingMenu();
	}

	/**
	 * @category 初始化侧滑菜单
	 * 
	 */
	private void initSlidingMenu() {
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		sm = new SlidingMenu(DetailsActivity.this);
		sm.setMode(SlidingMenu.RIGHT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		sm.setBehindOffset(display.getWidth() * 1 / 4);
		sm.setShadowDrawable(R.drawable.sidebar_shadow_right);
		sm.setShadowWidth(30);
		sm.setMenu(R.layout.layout_menu);
		sm.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
			@Override
			public void onOpened() {
				mSwipeBackLayout.setEnableGesture(false);
			}
		});
		sm.setOnClosedListener(new SlidingMenu.OnClosedListener() {
			@Override
			public void onClosed() {
				mSwipeBackLayout.setEnableGesture(true);
			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
