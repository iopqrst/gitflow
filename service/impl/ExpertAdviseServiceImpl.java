package com.bskcare.ch.service.impl;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ExpertAdviseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ExpertAdviseService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.ExpertAdvise;

@Service
@SuppressWarnings("unchecked")
public class ExpertAdviseServiceImpl implements ExpertAdviseService{
	@Autowired
	private	ExpertAdviseDao expertAdviseDao ;
	
	public String getExpertAdvise(ExpertAdvise expertAdvise, QueryInfo queryInfo) {
		PageObject page = expertAdviseDao.getExpertAdvise(expertAdvise, queryInfo) ;
		JSONObject jo = new JSONObject();		
		jo.put("total",page.getTotalRecord()); 
		jo.put("list", JsonUtils.getJsonString4JavaListDate(page.getDatas()));		
		
		return jo.toString();
	}

	public void addExpertAdvise(ExpertAdvise expertAdvise, Integer userId) {
		// TODO Auto-generated method stub
		expertAdvise.setUserId(userId);
		expertAdvise.setDateTime(new Date());
		expertAdviseDao.addExpertAdvise(expertAdvise);
	}

	
}
