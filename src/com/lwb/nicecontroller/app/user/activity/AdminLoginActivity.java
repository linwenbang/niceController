package com.lwb.nicecontroller.app.user.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.base.BaseActivity;
import com.lwb.nicecontroller.base.MyApplication;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.MacUtils;
import com.lwb.nicecontroller.utils.SetTagOrAlisaUtils;

/**
 * @author lwb 创建日期:2015-4-7 下午10:56:39
 */
public class AdminLoginActivity extends BaseActivity {

	private EditText edt_admin_pwd;
	private Button btn_login;
	private ProgressDialog pDialog;
	private View view;
	private Button btn_sign_out;


	
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		view = LayoutInflater.from(this).inflate(R.layout.login_layout, null);
		setContentView(view);
		myApp = MyApplication.getInstance();
		initView();
		initData();
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub

	}
	
	private boolean isLogin() {
		if (MyApplication.isAdmin) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 
	 */
	private void initView() {

		if (isLogin()) {
			
		}
		
		edt_admin_pwd = (EditText) view.findViewById(R.id.edt_admin_pwd);
	

		btn_login = (Button) view.findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loginAdmin();

				View view = getWindow().peekDecorView();

				// 关闭软键盘
				if (view != null) {
					InputMethodManager inputmanger = (InputMethodManager) mContext
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
							0);
				}
			}

		});

		btn_sign_out = (Button) view.findViewById(R.id.btn_signOut);
		if (MyApplication.isAdmin) {
			btn_sign_out.setEnabled(true);
		} else {
			btn_sign_out.setEnabled(false);
		}
		btn_sign_out.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				signOut();
			}

		});
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
			public void onSuccess(String json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				pDialog.dismiss();

				ResultHandler(json);

			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				pDialog.dismiss();

				showShortToast("onFailure");
			}
		});

	}

	/**
	 * 管理员登录 
	 * POST: /api/v2.0/login/{userid}
	 * body:
	 * userid
	 * password
	 */
	private void loginAdmin() {
		// TODO Auto-generated method stub

		String pwd = edt_admin_pwd.getText().toString().trim();
		
		if (TextUtils.isEmpty(pwd)) {
			showShortToast("请输入密码");
			return;
		}

		String url = UrlContants.getADMIN_LOGIN_URL();

		if (TextUtils.isEmpty(url)) {
			return;
		}

		pDialog = ProgressDialog.show(this, "请稍等", "数据加载中");

		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		Map<String, String> body = new HashMap<String, String>();
		body.put("userid",  MacUtils.getMac(mContext));
		body.put("password", pwd);
		
		url = UrlContants.creatUrl(url, reqParam);

		HttpUtils.post(url, body ,new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				pDialog.dismiss();
				showShortToast("onFailure");
			}

			@Override
			public void onSuccess(String json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				pDialog.dismiss();
				showShortToast("onSuccess");

				ResultHandler(json);

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
//			DialogBtn.showDialog(mContext, "登录成功");
			// 设置全局标志位
			MyApplication.isAdmin = true;
			btn_login.setEnabled(false);
			btn_sign_out.setEnabled(true);
			// 设置Admin标签
			new SetTagOrAlisaUtils().setTag("admin", this);

			openActivity(AdminManagerActivity.class);
			finish();
			
			break;

		case 201:
			DialogBtn.showDialog(mContext, "退出成功");
			// 设置全局标志位
			MyApplication.isAdmin = false;
			btn_login.setEnabled(true);
			btn_sign_out.setEnabled(false);
			// 取消Admin标签,设置成user标签
			new SetTagOrAlisaUtils().setTag("user", this);
		default:
			DialogBtn.showDialog(mContext, summary);
			MyApplication.isAdmin = false;	
			
			break;
		}
	}

}
