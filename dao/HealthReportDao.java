package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.HealthReport;

@SuppressWarnings("unchecked")
public interface HealthReportDao extends BaseDao<HealthReport>{
	
	/**
	 * 查询所有的健康报告
	 */
	public PageObject findHealthReport(HealthReport healthReport , QueryInfo info);
	
	/**
	 * 根据用户id和健康报告id删除上传的健康报告
	 */
	public void deleteHealthReport(Integer id);
	
}
