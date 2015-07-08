package com.bskcare.ch.service.impl.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.SurveyDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.SurveyService;
import com.bskcare.ch.vo.rpt.Survey;
@Service
public class SurveyServiceImpl implements SurveyService{

	@Autowired
	SurveyDao surveyDao; 
	
	public Survey save(Survey survey) {
		return surveyDao.add(survey);
	}
	
	public Survey findById(Integer sid) {
		return surveyDao.load(sid);
	}

	public PageObject<Survey> queryList(QueryInfo queryInfo) {
		return surveyDao.queryList(queryInfo);
	}

	public void updateStatus(Survey survey) {
		surveyDao.update(survey);
	}

	public void deleteById(Integer sid) {
		surveyDao.delete(sid);		
	}

}
