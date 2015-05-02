package com.lwb.nicecontroller.bean;
/**
 * @author linwenbang
 * @date 创建时间：2015-3-25 上午11:32:23
 * @version 1.0 * @parameter
 * @return 灯控实体类
 */
public class Led {
	//亮度
	private int brightness;
	
	//开关
	private boolean switchLed;

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public boolean isSwitchLed() {
		return switchLed;
	}

	public void setSwitchLed(boolean switchLed) {
		this.switchLed = switchLed;
	}
	

}
