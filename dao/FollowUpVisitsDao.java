package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.FollowUpVisits;
import com.bskcare.ch.vo.UserInfo;

@SuppressWarnings("unchecked")
public interface FollowUpVisitsDao {
	/**
	 * 增加 随访
	 * 
	 * @param followUpVisits
	 * @param userInfo
	 */
	FollowUpVisits addFollowUpVisits(FollowUpVisits followUpVisits,
			UserInfo userInfo);

	void addFollowUp(FollowUpVisits followUpVisits);

	/**
	 * 得到随访列表
	 * 
	 * @param followUpVisits
	 * @return
	 */
	PageObject getFollowUpVisitsList(FollowUpVisits followUpVisits,
			QueryInfo queryInfo);

	/**
	 * 更新随访
	 * 
	 * @param followUpVisits
	 * @param userInfo
	 */
	void updateVisits(FollowUpVisits followUpVisits, UserInfo userInfo);

	void updateVisits(FollowUpVisits followUpVisits);

	/***
	 * 根据状态 求出最后 status[0] = 0 ,status[1] = 1 ;
	 * 
	 * @param followUpVisits
	 * @param status
	 * @return
	 */
	FollowUpVisits getLastFollowUpVisitsByStatus(FollowUpVisits followUpVisits,
			String[] status);

	/**
	 * 根据用户编号查询随访记录
	 */
	PageObject queryFollowList(FollowUpVisits follow, QueryInfo queryInfo,QueryCondition condition);

	/**
	 * 根据任务id获得随访列表
	 */
	PageObject getFollowUpVisitsByTaskId(int taskid , QueryInfo queryInfo);

	List queryFollowUpList(Integer clientId, FollowUpVisits follow);
	/**
	 * 根据用户id获得随访用户列表（已随访）
	 */
	PageObject getFollowUpUserByClientId(Integer clientId);
	/**
	 * 根据id删除随访信息
	 */
	public void deleteFollowUp(Integer id);
	/**
	 * 根据id获得随访
	 */
	FollowUpVisits getFollowById(Integer id);
	
	public List<FollowUpVisits> queryFollowUp(FollowUpVisits follow);
}
