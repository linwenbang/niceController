package com.lwb.nicecontroller.utils;

import java.util.Map;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author linwenbang
 * @date 创建时间：2015-4-3 下午9:20:14
 * @version 1.0
 */
public class HttpUtils {

	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(11000); // 设置链接超时，如果不设置，默认为10s
	}
	
	public static void logUrl(String urlString) {
		LogUtils.e("请求地址：" + urlString);
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.get(urlString, res);
		logUrl(urlString);
	}

	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) // url里面带参数
	{
		client.get(urlString, params, res);
		logUrl(urlString);
	}

	public static void get(String urlString, JsonHttpResponseHandler res) // 不带参数，获取json对象或者数组
	{
		client.get(urlString, res);
		logUrl(urlString);
	}

	public static void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) // 带参数，获取json对象或者数组
	{
		client.get(urlString, params, res);
		logUrl(urlString);
	}
	
	
	public static void post(String urlString, AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.post(urlString, res);
		logUrl(urlString);
	}
	
	
	public static void post(String urlString, Map<String , String > body ,AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		RequestParams requestParams =  new RequestParams(body);
		client.post(urlString, requestParams, res);
		logUrl(urlString);
	}
	
	public static void post(Context context ,String urlString, RequestParams requestParams,AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.post(context, urlString, requestParams, res);
		logUrl(urlString);
	}

	public static void post(Context context ,String urlString, HttpEntity httpEntity, String str2,AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.post(context, urlString, httpEntity, str2, res);
		logUrl(urlString);
	}
	
	public static void put(String urlString,AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.put(urlString, res);
		logUrl(urlString);
	}
	
	public static void put(String urlString,  Map<String , String > body, AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		RequestParams requestParams =  new RequestParams(body);
		client.put(urlString, requestParams, res);
		logUrl(urlString);
	}

	public static void put(Context context ,String urlString, RequestParams requestParams,AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.put(context, urlString, requestParams, res);
		logUrl(urlString);
	}
	
	public static void delete(String urlString,AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.delete(urlString, res);
		logUrl(urlString);
	}

	
	public static void delete(Context context,String urlString,AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{
		client.delete(context, urlString, res);
		logUrl(urlString);
	}
	
	public static AsyncHttpClient getClient() {
		return client;
	}

}
