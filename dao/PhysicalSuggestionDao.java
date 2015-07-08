package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.PhysicalSuggestion;

public interface PhysicalSuggestionDao extends BaseDao<PhysicalSuggestion>{
	
	/**
	 * 根据用户id和指标id查询具体的建议信息
	 */
	public PhysicalSuggestion findSuggestionById(Integer clientId,Integer pdId);
	
	/**
	 * 根据用户id查询用户拥有的所有的体检建议
	 */
	public List<PhysicalSuggestion> findSuggestionBycId(Integer clientId);
	
}
