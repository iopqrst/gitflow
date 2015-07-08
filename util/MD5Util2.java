package com.bskcare.ch.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 
 * 经该类加密的字符串与php程序加密的相同
 */
public class MD5Util2 {

	public static String getMD5(String str) throws Exception {
		/** 创建MD5加密对象 */
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		/** 进行加密 */
		md5.update(str.getBytes());
		/** 获取加密后的字节数组 */
		byte[] md5Bytes = md5.digest();
		String res = "";
		for (int i = 0; i < md5Bytes.length; i++) {
			int temp = md5Bytes[i] & 0xFF;
			if (temp <= 0XF) { // 转化成十六进制不够两位，前面加零
				res += "0";
			}
			res += Integer.toHexString(temp);
		}
		return res;
	}
	
	public static void main(String[] args) {
		String a = "params={\"truename\":\"houzhiqing\",\"age\":\"30\",\"mobile\":\"\"}";
		String b = "params={\"truename\":\"\",\"age\":\"\",\"mobile\":\"\"}";
		String c = "params={\"truename\":\"侯志清\",\"age\":30,\"mobile\":\"\"}&stoken=123412343124124124&sign=336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210aparams={\"truename\":\"侯志清\",\"age\":30,\"mobile\":\"\"}&stoken=123412343124124124&sign=336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210aparams={\"truename\":\"侯志清\",\"age\":30,\"mobile\":\"\"}&stoken=123412343124124124&sign=336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210aparams={\"truename\":\"侯志清\",\"age\":30,\"mobile\":\"\"}&stoken=123412343124124124&sign=336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210aparams={\"truename\":\"侯志清\",\"age\":30,\"mobile\":\"\"}&stoken=123412343124124124&sign=336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210aparams={\"truename\":\"侯志清\",\"age\":30,\"mobile\":\"\"}&stoken=123412343124124124&sign=336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210aparams={\"truename\":\"侯志清\",\"age\":30,\"mobile\":\"\"}&stoken=123412343124124124&sign=336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a336c0f41-35f1-495e-b706-ccba63ba210a";
		try {
			System.out.println(getMD5(a) + "\n");
			System.out.println(getMD5(b) + "\n");
			System.out.println(getMD5(c) + "\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
