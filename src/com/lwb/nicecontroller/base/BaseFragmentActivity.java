package com.lwb.nicecontroller.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lwb.nicecontroller.R;

/**
 * FragmentActivity基类
 * 
 * @author lwb
 * 
 */

public class BaseFragmentActivity extends FragmentActivity {

	@SuppressWarnings("unused")
	private static final String TAG = "BaseActivity";

	protected static Resources res;

	protected Context mContext;

	protected MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		res = this.getResources();
		mContext = this;
		this.myApp = MyApplication.getInstance();
		AppManagerUtils.getAppManager().addActivity(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		AppManagerUtils.getAppManager().finishActivity(this);
	}

	public void initialization() {
	};

	public void findViews() {
	};

	public void bindEvent() {
	};

	protected void showShortToast(int pResId) {
		showShortToast(getString(pResId));
	}

	protected void showLongToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
	}

	protected void showShortToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
	}

	protected boolean hasExtra(String pExtraKey) {
		if (getIntent() != null) {
			return getIntent().hasExtra(pExtraKey);
		}
		return false;
	}

	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	protected void openActivityClearTop(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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

	protected void openActivityForResult(Class<?> pClass, int requestCode) {
		openActivityForResult(pClass, requestCode, null);
	}

	protected void openActivityForResult(Class<?> pClass, int requestCode,
			Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	protected void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void animFinish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void defaultFinish() {
		super.finish();
	}

	// protected static void showDialogNetError(final Context mContext,
	// Message msg, boolean isGetNetTimeOut) {
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
	// }
	//
	// protected static void showDialogNetError(final Context mContext, Message
	// msg) {
	// showDialogNetError(mContext, msg, false);
	// }

}
