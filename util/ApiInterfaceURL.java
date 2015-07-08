package com.bskcare.ch.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 对外接口对应url
 * 
 * @author houzhiqing
 */
public class ApiInterfaceURL {

	private static final String BUNDLE_NAME = "api_interface_url";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private ApiInterfaceURL() {
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
