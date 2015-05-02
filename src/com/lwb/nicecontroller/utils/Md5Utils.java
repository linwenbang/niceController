package com.lwb.nicecontroller.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取Md5工具类
 * 
 * @author csf
 * 
 */
public class Md5Utils {
//
//	/**
//	 * 获取文件Md5
//	 * @param file
//	 * @return
//	 * @throws FileNotFoundException
//	 */
//	public static String getMd5ByFile(File file)  {
//		String value = null;
//		FileInputStream in = null;
//		try {
//			in = new FileInputStream(file);
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			MappedByteBuffer byteBuffer = in.getChannel().map(
//					FileChannel.MapMode.READ_ONLY, 0, file.length());
//			MessageDigest md5 = MessageDigest.getInstance("MD5");
//			md5.update(byteBuffer);
//			BigInteger bi = new BigInteger(1, md5.digest());
//			value = bi.toString(16);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != in) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return value;
//	}
	
	/**
	 * 默认的密码字符串组合，apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
//	public static void main(String[] args) throws IOException {
//		long begin = System.currentTimeMillis();
//
//		File big = new File("F:/音乐/1-10月胎教音乐/孕1月胎教音乐/《春日百花香》平缓放松.mp3");
//
//		String md5 = getFileMD5String(big);
//
//		long end = System.currentTimeMillis();
//		System.out.println("md5:" + md5 + " time:" + ((end - begin) / 1000)
//				+ "s");
//	}

	/**
	 * 适用于上G大的文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file)  {
		FileInputStream in = null;
		try {
			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileChannel ch = in.getChannel();
			MappedByteBuffer byteBuffer = null;
			try {
				byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,
						0, file.length());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(byteBuffer);
			return bufferToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException nsaex) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(bytes);
			return bufferToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException nsaex) {
			return null;
		}
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

}
