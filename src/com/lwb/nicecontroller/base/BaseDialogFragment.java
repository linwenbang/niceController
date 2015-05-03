package com.lwb.nicecontroller.base;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Toast;

/**
 * DialogFragment基类
 * 
 * @author lwb
 * 
 */

public class BaseDialogFragment extends DialogFragment {

	protected Resources res;

	protected Context mContext;

	protected View view;

	protected Dialog dialog;

	protected MyApplication myApp;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = getActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.res = this.getResources();
		this.view = new View(mContext);
		this.myApp = MyApplication.getInstance();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	public void initialization() {
	};

	public void findViews(View v) {
	};

	public void bindEvent() {
	};

	protected void showShortToast(int pResId) {
		showShortToast(getString(pResId));
	}

	protected void showLongToast(String pMsg) {
		Toast.makeText(mContext, pMsg, Toast.LENGTH_LONG).show();
	}

	protected void showShortToast(String pMsg) {
		Toast.makeText(mContext, pMsg, Toast.LENGTH_SHORT).show();
	}

	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(mContext, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	protected void openActivityClearTop(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(mContext, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * @param pDialog
	 * @param pIsClose
	 */
	public void setAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
		try {
			Field field = pDialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(pDialog, pIsClose);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void showDialogNetError(final Context mContext,
			Message msg, boolean isGetNetTimeOut) {
		// if (msg.getData() != null) {
		// String summary = msg.getData().getString("summary");
		// String code = msg.getData().getString(KeyConstants.CODE);
		// if (code == null) {
		// return;
		// }
		// if (!isGetNetTimeOut) {
		// if (code.equals(String
		// .valueOf(HttpConstants.REQUEST_URL_LLEGAL_CODE))
		// || code.equals(String
		// .valueOf(HttpConstants.REQUEST_TIMEOUT_CODE))) {
		// return;
		// }
		// }
		// if (code.equals(String
		// .valueOf(HttpConstants.REQUEST_LOGGED_IN_OTHER_CODE))) {
		// ((MyApplication) MyApplication.getInstance()).signOut(null);
		// DialogBtn.showDialog(mContext, summary, "前往登录",
		// new setPositiveButton() {
		//
		// @Override
		// public void onClick() {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(mContext,
		// LoginActivity.class);
		// mContext.startActivity(intent);
		// return;
		// }
		// });
		// return;
		// }
		//
		// if (TextUtils.isEmpty(summary)) {
		// summary = mContext.getString(R.string.dialog_net_error);
		// }
		// DialogBtn.showDialog(mContext, summary, "完成",
		// new setPositiveButton() {
		//
		// @Override
		// public void onClick() {
		// // TODO Auto-generated method stub
		// return;
		// }
		// });
		// }
	}

	protected static void showDialogNetError(final Context mContext, Message msg) {
		showDialogNetError(mContext, msg, false);
	}

}