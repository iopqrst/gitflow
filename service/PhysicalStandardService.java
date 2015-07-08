package com.bskcare.ch.service;


public interface PhysicalStandardService {
	
	/**
	 * 根据pdId查询某个指标标准
	 */
	
	public String findStandardBypdId(Integer pdId);
}
