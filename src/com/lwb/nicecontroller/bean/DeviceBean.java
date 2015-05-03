package com.lwb.nicecontroller.bean;

/**
 * @author lwb 创建日期:2015-5-3 下午1:58:01
 */
public class DeviceBean {
	private Boolean led;
	private Boolean fan;
	private Boolean safe_mode;
	private Boolean beep;
	private Dht11Bean dht11;

	/**
	 * @return the led
	 */
	public Boolean getLed() {
		return led;
	}

	/**
	 * @param led
	 *            the led to set
	 */
	public void setLed(Boolean led) {
		this.led = led;
	}

	/**
	 * @return the fan
	 */
	public Boolean getFan() {
		return fan;
	}

	/**
	 * @param fan
	 *            the fan to set
	 */
	public void setFan(Boolean fan) {
		this.fan = fan;
	}

	/**
	 * @return the safe_mode
	 */
	public Boolean getSafe_mode() {
		return safe_mode;
	}

	/**
	 * @param safe_mode
	 *            the safe_mode to set
	 */
	public void setSafe_mode(Boolean safe_mode) {
		this.safe_mode = safe_mode;
	}

	/**
	 * @return the beep
	 */
	public Boolean getBeep() {
		return beep;
	}

	/**
	 * @param beep
	 *            the beep to set
	 */
	public void setBeep(Boolean beep) {
		this.beep = beep;
	}

	/**
	 * @return the dht11
	 */
	public Dht11Bean getDht11() {
		return dht11;
	}

	/**
	 * @param dht11
	 *            the dht11 to set
	 */
	public void setDht11(Dht11Bean dht11) {
		this.dht11 = dht11;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	private String picture;
}
