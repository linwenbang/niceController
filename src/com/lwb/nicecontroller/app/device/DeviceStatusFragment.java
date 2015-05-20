package com.lwb.nicecontroller.app.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.R.string;
import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
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
	private ImageView img_auto_led;
	private ImageView img_sound;
	private ImageView img_pir;
	private ImageView img_motor;

	private TextView txt_temp;
	private TextView txt_hum;
	private TextView txt_refresh;
	private TextView txt_cpu_temp;
	private TextView txt_gpu_temp;

	private SeekBar seekBar_temp;
	private SeekBar seekBar_hum;

	private SeekBar seekBar_cpu;
	private SeekBar seekBar_gpu;

	private LinearLayout ly_seek_cpu_gpu;
	private LinearLayout ly_seek_temp_hum;

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
		initPicWindow();
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
		img_auto_led = (ImageView) view.findViewById(R.id.img_auto_led);
		img_sound = (ImageView) view.findViewById(R.id.img_sound);
		img_pir = (ImageView) view.findViewById(R.id.img_pir);
		img_motor = (ImageView) view.findViewById(R.id.img_motor);

		ly_seek_cpu_gpu = (LinearLayout) view
				.findViewById(R.id.ly_seek_cpu_gpu);
		ly_seek_temp_hum = (LinearLayout) view
				.findViewById(R.id.ly_seek_temp_hum);

		img_beep.setOnClickListener(this);
		img_led.setOnClickListener(this);
		img_fan.setOnClickListener(this);
		img_lock.setOnClickListener(this);
		img_auto_led.setOnClickListener(this);
		// img_sound.setOnClickListener(this);
		// img_pir.setOnClickListener(this);
		img_motor.setOnClickListener(this);

		ly_seek_cpu_gpu.setOnClickListener(this);
		ly_seek_temp_hum.setOnClickListener(this);

		txt_hum = (TextView) view.findViewById(R.id.txt_hum);
		txt_temp = (TextView) view.findViewById(R.id.txt_temp);
		txt_cpu_temp = (TextView) view.findViewById(R.id.txt_cpu_temp);
		txt_gpu_temp = (TextView) view.findViewById(R.id.txt_gpu_temp);
		txt_refresh = (TextView) view.findViewById(R.id.txt_refresh);
		txt_refresh.setOnClickListener(this);

		seekBar_hum = (SeekBar) view.findViewById(R.id.seekBar_hum);
		seekBar_temp = (SeekBar) view.findViewById(R.id.seekBar_temp);
		seekBar_cpu = (SeekBar) view.findViewById(R.id.seekBar_cpu);
		seekBar_gpu = (SeekBar) view.findViewById(R.id.seekBar_gpu);

		// 禁止用户进行拉动
		seekBar_hum.setEnabled(false);
		seekBar_temp.setEnabled(false);
		seekBar_cpu.setEnabled(false);
		seekBar_gpu.setEnabled(false);
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

		Bundle bundle = new Bundle();
		switch (view.getId()) {
		case R.id.ly_seek_temp_hum:
			bundle.putInt("type", 0);
			openActivityForResult(SeekInfoActivity.class, 0, bundle);
			return;
		case R.id.ly_seek_cpu_gpu:
			bundle.putInt("type", 1);
			openActivityForResult(SeekInfoActivity.class, 0, bundle);
			return;
		default:
			break;
		}
		setDeviceStatus(view);
	}
	private PopupWindow popupWindow;
	private void initPicWindow() {
		// 创建PopupWindow对象
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		// 引入窗口配置文件
		View view = inflater.inflate(R.layout.pic_windows_layout, null);
		popupWindow = new PopupWindow(view,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, false);
		final ImageView img_pic = (ImageView) view.findViewById(R.id.img_pic);
		// 需要设置一下此参数，点击外边可消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		popupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		popupWindow.setFocusable(true);
		img_sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popupWindow.isShowing()) {
					// 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
					popupWindow.dismiss();
				} else {
					// 显示窗口
					popupWindow.showAsDropDown(v);
					actionForDeviceStatus(DeviceNameStatus.picture.name,
							DeviceActionStatus.getPic.action, img_pic);
				}
			}
		});

	}

	/**
	 * 获取所有设备状态 POST: /api/v2.0/device/{userid}
	 */
	public void actionForDeviceStatus(final String device, final String action,
			final View deviceView) {
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
				DialogBtn.showDialog(mContext, "网络请求失败");
			}
		});

	}

	private DeviceBean deviceBean;

	/**
	 * 处理返回结果
	 */
	private void setResult(String device, String action, String json,
			View deviceView) {

		if (device.equals(DeviceNameStatus.picture.name)
				&& action.equals(DeviceActionStatus.getPic.name())) {
			//将二进制文件转化成位图
			
			String picData;
			
			
			
			return ;
		}
		
		
		if (device.equals(DeviceNameStatus.all.name)
				&& action.equals(DeviceActionStatus.status.name())) {
			// 获取所有设备状态
			String dto = FastjsonUtils.getDto(json);
			try {
				deviceBean = (DeviceBean) FastjsonUtils.getBeanObject(dto,
						DeviceBean.class);
				if (deviceBean == null) {
					LogUtils.e("deviceBean 为空");
					return;
				}

				img_led.setSelected(deviceBean.getLed());
				img_fan.setSelected(deviceBean.getFan());
				img_beep.setSelected(deviceBean.getBeep());
				img_lock.setSelected(deviceBean.getSafe_mode());
				img_auto_led.setSelected(deviceBean.getLed_auto());
				img_pir.setSelected(deviceBean.getPir());
				img_sound.setSelected(deviceBean.getSound());
				img_motor.setSelected(deviceBean.getMotor());

				float hum = deviceBean.getWet();
				float temp = deviceBean.getTemp();
				float cpu_temp = deviceBean.getCpu_temp();
				float gpu_temp = deviceBean.getGpu_temp();

				txt_hum.setText(String.valueOf(hum));
				txt_temp.setText(String.valueOf(temp));
				txt_cpu_temp.setText(String.valueOf(cpu_temp));
				txt_gpu_temp.setText(String.valueOf(gpu_temp));
				seekBar_hum.setProgress((int) hum);
				seekBar_temp.setProgress((int) temp);
				seekBar_cpu.setProgress((int) cpu_temp);
				seekBar_gpu.setProgress((int) gpu_temp);

				showShortToast("更新设备状态成功");
			} catch (Exception e) {
				LogUtils.e("返回结果设置错误：" + dto);
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
			break;
		case R.id.img_fan:
			deviceName = DeviceNameStatus.fan.name;
			break;
		case R.id.img_led:
			deviceName = DeviceNameStatus.led.name;
			break;
		case R.id.img_lock:
			deviceName = DeviceNameStatus.safe_mode.name;
			break;
		case R.id.img_auto_led:
			deviceName = DeviceNameStatus.led_auto.name;
			break;
		case R.id.img_sound:
			deviceName = DeviceNameStatus.sound.name;
			break;
		case R.id.img_pir:
			deviceName = DeviceNameStatus.pir.name;
			break;
		case R.id.img_motor:
			deviceName = DeviceNameStatus.motor.name;
			break;

		default:
			break;
		}

		if (deviceView.isSelected()) {
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
