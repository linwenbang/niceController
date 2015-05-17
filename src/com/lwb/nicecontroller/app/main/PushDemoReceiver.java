package com.lwb.nicecontroller.app.main;

/**
 * @author lwb
 * 创建日期:2015-5-17 下午3:27:07
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushConsts;
import com.lwb.nicecontroller.utils.LogUtils;

public class PushDemoReceiver extends BroadcastReceiver {

	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {

		mContext = context;

		Bundle bundle = intent.getExtras();
		LogUtils.e("onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传（payload）数据
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null) {
				String data = new String(payload);
				LogUtils.e("Data:" + data);
				// TODO:接收处理透传（payload）数据
				// DialogBtn.showDialog(mContext, data);
				Intent pushIntent = new Intent(MESSAGE_RECEIVED_ACTION);
				Bundle pushBundle = new Bundle();
				pushBundle.putString("cn.jpush.android.MESSAGE", data);
				mContext.sendBroadcast(pushIntent);
			}
			// 获取taskid和messageid
			String taskid = bundle.getString("taskid");
			String messageid = bundle.getString("messageid");
			// TODO:
			/* 保存taskid和messageid供自定义事件回执使用 */
			LogUtils.e("taskid　＋ " + taskid + " messageid：" + messageid);
			break;

		default:
			break;
		}
	}
}