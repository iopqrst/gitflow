package com.bskcare.ch.util;

import java.text.MessageFormat;

public class LogFormat {
	
	private static final String PREFIX_LOG = ">>>>>>>>>>>>>>>>>>>> {0}";
	private static final String BOTH_LOG = ">>>>>>>>>>>>>>>>>>>> {0} <<<<<<<<<<<<<<<<<<<";

	/**
	 * 输出格式为 >>>>>>>>>>>>>>>>>>>> xxxxxxxxxxxxxxxx
	 */
	public static String f(String msg) {
		if(null == msg || StringUtils.isEmpty(msg)) return PREFIX_LOG;
		return  MessageFormat.format(PREFIX_LOG, msg);
	}
	
	/**
	 * 输出格式为 >>>>>>>>>>>>>>>>>>>> xxxxxxxxxxxxxxxx <<<<<<<<<<<<<<<<<<<
	 */
	public static String b(String msg) {
		if(null == msg || StringUtils.isEmpty(msg)) return BOTH_LOG;
		return  MessageFormat.format(BOTH_LOG, msg);
	}
	
	public static String no(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("+++     ++      +++");
		sb.append("\n");
		sb.append("++ ++   ++   ++     ++");
		sb.append("\n");
		sb.append("++  ++  ++   ++     ++ " + "======> " + msg);
		sb.append("\n");
		sb.append("++   ++ ++   ++     ++");
		sb.append("\n");
		sb.append("++    ++++      +++");
		sb.append("\n");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(LogFormat.no("呃。。。"));
	}
}
