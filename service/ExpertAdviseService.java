package com.bskcare.ch.service;

import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ExpertAdvise;

@SuppressWarnings("unchecked")
public interface ExpertAdviseService {
	/**
	 * 得到专家建议列表
	 * @param expertAdvise
	 * @param queryInfo 
	 * @return
	 */
	String getExpertAdvise(ExpertAdvise expertAdvise, QueryInfo queryInfo);

	void addExpertAdvise(ExpertAdvise expertAdvise, Integer userId);

}
