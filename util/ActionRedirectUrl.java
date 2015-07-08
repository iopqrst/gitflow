package com.bskcare.ch.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 从资源文件中读取数据
 * @author houzhiqing
 */
public class ActionRedirectUrl {

	private static final String BUNDLE_NAME = "action_redirect_url";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private ActionRedirectUrl() {
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
