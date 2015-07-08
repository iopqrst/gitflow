package com.bskcare.ch.service.timeLine;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.EvaluatingResult;
import com.bskcare.ch.vo.timeLine.TimeLineRule;

public interface TimeLineRuleService {
	/**
	 * 根据条件查询规则列表
	 */
	public List<TimeLineRule> queryList(TimeLineRule rule);

	/**
	 * 分页规则列表
	 * 
	 * @param rule
	 * @param info
	 * @return
	 */
	public PageObject<TimeLineRule> queryList(TimeLineRule rule, QueryInfo info);

	/**
	 * 修改或保存
	 * 
	 * @param rule
	 */
	public void saveOrUpdate(TimeLineRule rule);

	/**
	 * 删除规则
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 自动产生时间轴
	 * 
	 * @throws Exception
	 */
	public void automaticallyAssignTimeLine(Integer soft);

	/**
	 * 为某个客户生成添加时间轴 用与用户上传评测结果后直接生成第二天时间轴
	 * 
	 * 针对当个用户的评论
	 * 
	 * @param soft
	 *            软件
	 * @param evaluatingResult
	 *            评测结果
	 * @param type 1,代表升级vip重新生成，不会生产报告
	 */
	public void addTaskByEvaluating(Integer soft,
			EvaluatingResult evaluatingResult,Integer type);

	/**
	 * 获得规则
	 * 
	 * @param id
	 */
	public TimeLineRule getLineRuleById(Integer id);

	/**
	 * 为某个客户从新生成时间轴
	 * 	会先删除用户所有的除服药提醒，用户自己添加，医生添加的任务外的所有任务。
	 * 然后遍历时间轴规则添加新的时间轴任务。
	 * 主要用于    用户升级vip，用户上传新的评测结果。
	 * @param cid 用户id
	 * @param softType  软件类型
	 * @param type 1,代表升级vip重新生成，不会生产报告，
	 */
	public void againFound(Integer cid, Integer softType,Integer type);

	public List<TimeLineRule> queryList(Integer cid, Integer softType);
}
