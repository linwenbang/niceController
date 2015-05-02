package com.lwb.nicecontroller.app.testhttp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.base.BaseFragment;
import com.lwb.nicecontroller.utils.HttpUtils;

public class TestGetFragment extends BaseFragment implements OnClickListener {

	private ProgressDialog pDialog;
	private TextView txt_json; // 下面textview，显示获取的所有数据
	private TextView txt_get;
	private EditText edit_url;
	private View view;

	private String url;

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.second_main);
	//
	// }

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.http_get_layout, null);
		initView();
		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		txt_json = (TextView) view.findViewById(R.id.txt_json);
		edit_url = (EditText) view.findViewById(R.id.edit_url);

		txt_get = (TextView) view.findViewById(R.id.txt_http_get);
		txt_get.setOnClickListener(this);

		url = "http://smarthome523000.sinaapp.com/manager?action=login&userid=00112233445566&password=admin";
		edit_url.setText(url);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.txt_http_get:
			get();
			break;
		default:
			break;
		}
	}

	public void get() {
		Log.e("BUG", "get()调用");
		pDialog = ProgressDialog.show(getActivity(), "请稍等", "数据加载中");
		String temp_url = edit_url.getText().toString();
		if (TextUtils.isEmpty(temp_url)) {
			return;
		}
		HttpUtils.get(temp_url, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				pDialog.dismiss();
				Toast.makeText(getActivity(), "onFailure", Toast.LENGTH_LONG)
						.show();
			}

			@Override
			public void onSuccess(String json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				pDialog.dismiss();
				Toast.makeText(getActivity(), "onSuccess", Toast.LENGTH_LONG)
						.show();

				txt_json.setText(json);

				// 开始解析，json 转换成 实体类
				// FastjsonUtils.getBeanObject(json, c);
			}
		});
	}
}