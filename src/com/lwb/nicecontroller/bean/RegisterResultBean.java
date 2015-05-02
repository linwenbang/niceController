package com.lwb.nicecontroller.bean;

/**
 * @author lwb 创建日期:2015-4-9 上午12:38:01
 */
public class RegisterResultBean {
	private String userid;
	private String alias;
	private String msg_type;

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the msg_type
	 */
	public String getMsg_type() {
		return msg_type;
	}

	/**
	 * @param msg_type
	 *            the msg_type to set
	 */
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return msg_type + "\n userid = " + userid + "\n alias = " + alias;
	}
}
