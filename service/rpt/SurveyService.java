package com.bskcare.ch.service.rpt;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.Survey;

public interface SurveyService {

	public Survey save(Survey survey);
	public Survey findById(Integer sid);
	/**
	 * 问卷全查
	 */
	public PageObject<Survey> queryList(QueryInfo queryInfo);
	/**
	 * 修改状态
	 */
	public void updateStatus(Survey survey);
	/**
	 * 删除
	 */
	public void deleteById(Integer sid);
}
