package com.bskcare.ch.dao;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ExpertAdvise;

public interface ExpertAdviseDao {
	/**
	 * 获得专家建议
	 * @param expertAdvise
	 * @return
	 */
	public PageObject getExpertAdvise(ExpertAdvise expertAdvise,QueryInfo queryInfo) ;
	/***
	 * 最新专家建议
	 * @param expertAdvise
	 * @return
	 */
	public ExpertAdvise getLastExpertAdvise(ExpertAdvise expertAdvise) ;
	public void addExpertAdvise(ExpertAdvise expertAdvise);
}
