package com.kince.andevui.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.kince.andevui.AndApplication;
import com.kince.andevui.R;
import com.kince.andevui.db.DBManager;
import com.kince.andevui.db.SQLHelper;
import com.kince.andevui.entity.User;
import com.kince.andevui.fragment.AboutFragment;
import com.kince.andevui.fragment.AppstoreFragment;
import com.kince.andevui.fragment.CollectFragment;
import com.kince.andevui.fragment.MainUiFragment;
import com.kince.andevui.fragment.RecommendFragment;
import com.kince.andevui.menu.FoldingDrawerLayout;
import com.kince.andevui.menu.OnFoldListener;
import com.kince.andevui.util.ActivityStackControlUtil;
import com.kince.andevui.util.ActionSheet.OnActionSheetSelected;
import com.kince.andevui.widget.DynamicWeatherCloudyView;
import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennResponse;
import com.renn.rennsdk.RennExecutor.CallBack;
import com.renn.rennsdk.exception.RennException;
import com.renn.rennsdk.param.GetLoginUserParam;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author kince
 * @category 主界面 侧滑 viewpager实现内容切换 仿网易的动画效果
 * @since 2014.5.4 2014.5.27 2014.7.1
 * @version v1.0.0
 */
public class MainActivity extends BaseActivity implements OnClickListener,
		OnFoldListener, OnActionSheetSelected, OnCancelListener {

	static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB;
	// 菜单折叠控件
	private FoldingDrawerLayout mDrawerLayout;
	// 标题
	private CharSequence mDrawerTitle;
	//
	private CharSequence mTitle;
	//
	private String[] mUiTitles;
	// toggle
	private ActionBarDrawerToggle mDrawerToggle;
	// 左侧菜单
	private View left_menu;
	// 点击菜单位置
	private int menu_position;
	// 各个菜单项
	private RelativeLayout anddevui_btn, favorite_btn, offline_btn_btn,
			setting_btn, about_btn, appstore_btn;
	// 屏幕dpi
	public static float density;
	public static float xdpi;
	public static float ydpi;
	// 屏幕宽带
	public static float screenWidth;
	// 屏幕高度
	public static float screenHeight;
	public static float densityDPI;
	public static int screenWidthDip;
	public static int screenHeightDip;
	//
	private long waitTime = 2000;
	private long touchTime = 0;
	//
	private Fragment mContent = null;
	private Fragment fragment = null;
	// 折叠菜单
	private FrameLayout flLayout;
	// 用户
	private ImageButton qq_btn, weibo_btn, renren_btn;

	private ImageView iv_user_photo;
	private TextView tv_user_commond, tv_username;
	/** 人人 */
	private RennClient rennClient;

	private LinearLayout layout_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		initView();
		getDisplayDp();

		if (savedInstanceState == null) {
			fragment = MainUiFragment.getInstance();
			mContent = fragment;
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
		}
	}

	/**
	 * @category 初始化view
	 */
	private void initView() {

		flLayout = (FrameLayout) findViewById(R.id.layout_drawer_list);
		layout_login = (LinearLayout) findViewById(R.id.login_layout);
		iv_user_photo = (ImageView) findViewById(R.id.avatar);
		qq_btn = (ImageButton) findViewById(R.id.qq_btn);
		weibo_btn = (ImageButton) findViewById(R.id.weibo_btn);
		renren_btn = (ImageButton) findViewById(R.id.renren_btn);
		tv_user_commond = (TextView) findViewById(R.id.tv_user_commond);
		tv_username = (TextView) findViewById(R.id.user_name);

		if (AndApplication.isLogin) {
			// 提示登陆按钮设置不可见
			qq_btn.setVisibility(View.GONE);
			weibo_btn.setVisibility(View.GONE);
			renren_btn.setVisibility(View.GONE);
			layout_login.setVisibility(View.VISIBLE);
			// 显示用户头像
			tv_user_commond.setVisibility(View.GONE);
		}

		mDrawerLayout = (FoldingDrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		left_menu = findViewById(R.id.left_menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {

			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				if (IS_HONEYCOMB) {
					invalidateOptionsMenu(); // creates call to
					// onPrepareOptionsMenu()
				}

			}

			public void onDrawerSlide(View drawerView, float slideOffset) {

			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				if (IS_HONEYCOMB) {
					invalidateOptionsMenu(); // creates call to
					// onPrepareOptionsMenu()

				}

			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		setWeather();

		anddevui_btn = (RelativeLayout) this.findViewById(R.id.anddevui_btn);
		anddevui_btn.setOnClickListener(this);
		favorite_btn = (RelativeLayout) this.findViewById(R.id.favorite_btn);
		favorite_btn.setOnClickListener(this);
		offline_btn_btn = (RelativeLayout) this.findViewById(R.id.offline_btn);
		offline_btn_btn.setOnClickListener(this);
		setting_btn = (RelativeLayout) this.findViewById(R.id.setting_btn);
		setting_btn.setOnClickListener(this);
		about_btn = (RelativeLayout) this.findViewById(R.id.about_btn);
		about_btn.setOnClickListener(this);
		appstore_btn = (RelativeLayout) this.findViewById(R.id.appstore_btn);
		appstore_btn.setOnClickListener(this);

	}

	/**
	 * @category 设置天气背景
	 * 
	 */
	private void setWeather() {
		flLayout.setBackgroundResource(R.drawable.yjjc_h_a1);
		DynamicWeatherCloudyView view1 = new DynamicWeatherCloudyView(this,
				R.drawable.yjjc_h_a2, -150, 40, 20);
		DynamicWeatherCloudyView view4 = new DynamicWeatherCloudyView(this,
				R.drawable.yjjc_h_a5, 0, 60, 30);
		DynamicWeatherCloudyView view2 = new DynamicWeatherCloudyView(this,
				R.drawable.yjjc_h_a3, 280, 80, 50);
		DynamicWeatherCloudyView view3 = new DynamicWeatherCloudyView(this,
				R.drawable.yjjc_h_a4, 140, 130, 40);

		flLayout.addView(view1);
		flLayout.addView(view2);
		flLayout.addView(view3);
		flLayout.addView(view4);

		view1.move();
		view2.move();
		view3.move();
		view4.move();

	}

	/**
	 * @category 初始化数据
	 * 
	 */
	private void initData() {
		final DBManager dbManager = DBManager.getInstance(this);

		mUiTitles = getResources().getStringArray(R.array.ui_array);
		mTitle = mDrawerTitle = getTitle();
		rennClient = RennClient.getInstance(this);// 这句一定要加上 否则虽然之前已经有实例
		GetLoginUserParam param = new GetLoginUserParam();
		try {
			rennClient.getRennService().sendAsynRequest(param, new CallBack() {
				@Override
				public void onSuccess(RennResponse response) {
					JSONObject resultObject = null;
					try {
						resultObject = response.getResponseObject();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Gson gson = new Gson();
					// 获取一级json的方法
					User user = gson.fromJson(resultObject.toString(),User.class);
					String name = user.getName();
					int id = user.getId();
					tv_username.setText(name);

					ContentValues values = new ContentValues();
					values.put("name", name);
					values.put("id", id);
					dbManager.insertData(SQLHelper.TABLE_User, values);
				}

				@Override
				public void onFailed(String errorCode, String errorMessage) {

					Toast.makeText(MainActivity.this, "获取失败：" + errorMessage,
							Toast.LENGTH_SHORT).show();
				}
			});
		} catch (RennException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * @category 获取屏幕分辨率
	 * 
	 */
	private void getDisplayDp() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		screenWidthDip = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		screenHeightDip = dm.heightPixels; // 屏幕宽（dip，如：533dip）
		screenWidth = (int) (dm.widthPixels * density + 0.5f); // 屏幕宽（px，如：480px）
		screenHeight = (int) (dm.heightPixels * density + 0.5f); // 屏幕高（px，如：800px）
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Called whenever we call invalidateOptionsMenu()
	 * 
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * @category 菜单条目点击 切换不同的界面 因为各个一级Fragment是静态的，所以直接使用单例模式
	 * @param position
	 */
	private void selectItem(int position) {
		// update the main content by replacing fragments

		switch (position) {
		// 主页
		case 0:
			fragment = MainUiFragment.getInstance();
			if (fragment != mContent) {
				switchContent(mContent, fragment);
			}
			break;
		// 主页
		case 1:
			fragment = new CollectFragment();
			if (fragment != mContent) {
				switchContent(mContent, fragment);
			}
			break;
		// 主页
		case 2:
			fragment = new RecommendFragment();
			if (fragment != mContent) {
				switchContent(mContent, fragment);
			}
			break;
		// 主页
		case 4:
			fragment = new AboutFragment();
			if (fragment != mContent) {
				switchContent(mContent, fragment);
			}
			break;
		// 主页
		case 5:
			fragment = new AppstoreFragment();
			if (fragment != mContent) {
				switchContent(mContent, fragment);
			}
			break;

		default:
			break;
		}
		// update selected item and title, then close the drawer
		setTitle(mUiTitles[position]);
		mDrawerLayout.closeDrawer(left_menu);
	}

	/**
	 * @category 采用隐藏显示的方式 解决切换fragment空白的问题
	 * @param from
	 * @param to
	 */
	public void switchContent(Fragment from, Fragment to) {
		if (mContent != to) {
			mContent = to;
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction().setCustomAnimations(
							android.R.anim.fade_in, R.anim.abc_fade_out);
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.anddevui_btn:
			menu_position = 0;
			selectItem(menu_position);

			break;
		case R.id.favorite_btn:
			menu_position = 1;
			selectItem(menu_position);

			break;
		case R.id.logout_layout:
			// 离线下载 暂未实现
			// menu_position = 2;
			// selectItem(menu_position);

			break;
		case R.id.setting_btn:
			startActivity(new Intent(this, SettingActivity.class));
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			break;
		case R.id.about_btn:
			menu_position = 4;
			selectItem(menu_position);

			break;
		case R.id.appstore_btn:
			menu_position = 5;
			selectItem(menu_position);

			break;

		default:
			break;
		}
	}

	@Override
	public void onStartFold() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndFold() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		long currentTime = System.currentTimeMillis();
		if (mDrawerLayout.isDrawerOpen(left_menu)) {
			mDrawerLayout.closeDrawer(left_menu);
		} else {
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				// 退出整个程序
				ActivityStackControlUtil.finishProgram();
			}
		}

	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(int whichButton) {
		// TODO Auto-generated method stub
		switch (whichButton) {
		case 0:
			// 退出登录

			break;

		case 1:

			break;

		default:
			break;
		}
	}

}
