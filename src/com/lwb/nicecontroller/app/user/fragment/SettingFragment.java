package com.lwb.nicecontroller.app.user.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.user.activity.AdminLoginActivity;
import com.lwb.nicecontroller.app.user.activity.ModifyPwdActivity;
import com.lwb.nicecontroller.app.user.activity.ModifyServerUrlActivity;
import com.lwb.nicecontroller.app.user.activity.UserManagerActivity;
import com.lwb.nicecontroller.app.user.activity.UserRegisterActivity;
import com.lwb.nicecontroller.base.BaseFragment;

/**
 * @author lwb 创建日期:2015-5-1 下午4:39:46
 */
public class SettingFragment extends BaseFragment implements OnClickListener {

	private View view;
	
	//控件-------------------------
	private TextView txt_admin_login;
	private TextView txt_service_modify;
	private TextView txt_admin_pwd_modify;
	private TextView txt_user_register;
	private TextView txt_get_users_list;


	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.setting_layout, null);
		initView();
		initData();
		return view;
	}


	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		txt_admin_login = (TextView) view.findViewById(R.id.txt_admin_login);
		txt_service_modify = (TextView) view.findViewById(R.id.txt_service_modify);
		txt_admin_pwd_modify = (TextView) view.findViewById(R.id.txt_admin_pwd_modify);
		txt_user_register = (TextView) view.findViewById(R.id.txt_user_register);
		txt_get_users_list = (TextView) view.findViewById(R.id.txt_get_users_list);
		
		txt_admin_login.setOnClickListener(this);
		txt_service_modify.setOnClickListener(this);
		txt_admin_pwd_modify.setOnClickListener(this);
		txt_user_register.setOnClickListener(this);
		txt_get_users_list.setOnClickListener(this);
	}


	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.txt_admin_login:
			openActivity(AdminLoginActivity.class);
			break;
		case R.id.txt_service_modify:
			openActivity(ModifyServerUrlActivity.class);
			break;
		case R.id.txt_admin_pwd_modify:
			openActivity(ModifyPwdActivity.class);
			break;
		case R.id.txt_user_register:
			openActivity(UserRegisterActivity.class);
			break;
			
		case R.id.txt_get_users_list:
			openActivity(UserManagerActivity.class);
			break;
	

		default:
			break;
		}
	}

}
