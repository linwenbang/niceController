package com.lwb.nicecontroller.app.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.device.DeviceStatusFragment;
import com.lwb.nicecontroller.app.user.fragment.SettingFragment;
import com.lwb.nicecontroller.base.BaseFragmentActivity;
import com.lwb.nicecontroller.contants.SharedPreferencesConstants;
import com.lwb.nicecontroller.contants.UrlContants;

public class NiceMainActivity extends BaseFragmentActivity {
	private List<Fragment> mTabContents = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private ViewPager mViewPager;
	private List<String> mDatas = Arrays.asList("设备状态展示", "设置");
	private ViewPagerIndicator mIndicator;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vp_indicator);

		initView();
		initDatas();
	}
	

	private void initDatas() {
		//初始化head_url
		String head;
		sp = getSharedPreferences(UrlContants.HEAD_URL_SP, 0);
		head = sp.getString(SharedPreferencesConstants.HEAD_URL_KEY, UrlContants.HEAD_URL);
		if (head != null && !TextUtils.isEmpty(head)) {
			UrlContants.HEAD_URL = head;
		} 
		
		
		// 添加fragment
		mTabContents.add(new DeviceStatusFragment());
		mTabContents.add(new SettingFragment());

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTabContents.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mTabContents.get(position);
			}
		};

		// 设置Tab上的标题
		mIndicator.setTabItemTitles(mDatas);
		mViewPager.setAdapter(mAdapter);
		// 设置关联的ViewPager
		mIndicator.setViewPager(mViewPager, 0);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_vp);
		mViewPager.setOffscreenPageLimit(5);
		mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
	}

}
