package com.lwb.nicecontroller.app.user.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.base.BaseActivity;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;

/**
 * @author lwb 创建日期:2015-4-10 上午12:50:12
 */
public class ModifyPwdActivity extends BaseActivity {

	private View view;
	private EditText edt_old_pwd;
	private EditText edt_new_pwd;
	private Button btn_submit;


	
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		view = LayoutInflater.from(this).inflate(R.layout.modify_pwd_layout, null);
		setContentView(view);
		initView();
		initData();
	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		edt_new_pwd = (EditText) view.findViewById(R.id.edt_new_pwd);
		edt_old_pwd = (EditText) view.findViewById(R.id.edt_old_pwd);
		btn_submit = (Button) view.findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				submit();
			}
		});
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 1.3修改密码
	 * PUT: /api/v2.0/changepassword/{userid}
	 * body:
	 * userid
	 * oldpassword
	 * newpassword
	 */
	private void submit() {

		String newPwd = edt_new_pwd.getText().toString();
		String oldPwd = edt_old_pwd.getText().toString();

		if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(oldPwd)) {
			showShortToast("请输入新旧密码");
			return;
		}
		if (newPwd.equals(oldPwd)) {
			showShortToast("新旧密码不能一样");
			return;
		}

		String url = UrlContants.getMODIFY_PWD_URL();
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		url = UrlContants.creatUrl(url, reqParam);

		Map<String, String> body = new HashMap<String, String>();
		body.put("userid",  MacUtils.getMac(mContext));
		body.put("oldpassword", oldPwd);
		body.put("newpassword", newPwd);
		
		
		HttpUtils.put(url, body, new AsyncHttpResponseHandler() {
			
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
				int code = FastjsonUtils.getCode(json);
				switch (code) {
				case 200:
					DialogBtn.showDialog(mContext, "修改密码成功");
					edt_new_pwd.setText("");
					edt_old_pwd.setText("");
					break;

				default:
					DialogBtn.showDialog(mContext,
							FastjsonUtils.getSummary(json));
					break;
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				DialogBtn.showDialog(mContext,
						getResources().getString(R.string.dialog_net_error));
			}
		});
	}
}
