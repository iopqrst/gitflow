package com.bskcare.ch.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CrmURLConfig {

	private static final String BUNDLE_NAME = "crm_url_impl";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private CrmURLConfig() {
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
