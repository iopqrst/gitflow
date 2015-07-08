package com.bskcare.ch.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 从资源文件中读取数据
 * @author houzhiqing
 */
public class FunctionCfg {

	private static final String BUNDLE_NAME = "function_cfg";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private FunctionCfg() {
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
	
	public static void main(String[] args) {
		getString("card_list.jsp_generate_code");
	}
}
