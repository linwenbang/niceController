package com.lwb.nicecontroller.app.user.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.base.BaseActivity;
import com.lwb.nicecontroller.base.MyApplication;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;
import com.lwb.nicecontroller.utils.SetTagOrAlisaUtils;

/**
 * @author lwb
 * 创建日期:2015-5-8 下午5:49:14
 */
public class AdminManagerActivity extends BaseActivity implements OnClickListener{

	private TextView txt_service_modify;
	private TextView txt_admin_pwd_modify;
	private TextView txt_get_users_list;
	private TextView txt_logout;
	
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.admin_manager_layout);
		
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		txt_service_modify = (TextView) findViewById(R.id.txt_service_modify);
		txt_admin_pwd_modify = (TextView) findViewById(R.id.txt_admin_pwd_modify);
		txt_get_users_list = (TextView) findViewById(R.id.txt_get_users_list);
		txt_logout = (TextView) findViewById(R.id.txt_logout);
		
		txt_service_modify.setOnClickListener(this);
		txt_admin_pwd_modify.setOnClickListener(this);
		txt_get_users_list.setOnClickListener(this);
		txt_logout.setOnClickListener(this);
		
		DialogBtn.showDialog(mContext, "登录成功");
	}
	
	
	/**
	 * 管理员退出 
	 * put: /api/v2.0/logout/{userid}
	 */
	private void signOut() {

		pDialog = ProgressDialog.show(this, "请稍等", "数据加载中");

		String url = UrlContants.getADMIN_SIGN_OUT_URL();
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		url = UrlContants.creatUrl(url, reqParam);
		HttpUtils.put(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String json = null;
				try {
					json = new String(arg2, "GB2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.e("返回结果" + json);
				pDialog.dismiss();
				ResultHandler(json);

			}

			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				pDialog.dismiss();

				showShortToast("onFailure");
			}

		});

	}
	
	
	/**
	 * @param json
	 * @param success
	 */
	private void ResultHandler(String json) {
		int code = FastjsonUtils.getCode(json);
		String summary = FastjsonUtils.getSummary(json);

		switch (code) {
		case 200:
		case 201:
			showShortToast("退出成功");
			// 设置全局标志位
			MyApplication.isAdmin = false;
			// 取消Admin标签,设置成user标签
			new SetTagOrAlisaUtils().setTag("user", this);
			finish();
		default:
			DialogBtn.showDialog(mContext, summary);
			MyApplication.isAdmin = false;	
			
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.txt_service_modify:
			openActivity(ModifyServerUrlActivity.class);
			break;
		case R.id.txt_admin_pwd_modify:
			openActivity(ModifyPwdActivity.class);
			break;
		case R.id.txt_get_users_list:
			openActivity(UserManagerActivity.class);
			break;
		case R.id.txt_logout:
			signOut();
			break;
		default:
			break;
		}
	}
	
}
