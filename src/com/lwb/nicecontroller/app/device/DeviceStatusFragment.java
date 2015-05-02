package com.lwb.nicecontroller.app.device;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.base.BaseFragment;

public class DeviceStatusFragment extends BaseFragment {

	// 开始按钮
	private TextView txt_voice;
	// 文本框
	private TextView txt_voice_result;
	// 百度语音识别对话框
	private BaiduASRDigitalDialog mDialog = null;
	private DialogRecognitionListener mDialogListener = null;
	// 应用授权信息 ，这里使用了官方SDK中的参数，如果需要，请自行申请，并修改为自己的授权信息
	private String API_KEY = "eHPfF62QfGdSzkhW0Uahi0EL";
	private String SECRET_KEY = "a766efdcc36e90dc4eb7260201abb89b";

	private View view;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_main, null);

		super.onCreate(savedInstanceState);
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
						txt_voice_result.setText(rs.get(0));
					}

				}

			};
			mDialog.setDialogRecognitionListener(mDialogListener);
		}
		// 设置语音识别模式为输入模式
		mDialog.setSpeechMode(BaiduASRDigitalDialog.SPEECH_MODE_INPUT);
//		mDialog.se
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

		return view;
	}
}
