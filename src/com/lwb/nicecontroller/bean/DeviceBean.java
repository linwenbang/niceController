package com.lwb.nicecontroller.bean;

/**
 * @author lwb 创建日期:2015-5-3 下午1:58:01
 */
public class DeviceBean {
	private Boolean led;
	private Boolean fan;
	private Boolean safe_mode;
	private Boolean beep;
	private Boolean led_auto;
	private Boolean pir;
	private Boolean sound;
	private Boolean motor;
	private float temp;
	private float wet;
	private float cpu_temp;
	private float gpu_temp;

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(float temp) {
		this.temp = temp;
	}

	/**
	 * @param wet the wet to set
	 */
	public void setWet(float wet) {
		this.wet = wet;
	}

	/**
	 * @param cpu_temp the cpu_temp to set
	 */
	public void setCpu_temp(float cpu_temp) {
		this.cpu_temp = cpu_temp;
	}

	/**
	 * @param gpu_temp the gpu_temp to set
	 */
	public void setGpu_temp(float gpu_temp) {
		this.gpu_temp = gpu_temp;
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
	public float getTemp() {
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
	public float getWet() {
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
	public float getCpu_temp() {
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
	public float getGpu_temp() {
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

	/**
	 * @return the motor
	 */
	public Boolean getMotor() {
		return motor;
	}

	/**
	 * @param motor the motor to set
	 */
	public void setMotor(Boolean motor) {
		this.motor = motor;
	}

	
	/**
	 * @return the led_auto
	 */
	public Boolean getLed_auto() {
		return led_auto;
	}

	/**
	 * @param led_auto the led_auto to set
	 */
	public void setLed_auto(Boolean led_auto) {
		this.led_auto = led_auto;
	}

	private String picture;
}
