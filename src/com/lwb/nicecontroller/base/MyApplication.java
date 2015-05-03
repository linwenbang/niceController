package com.lwb.nicecontroller.base;

import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.lwb.nicecontroller.jpush.ExampleUtil;

public class MyApplication extends Application {

	public static MyApplication mInstance;
	public static int DIALOG_USED = 0;
	public static boolean isAdmin = false;
	private static final String TAG = "JPush";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;

	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	/**
	 * 获取SharedPreferences实例
	 * 
	 * @param name
	 * @return
	 */
	public SharedPreferences getSharedPreferences(String name) {
		SharedPreferences sharedPreferences = this.getSharedPreferences(name,
				MODE_PRIVATE);
		return sharedPreferences;
	}

}
