package com.lwb.nicecontroller.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络情况工具类
 * 
 * @author csf
 * 
 */
public class NetworkInfoUtils {

	private ConnectivityManager connectMgr;
	private NetworkInfo info;

	public NetworkInfoUtils(Context context) {
		connectMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		info = connectMgr.getActiveNetworkInfo();
	}

	/**
	 * 获取网络类型，-1表示没网络，0表示移动数据，1表示wifi
	 * 
	 * @return
	 */
	public int getNetType() {
		int netType = -1;
		if (info == null) {
			return netType;
		}
		return info.getType();
	}

}
