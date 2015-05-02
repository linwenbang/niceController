//package com.lwb.nicecontroller.app.testhttp;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import android.annotation.SuppressLint;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.android.api.TagAliasCallback;
//
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.lwb.nicecontroller.R;
//import com.lwb.nicecontroller.app.view.DialogBtn;
//import com.lwb.nicecontroller.app.view.DialogBtn.setNegativeButton;
//import com.lwb.nicecontroller.app.view.DialogBtn.setPositiveButton;
//import com.lwb.nicecontroller.base.BaseFragment;
//import com.lwb.nicecontroller.bean.RegisterResultBean;
//import com.lwb.nicecontroller.contants.UrlContants;
//import com.lwb.nicecontroller.jpush.ExampleUtil;
//import com.lwb.nicecontroller.utils.FastjsonUtils;
//import com.lwb.nicecontroller.utils.HttpUtils;
//import com.lwb.nicecontroller.utils.LogUtils;
//import com.lwb.nicecontroller.utils.MacUtils;
//
//public class TestPushFragment extends BaseFragment {
//
//	private static final String TAG = "JPush";
//	public static boolean isForeground = false;
//	private ListView listView_push;
//	private SimpleAdapter simpleAdapter;
//	private View view;
//	private List<Map<String, String>> pushList = new ArrayList<Map<String, String>>();
//	private RegisterResultBean registerResultBean;
//
//	@SuppressLint("InflateParams")
//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			 ViewGroup container,  Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		view = inflater.inflate(R.layout.http_push_layout, null);
//		initView();
//		registerMessageReceiver(); // used for receive msg
//		return view;
//	}
//
//	private void initView() {
//		listView_push = (ListView) view.findViewById(R.id.listView_push);
//
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("info", "测试数据显示：（用于显示自定义消息）");
//		pushList.add(map);
//		simpleAdapter = new SimpleAdapter(getActivity(), pushList,
//				R.layout.push_item, new String[] { "info" },
//				new int[] { R.id.txt_info });
//		listView_push.setAdapter(simpleAdapter);
//
//		TextView mImei = (TextView) view.findViewById(R.id.txt_imei);
//		String udid = ExampleUtil.getImei(
//				getActivity().getApplicationContext(), "");
//		if (null != udid)
//			mImei.setText("IMEI: " + udid);
//
//		TextView mAppKey = (TextView) view.findViewById(R.id.txt_appkey);
//		String appKey = ExampleUtil.getAppKey(getActivity()
//				.getApplicationContext());
//		if (null == appKey)
//			appKey = "AppKey异常";
//		mAppKey.setText("AppKey: " + appKey);
//
//		String packageName = getActivity().getPackageName();
//		TextView mPackage = (TextView) view.findViewById(R.id.txt_packageName);
//		mPackage.setText("PackageName: " + packageName);
//
//		String versionName = ExampleUtil.GetVersion(getActivity()
//				.getApplicationContext());
//		TextView mVersion = (TextView) view.findViewById(R.id.txt_version);
//		mVersion.setText("Version: " + versionName);
//
//		final EditText editText = (EditText) view
//				.findViewById(R.id.editText_tag);
//		TextView txt_setTag = (TextView) view.findViewById(R.id.txt_setTag);
//		txt_setTag.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				// 检查 tag 的有效性
//				String tag = editText.getText().toString();
//				if (TextUtils.isEmpty(tag)) {
//					showShortToast("昵称不能为空");
//					return;
//				}
//
//				// ","隔开的多个 转换成 Set
//				String[] sArray = tag.split(",");
//				Set<String> tagSet = new LinkedHashSet<String>();
//				for (String sTagItme : sArray) {
//					if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
//						Toast.makeText(getActivity(), "不能为空",
//								Toast.LENGTH_SHORT).show();
//						return;
//					}
//					tagSet.add(sTagItme);
//				}
//
//				// 调用JPush API设置Tag
//				mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS,
//						tagSet));
//			}
//		});
//
//	}
//
//	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {
//
//		@Override
//		public void gotResult(int code, String alias, Set<String> tags) {
//			String logs;
//			switch (code) {
//			case 0:
//				logs = "Set tag and alias success";
//				break;
//
//			case 6002:
//				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//				if (ExampleUtil.isConnected(getActivity()
//						.getApplicationContext())) {
//					mHandler.sendMessageDelayed(
//							mHandler.obtainMessage(MSG_SET_TAGS, tags),
//							1000 * 60);
//				} else {
//					Log.e(TAG, "No network");
//				}
//				break;
//
//			default:
//				logs = "Failed with errorCode = " + code;
//			}
//
//			ExampleUtil.showToast(logs, getActivity().getApplicationContext());
//			showShortToast(logs);
//		}
//
//	};
//
//	private static final int MSG_SET_TAGS = 1002;
//
//	private final Handler mHandler = new Handler() {
//		@SuppressWarnings("unchecked")
//		@Override
//		public void handleMessage(android.os.Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//
//			case MSG_SET_TAGS:
//				JPushInterface.setAliasAndTags(getActivity()
//						.getApplicationContext(), null, (Set<String>) msg.obj,
//						mTagsCallback);
//				break;
//
//			default:
//			}
//		}
//	};
//
//	@Override
//	public void onDestroyView() {
//		// TODO Auto-generated method stub
//		super.onDestroyView();
//		getActivity().unregisterReceiver(mMessageReceiver);
//	}
//
//	// for receive customer msg from jpush server
//	private MessageReceiver mMessageReceiver;
//	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
//	public static final String KEY_TITLE = "title";
//	public static final String KEY_MESSAGE = "message";
//	public static final String KEY_EXTRAS = "extras";
//
//	public void registerMessageReceiver() {
//		mMessageReceiver = new MessageReceiver();
//		IntentFilter filter = new IntentFilter();
//		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//		filter.addAction(MESSAGE_RECEIVED_ACTION);
//		getActivity().registerReceiver(mMessageReceiver, filter);
//	}
//
//	public class MessageReceiver extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//				Bundle bundle = intent.getExtras();
//				// 获取信息
//				if (bundle != null) {
//					setCostomMsg(printBundle(bundle));
//					String msg = bundle.getString("cn.jpush.android.MESSAGE");
//					LogUtils.e("msg = " + msg);
//					registerResultBean = (RegisterResultBean) FastjsonUtils
//							.getBeanObject(msg, RegisterResultBean.class);
//
//					// 判断推送类别，进行处理-------------------------------------------
//					if (registerResultBean != null
//							&& registerResultBean.getMsg_type().equals(
//									"register")) {
//						DialogBtn.showDialog(mContext,
//								registerResultBean.toString() + "\n申请注册,是否允许",
//								new setPositiveButton() {
//
//									@Override
//									public void onClick() {
//										// TODO Auto-generated method stub
//										setVerification();
//									}
//
//								}, new setNegativeButton() {
//
//									@Override
//									public void onClick() {
//										// TODO Auto-generated method stub
//
//									}
//
//								});
//					}
//
//				}
//
//			}
//		}
//	}
//
//	private String url;
//
//	/**
//	 * 管理员允许注册
//	 * "manager?action=verifyuser&sure={yes}&userid={mac}&newuserid={newmac}&alias={alias}"
//	 */
//	private void setVerification() {
//		// TODO Auto-generated method stub
//		url = UrlContants.getADMIN_VERIFY_URL();
//		Map<String, String> reqParam = new HashMap<String, String>();
//		reqParam.put("{mac}", MacUtils.getMac(mContext));
//		reqParam.put("{yes}", "yes");
//		reqParam.put("{newmac}", registerResultBean.getUserid());
//		reqParam.put("{alias}", registerResultBean.getAlias());
//
//		url = UrlContants.creatUrl(url, reqParam);
//
//		HttpUtils.get(url, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(String arg0) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0);
//				showShortToast("允许成功" + url);
//			}
//
//			@Override
//			public void onFailure(Throwable arg0, String arg1) {
//				// TODO Auto-generated method stub
//				super.onFailure(arg0, arg1);
//				showShortToast("允许失败");
//			}
//		});
//	}
//
//	private void setCostomMsg(String msg) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("info", msg);
//		pushList.add(map);
//		simpleAdapter.notifyDataSetChanged();
//	}
//
//	// 打印所有的 intent extra 数据
//	private static String printBundle(Bundle bundle) {
//		StringBuilder sb = new StringBuilder();
//		for (String key : bundle.keySet()) {
//			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//			} else {
//				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//			}
//		}
//		return sb.toString().trim();
//	}
//
//}