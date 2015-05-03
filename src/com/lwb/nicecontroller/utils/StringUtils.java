package com.lwb.nicecontroller.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 字符串操作工具包
 */
public class StringUtils {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	// private final static SimpleDateFormat dateFormater = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// private final static SimpleDateFormat dateFormater2 = new
	// SimpleDateFormat("yyyy-MM-dd");

	private final static ThreadLocal<SimpleDateFormat> dateFormater_YMDHMS = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return DateUtils.YMDHMS_ENGLISH;
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater_YMD = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return DateUtils.YMD_ENGLISH;
		}
	};

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater_YMDHMS.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String friendlyChildAgeOld(Date birthday) {
		return friendlyChildAgeOld(birthday, new Date());
	}

	public static String friendlyChildAgeOld(Date birthday, Date compareDay) {
		int[] datas = DateUtils.diffDateYMD(birthday, compareDay);
		if (datas[3] == 0) {
			return "刚出生";
		} else if (datas[0] == 0 && datas[1] == 1 && datas[2] == 0) {
			return "满月了";
		} else if (datas[3] == 100) {
			return "满百天了";
		} else {
			StringBuilder sb = new StringBuilder();
			if (datas[0] != 0)
				sb.append(datas[0] + "岁");
			if (datas[1] != 0)
				sb.append(datas[1] + "月");
			if (datas[2] != 0)
				sb.append(datas[2] + "天");
			return sb.toString();
		}
	}

	public static String friendlyPregnantOld(Date compareDay, Date pregnantDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(pregnantDay);
		c.add(Calendar.DATE, -280);
		int[] datas = DateUtils.diffDateMWD(c.getTime(), compareDay);

		StringBuilder sb = new StringBuilder();
		if (datas[0] != 0)
			sb.append(datas[0] + "月");
		if (datas[1] != 0)
			sb.append(datas[1] + "天");
		if (datas[2] != 0)
			sb.append("(" + datas[2] + "周");
		if (datas[3] != 0)
			sb.append(datas[3] + "天)");
		return sb.toString();
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendlyPubTime(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater_YMD.get().format(cal.getTime());
		String paramDate = dateFormater_YMD.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater_YMD.get().format(time);
		}
		return ftime;
	}
	
	/**
	 * 以友好的方式显示时间
	 * @param date
	 * @return
	 */
	public static String friendlyPubTime(Date date) {
		SimpleDateFormat f = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return friendlyPubTime(f.format(date));
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater_YMD.get().format(today);
			String timeDate = dateFormater_YMD.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 判断手机格式是否正确
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {

		// Pattern p = Pattern
		//
		// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Pattern p = Pattern
				.compile("^((13[0-9])|(15[0-3,5-9])|(145)|(147)|(17[0678])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	/**
	 * 判断身份证格式
	 * 需要验证15位以及18位身份证
	 * @param id
	 * @return
	 */
	public static boolean isIDCard(String id) {
		Pattern patternSfzhm1 = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
		Pattern patternSfzhm2 = Pattern
				.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		Matcher matcherSfzhm1 = patternSfzhm1.matcher(id);
		Matcher matcherSfzhm2 = patternSfzhm2.matcher(id);

		return matcherSfzhm1.find() || matcherSfzhm2.find();

	}

	/**
	 * 判断邮编格式是否正确
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isZipNO(String zipString) {
		String str = "^[1-9][0-9]{5}$";
		return Pattern.compile(str).matcher(zipString).matches();
	}

	/**
	 * 不同于其他的转化，这个是将任意字符串，即非数字型字符串，转成唯一的int类型数据
	 * 
	 * @param str
	 * @return
	 */
	/*
	 * public static int string2IntMd5(String str) { String md5 =
	 * Md5Utils.md516One(str); return string2IntBase(md5); }
	 */

	public static int string2IntBase(String str) {
		char[] ch = str.toCharArray();
		int result = 0;
		for (int i = 0; i < ch.length; i++) {
			int tmp = (int) ch[i];
			result += (ch.length - i) * tmp;
		}

		return result;
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * <p>
	 * Capitalizes a String changing the first letter to title case as per
	 * {@link Character#toTitleCase(char)}. No other letters are changed.
	 * </p>
	 * 
	 * <p>
	 * For a word based algorithm, see
	 * {@link org.apache.commons.lang3.text.WordUtils#capitalize(String)}. A
	 * {@code null} input String returns {@code null}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.capitalize(null)  = null
	 * StringUtils.capitalize("")    = ""
	 * StringUtils.capitalize("cat") = "Cat"
	 * StringUtils.capitalize("cAt") = "CAt"
	 * </pre>
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return the capitalized String, {@code null} if null String input
	 * @see org.apache.commons.lang3.text.WordUtils#capitalize(String)
	 * @see #uncapitalize(String)
	 * @since 2.0
	 */
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	public static String stringToUnicode(String str) {
		if (isEmpty(str))
			return null;
		StringBuffer strb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (strb.length() == 0) {
				strb.append(Integer.toHexString(str.charAt(i)));
			} else {
				strb.append("-").append(Integer.toHexString(str.charAt(i)));
			}
		}
		return strb.toString();
	}

	public static String unicodeToString(String str) {
		if (isEmpty(str))
			return null;

		Pattern pattern = Pattern.compile("(\\-(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	public static String unicodeToString2(String str) {
		if (isEmpty(str))
			return null;
		String[] arr = str.split("-");
		StringBuilder sb = new StringBuilder();

		char ch;
		for (String s : arr) {
			ch = (char) Integer.parseInt(s, 16);
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 
	 * java字节码转字符串
	 * 
	 * @param b
	 * 
	 * @return
	 */

	public static String byte2hex(byte[] b) { // 一个字节的数，

		// 转成16进制字符串

		String hs = "";

		String tmp = "";

		for (int n = 0; n < b.length; n++) {

			// 整数转成十六进制表示

			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (tmp.length() == 1) {

				hs = hs + "0" + tmp;

			} else {

				hs = hs + tmp;

			}

		}

		tmp = null;

		return hs.toLowerCase(); // 转成小写

	}

	/**
	 * 提示输入内容超过规定长度
	 * 
	 * @param context
	 * @param editText
	 * @param max_length
	 * @param err_msg
	 */
	public static void lengthFilter(final Context context, EditText editText,
			final int max_length, final String err_msg) {

		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(max_length) {

			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				// TODO Auto-generated method stub
				// 获取字符个数(一个中文算2个字符)
				int destLen = StringUtils.getCharacterNum(dest.toString());
				int sourceLen = StringUtils.getCharacterNum(source.toString());
				if (destLen + sourceLen > max_length) {
					Toast.makeText(context, err_msg, Toast.LENGTH_SHORT).show();
					return "";
				}
				return source;
			}

		};
		editText.setFilters(filters);
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	public static int getCharacterNum(String content) {
		if (content.equals("") || null == content) {
			return 0;
		} else {
			return content.length() + getChineseNum(content);
		}

	}

	/**
	 * 计算字符串的中文长度
	 * 
	 * @param s
	 * @return
	 */
	public static int getChineseNum(String s) {
		int num = 0;
		char[] myChar = s.toCharArray();
		for (int i = 0; i < myChar.length; i++) {
			if ((char) (byte) myChar[i] != myChar[i]) {
				num++;
			}
		}
		return num;
	}

}
