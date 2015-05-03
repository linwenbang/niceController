package com.lwb.nicecontroller.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	public static final SimpleDateFormat YMDHMS_CHINESE = new SimpleDateFormat(
			"yyyy年MM月dd日HH点mm分ss秒", Locale.getDefault());
	public static final SimpleDateFormat YMDHM_CHINESE = new SimpleDateFormat(
			"yyyy年MM月dd日HH时mm分", Locale.getDefault());
	public static final SimpleDateFormat YMD_CHINESE = new SimpleDateFormat(
			"yyyy年MM月dd日", Locale.getDefault());
	public static final SimpleDateFormat YM_CHINESE = new SimpleDateFormat(
			"yyyy年MM月", Locale.getDefault());

	public static final SimpleDateFormat YMDHMS_ENGLISH = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	public static final SimpleDateFormat YMDHM_ENGLISH = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm", Locale.getDefault());

	public static final SimpleDateFormat Y_ENGLISH = new SimpleDateFormat(
			"yyyy", Locale.getDefault());
	public static final SimpleDateFormat YMD_ENGLISH = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.getDefault());
	public static final SimpleDateFormat YM_ENGLISH = new SimpleDateFormat(
			"yyyy-MM", Locale.getDefault());

	public static final SimpleDateFormat M_CHINESE = new SimpleDateFormat(
			"MM月", Locale.getDefault());

	public static final SimpleDateFormat D_ENGLISH = new SimpleDateFormat("dd",
			Locale.getDefault());

	public static final SimpleDateFormat DHMS_CHINESE = new SimpleDateFormat(
			"dd日HH点mm分ss秒", Locale.getDefault());

	public static String getCurDateString() {
		return YMDHMS_ENGLISH.format(new Date());
	}

	public enum DateCompareType {
		Month, Day, week, Year;
	}

	/**
	 * 用于出生日期计算， 返回数组为4，分别为年月日,总天数(包含当天)
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int[] diffDateYMD(Date startDay, Date endDay) {
		int years = diffDate(startDay, endDay, DateCompareType.Year);
		int months = 0;
		int days = 0;
		long lt = startDay.getTime() / 86400000;
		long ct = endDay.getTime() / 86400000;
		int wholeDays = (int) (ct - lt);

		Calendar c = Calendar.getInstance();
		c.setTime(startDay);
		c.add(Calendar.YEAR, years);

		months = diffDate(c.getTime(), endDay, DateCompareType.Month);
		c.add(Calendar.MONTH, months);

		days = diffDate(c.getTime(), endDay, DateCompareType.Day);

		int[] arr = { years, months, days, wholeDays };
		return arr;
	}

	/**
	 * 用于怀孕日期计算， 返回数组为4，分别为月日，周日，一个是按月计算，一个是按周计算，例如怀孕3个月3天，以及按周计算即13周1天
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int[] diffDateMWD(Date startDay, Date endDay) {
		int months = 0;
		int week = 0;
		int monthDays = 0;
		int weekDays = 0;

		Calendar c = Calendar.getInstance();
		c.setTime(startDay);

		months = diffDate(c.getTime(), endDay, DateCompareType.Month);
		c.add(Calendar.MONTH, months);

		monthDays = diffDate(c.getTime(), endDay, DateCompareType.Day);

		c.setTime(startDay);
		week = diffDate(c.getTime(), endDay, DateCompareType.week);
		c.add(Calendar.WEEK_OF_YEAR, week);

		weekDays = diffDate(c.getTime(), endDay, DateCompareType.Day);

		int[] arr = { months, monthDays, week, weekDays };
		return arr;
	}

	/**
	 * @param date1
	 *            需要比较的时间 小的时间
	 * @param date2
	 *            被比较的时间 大的时间，为空(null)则为当前时间
	 */
	public static int diffDate(Date startDay, Date endDay, DateCompareType stype) {
		// System.out.println("开始比较 start:" +
		// YMD_ENGLISH.format(startDay.getTime()) + "end:"
		// + YMD_ENGLISH.format(endDay.getTime()) + "类型:" + stype.toString());
		int n = 0;
		DateFormat df = stype == DateCompareType.Year ? YM_ENGLISH
				: YMD_ENGLISH;
		endDay = endDay == null ? new Date() : endDay;
		Calendar c1 = Calendar.getInstance(Locale.getDefault());
		Calendar c2 = Calendar.getInstance(Locale.getDefault());
		try {
			c1.setTime(df.parse(df.format(startDay)));
			c2.setTime(df.parse(df.format(endDay)));
		} catch (Exception e) {
		}
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// System.out.println("start:" + df.format(c1.getTime()));
			// System.out.println("end:" + df.format(c2.getTime()));
			n++;
			if (stype == DateCompareType.Month) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else if (stype == DateCompareType.Year) {
				c1.add(Calendar.YEAR, 1); // 比较年数，日期+1
			} else if (stype == DateCompareType.week) {
				c1.add(Calendar.WEEK_OF_YEAR, 1); // 比较年数，日期+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}

		n = n - 1;
		return n;
	}

	/**
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		// long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		// long seconds = (mss % (1000 * 60)) / 1000;
		// return days + " days " + hours + " hours " + minutes + " minutes "
		// + seconds + " seconds ";
		return days + " 天 " + hours + " 小时 ";
	}
}
