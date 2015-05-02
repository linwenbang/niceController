package com.lwb.nicecontroller.app.user.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.user.adpater.UserManagerAdpater;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.base.BaseActivity;
import com.lwb.nicecontroller.bean.UserManagerBean;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;

/**
 * 管理已经注册用户的增删改查
 * 
 * @author lwb 创建日期:2015-4-8 下午10:17:13
 */
public class UserManagerActivity extends BaseActivity {

	private View view;
	private UserManagerAdpater userManagerAdpater;
	private ListView lv_users;
	private List<UserManagerBean> usersList =  new ArrayList<UserManagerBean>();
	private TextView txt_title;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		view = LayoutInflater.from(this).inflate(R.layout.user_manager_layout, null);
		setContentView(view);
		initView();
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
//		getList();
	}

	/**
	 * 获取用户列表
	 * GET: /api/v2.0/regist/{userid}
	 */
	private void getList() {
		// TODO Auto-generated method stub
		String url = UrlContants.getGET_USERS_URL();

		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		url = UrlContants.creatUrl(url, reqParam);

		HttpUtils.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				showShortToast("onSuccess");
				LogUtils.e("返回结果:" + json);

				int code = FastjsonUtils.getCode(json);
				String dto = FastjsonUtils.getDto(json);
				switch (code) {
				case 200:
					usersList = FastjsonUtils.getBeanList(dto,
							UserManagerBean.class);
					userManagerAdpater = new UserManagerAdpater(mContext, usersList);
					lv_users.setAdapter(userManagerAdpater);
					
					break;

				default:
					DialogBtn.showDialog(mContext,
							FastjsonUtils.getSummary(json));
					break;
				}

			}

			@Override
			public void onFailure(Throwable arg0, String json) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, json);
				showShortToast("onFailure");
			}
		});

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		lv_users = (ListView) view.findViewById(R.id.lv_users);
		
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getList();
			}
		});

	}

}
