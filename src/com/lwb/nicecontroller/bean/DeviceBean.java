package com.lwb.nicecontroller.bean;

/**
 * @author lwb 创建日期:2015-5-3 下午1:58:01
 */
public class DeviceBean {
	private Boolean led;
	private Boolean fan;
	private Boolean safe_mode;
	private Boolean beep;
	private Boolean auto_led;
	private Boolean pir;
	private Boolean sound;
	private int temp;
	private int wet;
	private int cpu_temp;
	private int gpu_temp;


	/**
	 * @return the auto_led
	 */
	public Boolean getAuto_led() {
		return auto_led;
	}

	/**
	 * @param auto_led the auto_led to set
	 */
	public void setAuto_led(Boolean auto_led) {
		this.auto_led = auto_led;
	}

	/**
	 * @return the pir
	 */
	public Boolean getPir() {
		return pir;
	}

	/**
	 * @param pir the pir to set
	 */
	public void setPir(Boolean pir) {
		this.pir = pir;
	}

	/**
	 * @return the sound
	 */
	public Boolean getSound() {
		return sound;
	}

	/**
	 * @param sound the sound to set
	 */
	public void setSound(Boolean sound) {
		this.sound = sound;
	}

	/**
	 * @return the temp
	 */
	public int getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(int temp) {
		this.temp = temp;
	}

	/**
	 * @return the wet
	 */
	public int getWet() {
		return wet;
	}

	/**
	 * @param wet the wet to set
	 */
	public void setWet(int wet) {
		this.wet = wet;
	}

	/**
	 * @return the cpu_temp
	 */
	public int getCpu_temp() {
		return cpu_temp;
	}

	/**
	 * @param cpu_temp the cpu_temp to set
	 */
	public void setCpu_temp(int cpu_temp) {
		this.cpu_temp = cpu_temp;
	}

	/**
	 * @return the gpu_temp
	 */
	public int getGpu_temp() {
		return gpu_temp;
	}

	/**
	 * @param gpu_temp the gpu_temp to set
	 */
	public void setGpu_temp(int gpu_temp) {
		this.gpu_temp = gpu_temp;
	}

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
