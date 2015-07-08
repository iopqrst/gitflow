package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.SurveyAnswer;

public interface SurveyAnswerDao extends BaseDao<SurveyAnswer>{

	/**
	 * 根据问题编号查找答案集合
	 */
	public List<SurveyAnswer> queryAid(Integer sid);
	/**
	 * 根据问卷编号和用户编号查询问卷答案表单个对象
	 */
	public List<SurveyAnswer> findSurveyAnswerBySid(Integer sid, Integer clientId);
	
}
