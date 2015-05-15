package com.lwb.nicecontroller.app.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.device.DeviceStatusFragment;
import com.lwb.nicecontroller.app.user.fragment.SettingFragment;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.app.view.DialogBtn.setNegativeButton;
import com.lwb.nicecontroller.app.view.DialogBtn.setPositiveButton;
import com.lwb.nicecontroller.base.BaseFragmentActivity;
import com.lwb.nicecontroller.bean.RegisterResultBean;
import com.lwb.nicecontroller.bean.WarningResultBean;
import com.lwb.nicecontroller.contants.SharedPreferencesConstants;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;

public class NiceMainActivity extends BaseFragmentActivity {
	private List<Fragment> mTabContents = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private ViewPager mViewPager;
	private List<String> mDatas = Arrays.asList("设备状态展示", "设置");
	private ViewPagerIndicator mIndicator;
	private SharedPreferences sp;

	private List<Map<String, String>> pushList = new ArrayList<Map<String, String>>();
	private RegisterResultBean registerResultBean;
	private WarningResultBean warningResultBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vp_indicator);

		initView();
		initDatas();
		registerMessageReceiver();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mMessageReceiver);
	}

	private void initDatas() {
		//开启app 默认为未登录
		myApp.isAdmin = false;
		
		// 初始化head_url
		String head;
		sp = getSharedPreferences(UrlContants.HEAD_URL_SP, 0);
		head = sp.getString(SharedPreferencesConstants.HEAD_URL_KEY,
				UrlContants.HEAD_URL);
		if (head != null && !TextUtils.isEmpty(head)) {
			UrlContants.HEAD_URL = head;
		}

		// 添加fragment
		mTabContents.add(new DeviceStatusFragment());
		mTabContents.add(new SettingFragment());

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTabContents.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mTabContents.get(position);
			}
		};

		// 设置Tab上的标题
		mIndicator.setTabItemTitles(mDatas);
		mViewPager.setAdapter(mAdapter);
		// 设置关联的ViewPager
		mIndicator.setViewPager(mViewPager, 0);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_vp);
		mViewPager.setOffscreenPageLimit(5);
		mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
	}

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				Bundle bundle = intent.getExtras();
				// 获取信息
				if (bundle != null) {
					setCostomMsg(printBundle(bundle));
					String msg = bundle.getString("cn.jpush.android.MESSAGE");
					LogUtils.e("main msg = " + msg);
					
					try {
						registerResultBean = (RegisterResultBean) FastjsonUtils
								.getBeanObject(msg, RegisterResultBean.class);
						if (registerResultBean == null) {
							warningResultBean = (WarningResultBean) FastjsonUtils
									.getBeanObject(msg, WarningResultBean.class);
						}
					} catch (Exception e) {
						LogUtils.e("registerResultBean 解析错误");
						showShortToast("warningResultBean 解析错误");
					}
					

					// 判断推送类别，进行处理-------------------------------------------
					if (registerResultBean != null
							&& registerResultBean.getMsg_type().equals(
									"register")) {
						DialogBtn.showDialog(mContext,
								registerResultBean.toString() + "\n申请注册,是否允许",
								new setPositiveButton() {

									@Override
									public void onClick() {
										// TODO Auto-generated method stub
										setVerification(1);
									}

								}, new setNegativeButton() {

									@Override
									public void onClick() {
										// TODO Auto-generated method stub
										setVerification(0);
									}

								});
					}
					if (warningResultBean != null
							&& warningResultBean.getMsg_type().equals(
									"warnning")) {
						DialogBtn.showDialog(mContext,
								"警告:" + warningResultBean.getData());
								
					}

				}

			}
		}
	}

	private String url;

	/**
	 * 管理员允许注册 PUT: /api/v2.0/regist/{userid}
	 */
	private void setVerification(int confirm) {
		// TODO Auto-generated method stub
		url = UrlContants.getADMIN_VERIFY_URL();
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		url = UrlContants.creatUrl(url, reqParam);

		Map<String, String> body = new HashMap<String, String>();
		body.put("userid", MacUtils.getMac(mContext));
		body.put("newuserid", registerResultBean.getUserid());
		body.put("newalias", registerResultBean.getAlias());
		body.put("confirm", String.valueOf(confirm));
		HttpUtils.post(url, body, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				showShortToast("允许成功" + url);
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				showShortToast("允许失败");
			}
		});
	}

	private void setCostomMsg(String msg) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("info", msg);
		pushList.add(map);
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString().trim();
	}
}
