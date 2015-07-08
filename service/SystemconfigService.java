package com.bskcare.ch.service;


public interface SystemconfigService {

	
	/**
	 * 查询key对应的value值
	 */
	public String getValue(String key);
	/**
	 * 初始化
	 */
	public void initMethod();
	public void getSystemConfig();
}
