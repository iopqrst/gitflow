package com.bskcare.ch.service.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.HealthReportDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.HealthReportService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.HealthReport;

@Service
@SuppressWarnings("unchecked")
public class HealthReportServiceImpl implements HealthReportService{

	@Autowired
	private HealthReportDao healthReportDao;
	
	public PageObject findHealthReport(HealthReport healthReport , QueryInfo info){
		return healthReportDao.findHealthReport(healthReport, info);
	}
	
	public String findHealthReportJson(HealthReport healthReport , QueryInfo info){
		PageObject list = healthReportDao.findHealthReport(healthReport, info);
		JSONObject jo = new JSONObject();
		jo.put("total",list.getTotalRecord()); 
		jo.put("list", JsonUtils.getJsonString4JavaListDate(list.getDatas()));
		
		return jo.toString();
	}
	
	
	public void uploadHealthReport(HealthReport healthReport){
		healthReportDao.add(healthReport);
	}
	
	public void deleteHealthReport(Integer id){
		healthReportDao.deleteHealthReport(id);
	}
	
	public HealthReport findHealReportById(Integer id){
		return healthReportDao.load(id);
	}
}
