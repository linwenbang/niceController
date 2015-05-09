package com.lwb.nicecontroller.app.user.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.base.BaseActivity;

/**
 * @author lwb
 * 创建日期:2015-5-8 下午5:49:14
 */
public class AdminManagerActivity extends BaseActivity implements OnClickListener{

	private TextView txt_service_modify;
	private TextView txt_admin_pwd_modify;
	private TextView txt_get_users_list;
	
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
		
		txt_service_modify.setOnClickListener(this);
		txt_admin_pwd_modify.setOnClickListener(this);
		txt_get_users_list.setOnClickListener(this);
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

		default:
			break;
		}
	}
	
}
