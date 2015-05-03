package com.lwb.nicecontroller.enums;

/**
 * @author lwb
 * 创建日期:2015-5-3 下午1:41:10
 */
public enum DeviceActionStatus  {
	/** 打开 */
	open("open"),
	/** 关闭 */
	close("close"),
	/** 查询状态 */
	status("status");

	public final String action;

	/**
	 * @param chinaname
	 */
	private DeviceActionStatus(String action) {
		this.action = action;
	}

}
