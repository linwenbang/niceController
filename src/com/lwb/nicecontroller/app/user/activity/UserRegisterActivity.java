package com.lwb.nicecontroller.app.user.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.app.view.DialogBtn.setPositiveButton;
import com.lwb.nicecontroller.base.BaseActivity;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.jpush.ExampleUtil;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;

/**
 * @author lwb 创建日期:2015-4-8 上午9:41:13
 */
public class UserRegisterActivity extends BaseActivity {

	private EditText edt_alisa;
	private Button btn_register;
	private ProgressDialog pDialog;
	private View view;

	private static final String TAG = "JPush";

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		view = LayoutInflater.from(this)
				.inflate(R.layout.register_layout, null);
		setContentView(view);
		initView();
		initData();
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		edt_alisa = (EditText) view.findViewById(R.id.edt_alisa);

		btn_register = (Button) view.findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				registerUser();

				// 关闭软键盘
				if (view != null) {
					InputMethodManager inputmanger = (InputMethodManager) mContext
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
							0);
				}
			}

		});
	}

	/**
	 * 注册别名
	 */

	private void setAlias() {
		// EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
		String alias = edt_alisa.getText().toString().trim();
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			Toast.makeText(mContext, "别名设置错误", Toast.LENGTH_SHORT).show();

			return;
		}

		// 调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}

	/**
	 * 提交注册信息 POST: /api/v2.0/regist/{userid}  Body部分 信息单元 必选 类型 长度 说明 userid 非
	 * String 用户ID Alias 是 String 用户昵称
	 */
	private void registerUser() {
		// TODO Auto-generated method stub

		String alisa = edt_alisa.getText().toString().trim();

		if (TextUtils.isEmpty(alisa)) {
			// Toast.makeText(getActivity(), "请输入昵称", Toast.LENGTH_LONG).show();
			DialogBtn.showDialog(mContext, "请输入昵称");
			return;
		}

		String url = UrlContants.getREGISTER_URL();

		Log.e("BUG", "get()调用");
		pDialog = ProgressDialog.show(this, "请稍等", "数据加载中");
		if (TextUtils.isEmpty(url)) {
			return;
		}

		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		url = UrlContants.creatUrl(url, reqParam);

		Map<String, String> body = new HashMap<String, String>();
		body.put("userid", MacUtils.getMac(mContext));
		body.put("Alias", alisa);

		HttpUtils.post(url, body, new AsyncHttpResponseHandler() {

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
//				setAlias();
				DialogBtn.showDialog(mContext, "申请成功，请等候审核","确定",new setPositiveButton() {
					@Override
					public void onClick() {
						finish();
					}
				});
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

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(mContext)) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_SET_ALIAS, alias),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, mContext);
		}

	};

	private static final int MSG_SET_ALIAS = 1001;

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				JPushInterface.setAliasAndTags(mContext, (String) msg.obj,
						null, mAliasCallback);
				break;

			default:
			}
		}
	};

}
