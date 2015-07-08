package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.SurveyAnswerDao;
import com.bskcare.ch.service.rpt.SurveyAnswerService;
import com.bskcare.ch.vo.rpt.SurveyAnswer;

@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

	@Autowired
	SurveyAnswerDao surveyAnswerDao;

	public SurveyAnswer save(SurveyAnswer surveyAnswer) {
		return surveyAnswerDao.add(surveyAnswer);
	}

	public List<SurveyAnswer> findSurveyAnswerBySid(Integer sid,Integer clientId) {
		return surveyAnswerDao.findSurveyAnswerBySid(sid,clientId);
	}

}
