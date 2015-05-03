package com.lwb.nicecontroller.app.user.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.base.BaseActivity;
import com.lwb.nicecontroller.contants.SharedPreferencesConstants;
import com.lwb.nicecontroller.contants.UrlContants;

/**
 * @author lwb 创建日期:2015-5-2 下午1:52:35
 */
public class ModifyServerUrlActivity extends BaseActivity {

	private TextView txt_old_url;
	private Button btn_submit;
	private EditText edt_modify_url;
	private SharedPreferences sp;

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		view = LayoutInflater.from(this).inflate(
				R.layout.modify_server_url_layout, null);
		setContentView(view);
		initView();
		initData();

	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		txt_old_url = (TextView) findViewById(R.id.txt_old_url);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		edt_modify_url = (EditText) findViewById(R.id.edt_modify_url);

		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				submit();
			}

		});

		sp = getSharedPreferences(UrlContants.HEAD_URL_SP, 0);

		txt_old_url.setText(UrlContants.HEAD_URL);

	}

	/**
	 * 修改服务器地址url头
	 */
	private void submit() {
		String new_url = edt_modify_url.getText().toString().trim();
		if (TextUtils.isEmpty(new_url)) {
			new_url = UrlContants.HEAD_URL_DEFAULT;
		}

		Editor editor = sp.edit();
		editor.putString(SharedPreferencesConstants.HEAD_URL_KEY, new_url);
		editor.commit();

		UrlContants.HEAD_URL = new_url;
		txt_old_url.setText(new_url);

		// 关闭软键盘
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub

	}

}
