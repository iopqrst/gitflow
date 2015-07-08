package com.bskcare.ch.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author jason
 * 
 */
public class MD5Util {

	static MessageDigest md;
	static {
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (Exception ex) {
		}
	}

	/**
	 * 加密方法
	 * 
	 * @param msg
	 * @return
	 */
	public static String digest(String msg) {
		byte[] rlt = md.digest(msg.getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rlt.length; i++) {
			String d = "00" + Integer.toHexString(rlt[i]);
			sb.append(d.substring(d.length() - 2));
		}
		return sb.toString();
	}

	/**
	 * 32位加密
	 * 
	 * @param str
	 * @return
	 */
	public static String digest32(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	public static void main(String[] args) {
		String s = "{\"pid\":3,\"type\":0,\"payment\":0,\"payMethod\":0,\"methodKey\":\"XTGG\"}";
		System.out.println(digest("0236524"));
	}
}
