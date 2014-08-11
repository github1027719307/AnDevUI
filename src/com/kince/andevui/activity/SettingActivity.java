/**
 * 
 */
package com.kince.andevui.activity;

import com.kince.andevui.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author kince
 * @category 设置界面
 * @since 2014.7.3 2014.7.14
 * @version v1.0.0
 * 
 */
public class SettingActivity extends FragmentActivity implements
		OnClickListener {

	private TextView title;
	private	TextView tv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		initData();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setClickable(true);
		tv_back.setOnClickListener(this);
	}

	private void initData() {
		title.setText("设置");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_back:
			// 返回 暂且使用 以后可以换为swipelayout
			onBackPressed();
			break;
		default:
			break;
		}
	}

}
