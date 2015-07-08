package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.Question;


public interface QuestionService {

	/**
	 * 全查 
	 */
	public List<Question> findAll();
	/**
	 * 保存 
	 */
	public Question save(Question question);
	/**
	 * 分页
	 */
	public PageObject<Question> queryList(Question question,QueryInfo queryInfo);
	/**
	 * 删除
	 */
	public void delete(Integer id);
	/**
	 * 查询单个对象
	 */
	public Question findById(Integer id);
	/**
	 * 修改
	 */
	public void updateQuestion(Question question);
}
