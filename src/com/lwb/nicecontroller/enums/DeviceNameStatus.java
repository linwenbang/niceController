package com.lwb.nicecontroller.enums;

/**
 * @author lwb
 * 创建日期:2015-5-3 下午1:41:10
 */
public enum DeviceNameStatus  {
	/** 所有设备 */
	all("all"),
	/** led灯 */
	led("led"),
	/** 风扇 */
	fan("fan"),
	/** 蜂鸣器 */
	beep("beep"),
	/** 温湿度 */
	dht11("dht11"),
	/** 安防模式 */
	safe_mode("safe_mode"),
	/** 摄像头照片*/
	picture("picture"),
	/** 红外*/
	pir("pir"),
	/** 设备声音*/
	sound("sound"),
	/** 自动灯*/
	auto_led("auto_led");

	public final String name;

	/**
	 * @param chinaname
	 */
	private DeviceNameStatus(String name) {
		this.name = name;
	}

}
