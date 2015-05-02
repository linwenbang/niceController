/**
 * 北京思耐德科技有限公司深圳分公司 所有
 * 创建日期:2014-9-1 上午10:20:14
 */
package com.lwb.nicecontroller.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.jpush.android.api.JPushInterface;

import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.main.NiceMainActivity;
import com.lwb.nicecontroller.base.BaseActivity;

/**
 * 启动图
 * 
 * @author lwb
 * 
 */
public class SplashActivity extends BaseActivity {

	/** 延迟时间 */
	private final int SPLASH_DISPLAY_LENGHT = 1000;

	// /** 启动判断 */
	// private boolean isFirstStart;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(mContext);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(mContext);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				// SharedPreferences defaultPreferences = getSharedPreferences(
				// SharedPreferencesConstants.SP_KEY_FIRST_INSTALL,
				// MODE_PRIVATE);
				// isFirstStart = defaultPreferences.getBoolean("isFirstStart",
				// true);
				// if (isFirstStart) {
				// // 第一次安装
				// // startActivity(new Intent(SplashActivity.this,
				// // GuideActivity.class));
				// } else {
				// startActivity(new Intent(SplashActivity.this,
				// MainActivity.class));
				// }

				startActivity(new Intent(SplashActivity.this,
						NiceMainActivity.class));

				finish();
			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
