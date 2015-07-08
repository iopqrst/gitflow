package com.bskcare.ch.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 系统配置
 * @author houzhiqing
 */
public class SystemConfig {

	private static final String BUNDLE_NAME = "system_config";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private SystemConfig() {
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
