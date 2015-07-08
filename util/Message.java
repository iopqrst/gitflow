package com.bskcare.ch.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 从资源文件中读取数据
 * 该文件为系统中所放置的汉字文字
 * @author houzhiqing
 */
public class Message {

	private static final String BUNDLE_NAME = "message";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Message() {
	}

	/**
	 * get the value from the properties file
	 * 
	 * @param key
	 *            the key in the properties file
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "no " + key + " key!";
		}
	}
}
