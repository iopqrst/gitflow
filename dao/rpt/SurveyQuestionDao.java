package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.SurveyQuestionAnswerExtend;
import com.bskcare.ch.bo.SurveyQuestionExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SurveyQuestion;

public interface SurveyQuestionDao extends BaseDao<SurveyQuestion>{

	/**
	 * 根据问卷id查找问卷所有问题和对应的答案 
	 */
	public List<SurveyQuestionAnswerExtend> listBySid(Integer sid);
	
	/**
	 * 根据问卷编号查找相对应的问题 
	 */
	public List<SurveyQuestion> queryQid(Integer sid);
	/**
	 * 问卷问题全查
	 */
	public PageObject<SurveyQuestionExtend> queryList(QueryInfo queryInfo);
	/**
	 * 根据问卷编号查询数据集合
	 */
	public List<SurveyQuestionExtend> queryListBySid(Integer sid);
}
