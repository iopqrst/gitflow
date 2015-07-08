package com.bskcare.ch.service;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientWeight;

@SuppressWarnings("unchecked")
public interface ClientWeightService {
	
	/**添加体重信息*/
	public String addClientWeight(ClientWeight weight);
	
	
	/**
	 * 获取客户的体重信息
	 * @param  month：0：表示查询90天数据   3：表示查询3个月的数据
	 */
	public PageObject queryWeightByClientId(Integer clientId, int month, QueryInfo queryInfo, String uploadDate);
}
