/**
 * 北京思耐德科技有限公司深圳分公司 所有
 * 创建日期:2014-12-26 下午6:33:27
 */
package com.lwb.nicecontroller.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.lwb.nicecontroller.R;
import com.lwb.nicecontroller.base.MyApplication;

/**
 * 
 * 提示框
 * 
 * @author lwb
 * 
 */
public class DialogBtn {

	public interface setPositiveButton {
		void onClick();
	}

	public interface setNegativeButton {
		void onClick();
	}

	public interface setZXingButton {
		void onClick();
	}

	public interface setConfirmButton {
		void onClick(String text);
	}

	public static void showDialog(Context context, String content,
			String btnName, final setPositiveButton btnOk) {

		if (content == null) {
			content = context.getString(R.string.dialog_net_error);
		}
		// 判断dialog有没有在被使用
		if (MyApplication.DIALOG_USED == 0) {
			MyApplication.DIALOG_USED = 1;
			final Dialog dl = new Dialog(context, R.style.dialogtheme);
			View ly = LayoutInflater.from(context).inflate(R.layout.dialog_btn,
					null);
			ly.setMinimumHeight(LayoutParams.WRAP_CONTENT);
			ly.setMinimumWidth(LayoutParams.WRAP_CONTENT);
			TextView txt_dialog_content = (TextView) ly
					.findViewById(R.id.txt_dialog_content);

			Button cancel = (Button) ly.findViewById(R.id.btn_dialog_cancel);
			cancel.setVisibility(View.GONE);
			View divier = ly.findViewById(R.id.view_dialog_btn_divier);
			divier.setVisibility(View.GONE);

			Button ok = (Button) ly.findViewById(R.id.btn_dialog_ok);
			ok.setText(btnName);
			txt_dialog_content.setText(content);
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btnOk.onClick();
					dl.dismiss();
					MyApplication.DIALOG_USED = 0;
				}
			});

			dl.setCanceledOnTouchOutside(false);
			dl.setContentView(ly);
			android.view.WindowManager.LayoutParams params = dl.getWindow()
					.getAttributes();
			params.height = LayoutParams.WRAP_CONTENT;
			params.width = LayoutParams.MATCH_PARENT;
			dl.getWindow().setAttributes(params);
			dl.setCancelable(false);
			dl.show();
		}

	}

	/**
	 * 显示只有一个按钮的dialog,且点击确定不调用回调函数
	 * 
	 * @param context
	 * @param content
	 */
	public static void showDialog(Context context, String content) {
		if (content == null) {
			content = context.getString(R.string.dialog_net_error);
		}
		String btnName = "确定";
		showDialog(context, content, btnName, new setPositiveButton() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void showDialog(Context context, String content,
			final setPositiveButton btnOk, final setNegativeButton btnCancel) {
		if (content == null) {
			content = context.getString(R.string.dialog_net_error);
		}
		if (MyApplication.DIALOG_USED == 0) {
			MyApplication.DIALOG_USED = 1;
			final Dialog dl = new Dialog(context, R.style.dialogtheme);
			View ly = LayoutInflater.from(context).inflate(R.layout.dialog_btn,
					null);
			ly.setMinimumHeight(LayoutParams.WRAP_CONTENT);
			ly.setMinimumWidth(LayoutParams.WRAP_CONTENT);
			TextView txt_dialog_content = (TextView) ly
					.findViewById(R.id.txt_dialog_content);

			Button ok = (Button) ly.findViewById(R.id.btn_dialog_ok);
			ok.setText("确定");
			Button cancel = (Button) ly.findViewById(R.id.btn_dialog_cancel);
			cancel.setText("取消");

			txt_dialog_content.setText(content);
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					btnOk.onClick();
					dl.dismiss();
					MyApplication.DIALOG_USED = 0;
				}
			});
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnCancel.onClick();
					dl.dismiss();
					MyApplication.DIALOG_USED = 0;
				}
			});

			dl.setCanceledOnTouchOutside(false);
			dl.setContentView(ly);
			android.view.WindowManager.LayoutParams params = dl.getWindow()
					.getAttributes();
			params.height = LayoutParams.WRAP_CONTENT;
			params.width = LayoutParams.MATCH_PARENT;
			dl.getWindow().setAttributes(params);
			dl.setCancelable(false);
			dl.show();
		}
	}

	/**
	 * 程序异常奔溃提示框
	 * 
	 * @param context
	 * @param content
	 * @param btnOk
	 * @param btnCancel
	 */
	public static void showCrashDialog(Context context, String content,
			final setPositiveButton btnOk, final setNegativeButton btnCancel) {
		final Dialog dl = new Dialog(context, R.style.dialogtheme);
		View ly = LayoutInflater.from(context).inflate(R.layout.dialog_btn,
				null);
		ly.setMinimumHeight(LayoutParams.WRAP_CONTENT);
		ly.setMinimumWidth(LayoutParams.WRAP_CONTENT);
		TextView txt_dialog_content = (TextView) ly
				.findViewById(R.id.txt_dialog_content);
		txt_dialog_content.setTextSize(17);

		Button ok = (Button) ly.findViewById(R.id.btn_dialog_ok);
		ok.setText("退出");
		ok.setTextColor(context.getResources().getColor(R.color.gray));
		Button cancel = (Button) ly.findViewById(R.id.btn_dialog_cancel);
		cancel.setText("重启");

		txt_dialog_content.setText(content);
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnOk.onClick();
				dl.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnCancel.onClick();
				dl.dismiss();
			}
		});

		dl.setCanceledOnTouchOutside(false);
		dl.setContentView(ly);
		android.view.WindowManager.LayoutParams params = dl.getWindow()
				.getAttributes();
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.MATCH_PARENT;
		dl.getWindow().setAttributes(params);
		dl.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
		dl.setCancelable(false);
		dl.show();
	}

}
