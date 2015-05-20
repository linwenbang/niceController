package com.lwb.nicecontroller.app.device;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.app.view.DialogBtn.setPositiveButton;
import com.lwb.nicecontroller.base.BaseActivity;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;

/**
 * @author lwb 创建日期:2015-5-17 下午7:05:25
 */
public class SeekInfoActivity extends BaseActivity {

	private EditText edt_warningValue0;
	private EditText edt_warningValue1;
	private TextView txt_0;
	private TextView txt_1;
	private TextView txt_submit;
	private ImageView img_icon0;
	private ImageView img_icon1;

	private int type;

	// 全局常量------------
	private static final String DEVICE_KEY = "device";
	private static final String ACTION_KEY = "action";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seek_info_layout);

		initView();
		initData();
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		type = bundle.getInt("type");
		switch (type) {
		case 0:// temp_hum

			break;
		case 1:// cpu_gpu
			img_icon0.setImageResource(R.drawable.cpu_temp);
			img_icon1.setImageResource(R.drawable.gpu_temp);

			txt_0.setText("CPU");
			txt_1.setText("GPU");

			break;

		default:
			break;
		}

		float temp0, temp1;
		temp0 = bundle.getFloat("temp0");
		temp1 = bundle.getFloat("temp1");

		edt_warningValue0.setText(String.valueOf(temp0));
		edt_warningValue1.setText(String.valueOf(temp1));

	}

	private void initView() {
		img_icon0 = (ImageView) findViewById(R.id.img_icon0);
		img_icon1 = (ImageView) findViewById(R.id.img_icon1);
		txt_submit = (TextView) findViewById(R.id.txt_submit);
		edt_warningValue0 = (EditText) findViewById(R.id.edt_warning_value0);
		edt_warningValue1 = (EditText) findViewById(R.id.edt_warning_value1);

		txt_0 = (TextView) findViewById(R.id.txt_0);
		txt_1 = (TextView) findViewById(R.id.txt_1);

		txt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String action = edt_warningValue0.getText() + "&"
						+ edt_warningValue1.getText();
				String device = null;
				switch (type) {
				case 0:
					device = "setRoomWarnValue";
					break;
				case 1:
					device = "setSystemWarnValue";
					break;

				default:
					break;
				}
				actionForDeviceStatus(device, action);
			}
		});
	}

	/**
	 * 获取所有设备状态 POST: /api/v2.0/device/{userid}
	 */
	private void actionForDeviceStatus(final String device, final String action) {
		String url = UrlContants.getGET_DEVICE_STATUS_URL();
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		url = UrlContants.creatUrl(url, reqParam);
		Map<String, String> body = new HashMap<String, String>();
		body.put(DEVICE_KEY, device);
		body.put(ACTION_KEY, action);

		HttpUtils.post(url, body, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				showShortToast("onFailure");
			}

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
				setResult(200);
				int code = FastjsonUtils.getCode(json);
				switch (code) {
				case 200:
					setResult(device, action, json);
					break;
				default:
					String summary = FastjsonUtils.getSummary(json);
					DialogBtn.showDialog(mContext, summary);
					break;
				}
			}
		});

	}

	/**
	 * 处理返回结果
	 */
	private void setResult(String device, String action, String json) {
		// TODO Auto-generated method stub
		DialogBtn.showDialog(mContext, "设置成功", "确定", new setPositiveButton() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
