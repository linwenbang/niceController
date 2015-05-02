package com.lwb.nicecontroller.bean;

/**
 * @author linwenbang
 * @date 创建时间：2015-3-25 上午11:32:23
 * @version 1.0 * @parameter
 * @return 湿度实体类
 */
public class Humidity {
	// 当前值
	private int amount;
	// 最高的警戒值
	private int max;
	// 最低的警戒值
	private int min;
	public int getAmount() {
		return amount;
	}
	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}
	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
