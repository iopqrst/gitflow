package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.vo.rpt.SurveyAnswer;

public interface SurveyAnswerService {
		
	public SurveyAnswer save(SurveyAnswer surveyAnswer);
	/**
	 * 查询单个对象
	 */
	public List<SurveyAnswer> findSurveyAnswerBySid(Integer sid,Integer clientId);
}
