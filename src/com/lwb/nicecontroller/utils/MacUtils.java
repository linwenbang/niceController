package com.lwb.nicecontroller.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/**
 * @author lwb 创建日期:2015-4-8 上午10:46:22
 */
public class MacUtils {
	/**
	 * 获得mac
	 */
	public static String getMac(Context mContext) {
		// TODO Auto-generated method stub
		WifiManager wifi = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifi.getConnectionInfo();

		if (info.getMacAddress() != null) {
			return info.getMacAddress().replaceAll(":", "");
		} else {
			return "00000000";
		}

	}

	// 尝试打开wifi
	private static boolean tryOpenMAC(WifiManager manager) {
		boolean softOpenWifi = false;
		int state = manager.getWifiState();
		if (state != WifiManager.WIFI_STATE_ENABLED
				&& state != WifiManager.WIFI_STATE_ENABLING) {
			manager.setWifiEnabled(true);
			softOpenWifi = true;
		}
		return softOpenWifi;
	}

	// 尝试关闭MAC
	private static void tryCloseMAC(WifiManager manager) {
		manager.setWifiEnabled(false);
	}

	// 尝试获取MAC地址
	private static String tryGetMAC(WifiManager manager) {
		WifiInfo wifiInfo = manager.getConnectionInfo();
		if (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getMacAddress())) {
			return null;
		}
		String mac = wifiInfo.getMacAddress().replaceAll(":", "").trim()
				.toLowerCase();
		return mac;
	}

	// 尝试读取MAC地址
	private static String getMacFromDevice(int internal, Context mContext) {
		String mac = null;
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		mac = tryGetMAC(wifiManager);
		if (!TextUtils.isEmpty(mac)) {
			return mac;
		}

		// 获取失败，尝试打开wifi获取
		boolean isOkWifi = tryOpenMAC(wifiManager);
		for (int index = 0; index < internal; index++) {
			// 如果第一次没有成功，第二次做100毫秒的延迟。
			if (index != 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mac = tryGetMAC(wifiManager);
			if (!TextUtils.isEmpty(mac)) {
				break;
			}
		}

		// 尝试关闭wifi
		if (isOkWifi) {
			tryCloseMAC(wifiManager);
		}
		return mac;
	}

}
