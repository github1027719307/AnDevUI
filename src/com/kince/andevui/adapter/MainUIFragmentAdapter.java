/**
 * 
 */
package com.kince.andevui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author kince
 * 
 *
 */
public class MainUIFragmentAdapter extends FragmentStatePagerAdapter {
	
	private ArrayList<Fragment> fragments;
	private FragmentManager fm;
	/**
	 * @param fm
	 */
	public MainUIFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fm = fm;
	}

	public MainUIFragmentAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.fragments = fragments;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

}
