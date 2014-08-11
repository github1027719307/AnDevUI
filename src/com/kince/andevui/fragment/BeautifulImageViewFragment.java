package com.kince.andevui.fragment;

import com.kince.andevui.AndApplication;
import com.kince.andevui.Constants;
import com.kince.andevui.R;
import com.kince.andevui.adapter.MyImageViewpagerAdapter;
import com.kince.andevui.animation.DepthPageTransformer;
import com.kince.andevui.entity.CodeClass;
import com.kince.andevui.widget.MyViewpager;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class BeautifulImageViewFragment extends Fragment {
	private Activity a;
	private CodeClass[] list;
	private View rootView;
	private MyViewpager viewPager;
	private MyImageViewpagerAdapter mAdpater;
	private TextView textView1, textView;
	private int count;
	private int position;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public BeautifulImageViewFragment(Activity a) {
		this.a = a;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = a.getIntent().getExtras();
		String string = bundle.getString("Key");
		position = bundle.getInt("position");
		if (string != null) {
			if (string.equals("xiuxian")) {
				list = Constants.JXX_IMAGES_CODECLASS;
			}
		}
		count = list != null ? list.length : 0;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.viewpager_imageview, container,
				false);
		initTextView(rootView);
		initViewpager(rootView);

		return rootView;
	}

	private void initTextView(View view) {
		textView = (TextView) view.findViewById(R.id.textView);
		textView1 = (TextView) view.findViewById(R.id.textView1);
	}

	private void initViewpager(View view) {
		mAdpater = new MyImageViewpagerAdapter(getFragmentManager(), a, list,
				imageLoader);
		viewPager = (MyViewpager) view.findViewById(R.id.vp);
		viewPager.setAdapter(mAdpater);

		viewPager.setOffscreenPageLimit(3);
		viewPager.setCurrentItem(position, true);
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		viewPager.setPageTransformer(true, new DepthPageTransformer());

		textView1.setText(position + 1 + "");
		textView1.setTypeface(AndApplication.englishTypeface);
		textView.setText("/" + count);
		textView.setTypeface(AndApplication.englishTypeface);

	}

	private class MyPageChangeListener implements OnPageChangeListener {

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			textView1.setText(position + 1 + "");
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

}
