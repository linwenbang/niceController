package com.lwb.nicecontroller.base;

import java.util.Stack;

import com.lwb.nicecontroller.app.main.NiceMainActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * 
 * @author lwb
 */
public class AppManagerUtils {

	private static Stack<Activity> activityStack;
	private static AppManagerUtils instance;

	private AppManagerUtils() {
	}

	public int size() {
		return activityStack.size();
	}

	/**
	 * 单一实例
	 */
	public static AppManagerUtils getAppManager() {
		if (instance == null) {
			instance = new AppManagerUtils();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				Activity activity = activityStack.get(i);
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			/*
			 * Intent intent = new Intent(context, MainActivity.class);
			 * PendingIntent restartIntent = PendingIntent.getActivity( context,
			 * 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK); //退出程序 AlarmManager
			 * mgr =
			 * (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			 * mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
			 * restartIntent); // 1秒钟后重启应用
			 */
			// 杀死该应用进程
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
		}
	}

	/**
	 * 退出应用程序
	 */
	public void AppReboot(Context context) {
		try {
			finishAllActivity();
			Intent intent = new Intent(context, NiceMainActivity.class);
			PendingIntent restartIntent = PendingIntent.getActivity(context, 0,
					intent, Intent.FLAG_ACTIVITY_NEW_TASK);
			// 退出程序
			
			AlarmManager mgr = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
					restartIntent); // 1秒钟后重启应用
			// 杀死该应用进程
			
			
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}