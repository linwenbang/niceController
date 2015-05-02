package com.lwb.nicecontroller.utils;

import java.util.List;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * json解析类
 * 
 * @author lwb
 * 
 */
public class FastjsonUtils {

	/**
	 * 解析获取实体对象
	 * 
	 * @param json
	 * @param c
	 *            实体类
	 * @return
	 */
	public static <T> Object getBeanObject(String json, Class<T> c) {

		try {
			return JSON.parseObject(json, c);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("解析异常：" + e);
		}
		return null;

	}

	/**
	 * 解析获取实体对象List
	 * 
	 * @param json
	 * @param c
	 *            实体类
	 * @return
	 */
	public static <T> List<T> getBeanList(String json, Class<T> c) {
		try {
			return JSON.parseArray(json, c);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("解析异常：" + e);
		}
		return null;
	}

	/**
	 * 嵌套json字符串解析 将一个json解析成多个json,或者值
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getJsonString(String json, String key) {
		return JSON.parseObject(json).getString(key);
	}

	/**
	 * json字符串解析 将一个json解析成JSONObject,根据key获取Integer
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer getJsonInteger(String json, String key) {
		return JSON.parseObject(json).getInteger(key);
	}

	/**
	 * 获得code ,分析得到返回数据的有效性，0表示成功，其它表示操作失败,-1表示请求出错
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer getCode(String json) {
		if (TextUtils.isEmpty(json)) {
			return -1;
		}
		int code = -1;
		try {
			code = getJsonInteger(json, "code");
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("解析异常：" + e);
			return code;
		}
		return code;
	}

	/**
	 * 从请求返回的数据中得到具体的json字符串
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getDto(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		String dto = null;
		if (getCode(json) == 200) {
			try {
				dto = getJsonString(json, "dto");
			} catch (Exception e) {
				// TODO: handle exception
				LogUtils.e("解析异常：" + e);
				return null;
			}
		}
		return dto;
	}

	/**
	 * 错误码描述信息 当code不为0，才需要返回,描述了一些错误的信息提示
	 * 
	 * @param json
	 * @return
	 */
	public static String getSummary(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		if (getCode(json) != 0) {
			try {
				return getJsonString(json, "summary");
			} catch (Exception e) {
				// TODO: handle exception
				LogUtils.e("解析异常：" + e);
			}
			return null;
		}
		return null;
	}

	/**
	 * 将JavaBean序列化为JSON文本
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		if (object == null) {
			return null;
		}
		String json = JSON.toJSONString(object);
		return json;
	}

	/**
	 * 将json解析成jsonArray
	 * 
	 * @param object
	 * @return
	 */
	public static JSONArray praseJsonArray(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		try {
			JSONArray jsonArray = JSON.parseArray(json);
			return jsonArray;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("解析异常：" + e);
			return null;
		}

	}

}
