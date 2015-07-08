package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.Survey;

public interface SurveyDao extends BaseDao<Survey>{

	/**
	 * 查询问卷编号最大问卷
	 */
	public List<Survey> querySurveylist(Survey survey);
	/**
	 * 问卷全查
	 */
	public PageObject<Survey> queryList(QueryInfo queryInfo);
}
