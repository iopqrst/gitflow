package com.bskcare.ch.util;

import java.text.DecimalFormat;

/**
 * 格式化money
 * 
 * @author houzhiqing
 * 
 */
public class MoneyFormatUtil {

	/**
	 * ###,##0.00
	 */
	public final static DecimalFormat nf = new DecimalFormat("###,##0.00");
	
	/**
	 * ##0.00
	 */
	public final static DecimalFormat nf2 = new DecimalFormat("##0.00");

	public static String formatToMoney(String s) {
		if (s == null || s.equals("")) {
			return "0.00";
		}
		try {
			return formatToMoney(Double.parseDouble(s), null);
		} catch (Exception e) {
			return s;
		}
	}

	/**
	 * 格式换double
	 * @param d
	 * @param df 传入格式化样式，如果null 则按照##0.00展示
	 * @return
	 */
	public static String formatToMoney(double d, DecimalFormat df) {
		String outPutFormat = null;
		try {
			if (null != df) {
				outPutFormat = df.format(d);
			}else {
				outPutFormat = nf2.format(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
			outPutFormat = String.valueOf(d);
		}
		return outPutFormat;
	}
	
	/**
	 * 保留两位小树
	 */
	public static double formatDouble(double d) {
		return Double.parseDouble(formatToMoney(d,null));
	}

	public static void main(String[] args) {
		String str = formatToMoney("12345678990.12343");
		String dou = formatToMoney("12345678990.12343");
		Double dd = Double.parseDouble(formatToMoney(123456.23134,null));
		System.out.println(dd);
		
		Double d = Double.parseDouble(dou);
		System.out.println(d);
		System.out.println(str);
		System.out.println(dou);
		
	}
}
