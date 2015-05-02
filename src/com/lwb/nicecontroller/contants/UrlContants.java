package com.lwb.nicecontroller.contants;

import java.util.Map;

import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.StringUtils;

/**
 * @author lwb 创建日期:2015-4-8 上午10:14:20
 */
public class UrlContants {

//	public static final String HEAD_URL_DEFAULT = "http://test.tunnel.mobi/";
	public static final String HEAD_URL_DEFAULT = "http://smarthome523000.sinaapp.com/";
	public static String HEAD_URL = "http://smarthome523000.sinaapp.com/";
	// public static String HEAD_URL = "http://test.tunnel.mobi/";

	public static final String HEAD_URL_SP = "com.lwb.head.url";

	// 用户注册
	public static String REGISTER_URL;

	// 管理员登录
	public static String ADMIN_LOGIN_URL;

	// 管理员退出
	public static String ADMIN_SIGN_OUT_URL;

	// 管理员审核用户申请
	public String ADMIN_VERIFY_URL;

	// 管理员修改密码
	public static String MODIFY_PWD_URL;

	// 获取注册用户列表
	public static String GET_USERS_URL;

	// 删除注册用户
	public static String DEL_USERS_URL;

	// 获取设备状态
	public static String GET_DEVICE_STATUS_URL;

	/**
	 * @return the hEAD_URL
	 */
	public static String getHEAD_URL() {
		return HEAD_URL;
	}

	/**
	 * @return the rEGISTER_URL
	 */
	public static String getREGISTER_URL() {
		return HEAD_URL + "api/v2.0/regist/{userid}";
	}

	/**
	 * @return the aDMIN_LOGIN_URL
	 */
	public static String getADMIN_LOGIN_URL() {
		return HEAD_URL + "api/v2.0/login/{userid}";
	}

	/**
	 * @return the aDMIN_SIGN_OUT_URL
	 */
	public static String getADMIN_SIGN_OUT_URL() {
		return HEAD_URL + "api/v2.0/logout/{userid}";
	}

	/**
	 * @return the aDMIN_VERIFY_URL
	 */
	public static String getADMIN_VERIFY_URL() {
		return HEAD_URL + "api/v2.0/regist/{userid}";
	}

	/**
	 * @return the mODIFY_PWD_URL
	 */
	public static String getMODIFY_PWD_URL() {
		return HEAD_URL + "api/v2.0/changepassword/{userid}";
	}

	/**
	 * @return the gET_USERS_URL
	 */
	public static String getGET_USERS_URL() {
		return HEAD_URL + "api/v2.0/regist/{userid}";
	}

	/**
	 * @return the dEL_USERS_URL
	 */
	public static String getDEL_USERS_URL() {
//		return HEAD_URL + "api/v2.0/regist/{userid}/{deluserid}";
		return HEAD_URL + "api/v2.0/regist/{userid}&{deluserid}";
	}

	/**
	 * @return the gET_DEVICE_STATUS_URL
	 */
	public static String getGET_DEVICE_STATUS_URL() {
		return HEAD_URL + "api/v2.0/device/{userid}";
	}

	/**
	 * 创建url 工具
	 * 
	 * @param url
	 * @param reqParam
	 * @return
	 */
	public static String creatUrl(String url, Map<String, String> reqParam) {
		url = concatRestUriTemplate(url, reqParam);
		return url;
	}

	/**
	 * 将rest请求的路径上的参数替换掉,如/api/v1/app/{uid}/shares?pageNum={pageNum}
	 * 
	 * @param restUriTemplate
	 *            请求的RestPath模板uri
	 * @param restPathParam
	 * @return
	 */
	private static String concatRestUriTemplate(String restUriTemplate,
			Map<String, String> restPathParam) throws IllegalArgumentException {
		if (StringUtils.isEmpty(restUriTemplate)) {
			throw new IllegalArgumentException("restUriTemplate 不能为空！");
		}

		String restUri = restUriTemplate;
		try {
			for (Map.Entry<String, String> param : restPathParam.entrySet()) {
				restUri = restUri.replace(param.getKey(), param.getValue());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e("========" + e.getClass() + e.getLocalizedMessage()
					+ "Url构建出错，缺少参数！", e);
		}

		restUri = restUri.replace(" ", "%20");
		return restUri;
	}

}
