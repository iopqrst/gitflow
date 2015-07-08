package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.bo.SurveyQuestionAnswerExtend;
import com.bskcare.ch.bo.SurveyQuestionExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SurveyQuestion;

public interface SurveyQuestionService {

	public List<SurveyQuestionAnswerExtend> listBySid(Integer sid);
	
	/**
	 * 获取当前最新的问卷,并得到问卷上所有的问题和答案,根据答案找到相对应的答案提示
	 * 全部返回成一个字符串
	 */
	public String saveAnswerTipMsg();
	
	public SurveyQuestion save(SurveyQuestion surveyQuestion);
	/**
	 * 问卷问题全查
	 */
	public PageObject<SurveyQuestionExtend> queryList(QueryInfo queryInfo);
	/**
	 * 删除有问卷编号sid的数据
	 */
	public void deleteById(Integer sid);
	/**
	 * 根据问卷编号sid查询集合
	 */
	public List<SurveyQuestionExtend> queryListBySid(Integer sid);
}
