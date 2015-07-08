package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.client.PhysicalDetail;
import com.bskcare.ch.vo.client.PhysicalItem;

public interface PhysicalItemService{
	
	public List<PhysicalDetail> findDistinctPdId(Integer clientId);
	
	/**
	 * 根据用户id和指标id查询某个指标
	 */
	public String findPhysicalItemByid(Integer clientId,Integer pdId);
	
	public String getAnalysisInfo(Integer clientId);
	
	public List<PhysicalItem> findPhysicalItemByids(Integer clientId,Integer pdId);
	
	/**
	 * 查询显示最大值
	 */
	public double findMaxVlaue(Integer clientId,Integer pdId);
	
	/**
	 * 查询个人数据分析
	 */
	public String findAnalysisSuggestion(Integer clientId);

	public String getAnalysisCollection(int gendar, Integer clientId);
	
	/**
	 * 根据用户id和指标id查询某个指标
	 */
	public List findPhysicalItemBycIdpId(Integer clientId,Integer physicalId);
	
}
