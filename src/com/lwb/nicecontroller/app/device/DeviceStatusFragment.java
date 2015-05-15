package com.lwb.nicecontroller.app.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.app.view.DialogBtn;
import com.lwb.nicecontroller.base.BaseFragment;
import com.lwb.nicecontroller.bean.DeviceBean;
import com.lwb.nicecontroller.contants.UrlContants;
import com.lwb.nicecontroller.enums.DeviceActionStatus;
import com.lwb.nicecontroller.enums.DeviceNameStatus;
import com.lwb.nicecontroller.utils.FastjsonUtils;
import com.lwb.nicecontroller.utils.HttpUtils;
import com.lwb.nicecontroller.utils.LogUtils;
import com.lwb.nicecontroller.utils.MacUtils;

public class DeviceStatusFragment extends BaseFragment implements
		OnClickListener {

	// 百度语音识别对话框
	private BaiduASRDigitalDialog mDialog = null;
	private DialogRecognitionListener mDialogListener = null;
	// 应用授权信息 ，这里使用了官方SDK中的参数，如果需要，请自行申请，并修改为自己的授权信息
	private String API_KEY = "eHPfF62QfGdSzkhW0Uahi0EL";
	private String SECRET_KEY = "a766efdcc36e90dc4eb7260201abb89b";

	// 视图控件-----------------------
	private View view;
	// 开始按钮
	private TextView txt_voice;
	// 文本框
	private TextView txt_voice_result;
	private ImageView img_led;
	private ImageView img_fan;
	private ImageView img_beep;
	private ImageView img_lock;
	private ImageView img_hum;
	private ImageView img_temp;

	private TextView txt_temp;
	private TextView txt_hum;
	private TextView txt_refresh;

	private SeekBar seekBar_temp;
	private SeekBar seekBar_hum;

	// 全局常量------------
	private static final String DEVICE_KEY = "device";
	private static final String ACTION_KEY = "action";

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_main, null);

		super.onCreate(savedInstanceState);

		initView();
		initData();
		initVoice();

		return view;
	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		img_beep = (ImageView) view.findViewById(R.id.img_beep);
		img_led = (ImageView) view.findViewById(R.id.img_led);
		img_fan = (ImageView) view.findViewById(R.id.img_fan);
		img_lock = (ImageView) view.findViewById(R.id.img_lock);
		img_hum = (ImageView) view.findViewById(R.id.img_hum);
		img_temp = (ImageView) view.findViewById(R.id.img_temp);

		img_beep.setOnClickListener(this);
		img_led.setOnClickListener(this);
		img_fan.setOnClickListener(this);
		img_lock.setOnClickListener(this);
		img_hum.setOnClickListener(this);
		img_temp.setOnClickListener(this);

		txt_hum = (TextView) view.findViewById(R.id.txt_hum);
		txt_temp = (TextView) view.findViewById(R.id.txt_temp);
		txt_refresh = (TextView) view.findViewById(R.id.txt_refresh);
		txt_refresh.setOnClickListener(this);

		seekBar_hum = (SeekBar) view.findViewById(R.id.seekBar_hum);
		seekBar_temp = (SeekBar) view.findViewById(R.id.seekBar_temp);
	}

	/**
	 * 
	 */
	private void initData() {
		// TODO Auto-generated method stub
		// 获取所有设备状态
		actionForDeviceStatus(DeviceNameStatus.all.name,
				DeviceActionStatus.status.action, null);
	}

	/**
	 * 改变设备图标状态
	 * 
	 * @param view
	 */
	public void changStatus(View device) {
		device.setSelected(!device.isSelected());
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		setDeviceStatus(view);
	}

	/**
	 * 获取所有设备状态 POST: /api/v2.0/device/{userid}
	 */
	private void actionForDeviceStatus(final String device,
			final String action, final View deviceView) {
		String url = UrlContants.getGET_DEVICE_STATUS_URL();
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("{userid}", MacUtils.getMac(mContext));

		url = UrlContants.creatUrl(url, reqParam);
		Map<String, String> body = new HashMap<String, String>();
		body.put(DEVICE_KEY, device);
		body.put(ACTION_KEY, action);

		HttpUtils.post(url, body, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String json) {
				// TODO Auto-generated method stub
				super.onSuccess(json);
				LogUtils.e("返回结果" + json);
				int code = FastjsonUtils.getCode(json);
				switch (code) {
				case 200:
					setResult(device, action, json, deviceView);
					break;
				default:
					String summary = FastjsonUtils.getSummary(json);
					DialogBtn.showDialog(mContext, summary);
					break;
				}
				
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);

				showShortToast("onFailure");
			}
		});

	}

	private DeviceBean deviceBean;

	/**
	 * 处理返回结果
	 */
	private void setResult(String device, String action, String json,
			View deviceView) {
		// TODO Auto-generated method stub
		if (device.equals(DeviceNameStatus.all.name)
				&& action.equals(DeviceActionStatus.status.name())) {
			// 获取所有设备状态
			try {
				String dto = FastjsonUtils.getDto(json);
				deviceBean = (DeviceBean) FastjsonUtils.getBeanObject(dto,
						DeviceBean.class);

				img_led.setSelected(deviceBean.getLed());
				img_fan.setSelected(deviceBean.getFan());
				img_beep.setSelected(deviceBean.getBeep());
				img_lock.setSelected(deviceBean.getSafe_mode());

				int hum = deviceBean.getWet();
				int temp = deviceBean.getTemp();

				txt_hum.setText(String.valueOf(hum));
				txt_temp.setText(String.valueOf(temp));

				seekBar_hum.setProgress(hum);
				seekBar_temp.setProgress(temp);
				showShortToast("更新设备状态成功");
			} catch (Exception e) {
				LogUtils.e("返回结果解析错误：" + json);	
			}
		} else {
			changStatus(deviceView);
		}
	}

	/**
	 * 改变设置状态请求设置
	 */
	private void setDeviceStatus(View deviceView) {
		// TODO Auto-generated method stub
		String deviceName = null;
		Boolean isOpen = false;

		if (deviceView.getId() == R.id.txt_refresh) {
			actionForDeviceStatus(DeviceNameStatus.all.name,
					DeviceActionStatus.status.action, null);
			return;
		}

		if (deviceBean == null) {
			showShortToast("需先成功刷新所有设备状态-自动刷新中...");
			actionForDeviceStatus(DeviceNameStatus.all.name,
					DeviceActionStatus.status.action, null);
			return;
		}

		switch (deviceView.getId()) {
		case R.id.img_beep:
			deviceName = DeviceNameStatus.beep.name;
			isOpen = deviceBean.getBeep();
			break;
		case R.id.img_fan:
			deviceName = DeviceNameStatus.fan.name;
			isOpen = deviceBean.getFan();
			break;
		case R.id.img_led:
			deviceName = DeviceNameStatus.led.name;
			isOpen = deviceBean.getLed();
			break;
		case R.id.img_lock:
			deviceName = DeviceNameStatus.safe_mode.name;
			isOpen = deviceBean.getSafe_mode();
			break;
		default:
			break;
		}

		if (isOpen) {
			// 关闭申请
			actionForDeviceStatus(deviceName, DeviceActionStatus.close.action,
					deviceView);
		} else {
			// 开启申请
			actionForDeviceStatus(deviceName, DeviceActionStatus.open.action,
					deviceView);
		}
	}

	/**
	 * 
	 */
	private void initVoice() {
		// TODO Auto-generated method stub
		if (mDialog == null) {
			if (mDialog != null) {
				mDialog.dismiss();
			}
			Bundle params = new Bundle();
			// 设置API_KEY, SECRET_KEY
			params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
			params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, SECRET_KEY);
			// 设置语音识别对话框为蓝色高亮主题
			params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
					BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
			// 实例化百度语音识别对话框
			mDialog = new BaiduASRDigitalDialog(getActivity(), params);
			// 设置百度语音识别回调接口
			mDialogListener = new DialogRecognitionListener() {

				@Override
				public void onResults(Bundle mResults) {
					ArrayList<String> rs = mResults != null ? mResults
							.getStringArrayList(RESULTS_RECOGNITION) : null;
					if (rs != null && rs.size() > 0) {

						String result = (String) rs.get(0).subSequence(0,
								rs.get(0).length() - 1);
						LogUtils.e("语音抓取字符：" + result);
						txt_voice_result.setText(result);
						if (result.equals("刷新")) {
							// 获取所有设备状态
							actionForDeviceStatus(DeviceNameStatus.all.name,
									DeviceActionStatus.status.action, null);
						}
					}

				}

			};
			mDialog.setDialogRecognitionListener(mDialogListener);
		}
		// 设置语音识别模式为输入模式
		mDialog.setSpeechMode(BaiduASRDigitalDialog.SPEECH_MODE_INPUT);
		// mDialog.se
		// 禁用语义识别
		mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_NLU_ENABLE,
				false);

		// 界面元素
		txt_voice = (TextView) view.findViewById(R.id.txt_voice);
		txt_voice_result = (TextView) view.findViewById(R.id.txt_voice_result);
		txt_voice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.show();
			}
		});

	}

}
