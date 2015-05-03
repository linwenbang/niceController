package com.lwb.nicecontroller.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.lwb.nicecontroller.base.MyApplication;
import com.lwb.nicecontroller.contants.UrlContants;

/**
 * SharedPreference本地存储工具类
 * 
 * @author csf
 * 
 */
public class SharedPreferenceUtils {

	public static SharedPreferenceUtils INSTANCE;

	private SharedPreferences preferences;

	private static MyApplication myApp = (MyApplication) MyApplication
			.getInstance();

	public static synchronized SharedPreferenceUtils getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new SharedPreferenceUtils();
			INSTANCE.preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
		}
		return INSTANCE;
	}

	/**
	 * SharedPreference本地存储boolean类型
	 * 
	 * @param spName
	 *            SharedPreference文件名
	 * @param key
	 *            存储key
	 * @param value
	 *            存储值
	 */
	public static void setBoolean(String spName, String key, boolean value) {
		SharedPreferences sp = myApp.getSharedPreferences(spName);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获取SharedPreference本地存储的boolean类型
	 * 
	 * @param spName
	 *            SharedPreference文件名
	 * @param key
	 *            存储key
	 */
	public static boolean getBoolean(String spName, String key) {
		SharedPreferences sp = myApp.getSharedPreferences(spName);
		return sp.getBoolean(key, false);
	}

	/**
	 * 获取SharedPreference本地存储的boolean类型
	 * 
	 * @param spName
	 *            SharedPreference文件名
	 * @param key
	 *            存储key
	 */
	public static String getString(String spName, String key) {
		SharedPreferences sp = myApp.getSharedPreferences(spName);
		return sp.getString(key, UrlContants.HEAD_URL);
	}

	/**
	 * 得到指定SharedPreference的编辑器
	 * 
	 * @param spName
	 *            SharedPreference文件名
	 * @return
	 */
	public static Editor getEditor(String spName) {
		SharedPreferences sp = myApp.getSharedPreferences(spName);
		Editor editor = sp.edit();
		return editor;
	}
}
