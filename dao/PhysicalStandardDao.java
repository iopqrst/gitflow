package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.PhysicalStandard;

public interface PhysicalStandardDao extends BaseDao<PhysicalStandard>{
		
	/**
	 * 根据pdId查询某个指标标准
	 */
	
	public List<PhysicalStandard> findStandardBypdId(Integer pdId);
	
	/**
	 * 根据pdId查询此标准最大的值
	 */
	public double findMaxStandard(Integer pdId);
	
}
