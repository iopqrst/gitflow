package com.bskcare.ch.service;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.HealthReport;

@SuppressWarnings("unchecked")
public interface HealthReportService {
	
	/**
	 * 查询所有的健康报告
	 */
	public PageObject findHealthReport(HealthReport healthReport , QueryInfo info);
	
	public String findHealthReportJson(HealthReport healthReport , QueryInfo info);
	
	/**
	 * 上传健康报告
	 */
	public void uploadHealthReport(HealthReport healthReport);
	
	
	/**
	 * 根据用户id和健康报告id删除上传的健康报告
	 */
	public void deleteHealthReport(Integer id);
	
	/**
	 * 根据健康报告id查询健康报告信息
	 */
	public HealthReport findHealReportById(Integer id);
}
