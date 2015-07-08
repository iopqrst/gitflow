package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.FollowUpVisits;
import com.bskcare.ch.vo.UserInfo;

@SuppressWarnings("unchecked")
public interface FollowUpVisitsService {
	/***
	 * 增加随访
	 * 
	 * @param followUpVisits
	 * @param userInfo
	 */
	FollowUpVisits addFollowUpVisits(FollowUpVisits followUpVisits,
			UserInfo userInfo);

	void addFollowUp(FollowUpVisits followUpVisits);

	/**
	 * 得到随访列表JSON 数据
	 * 
	 * @param followUpVisits
	 * @return
	 */
	String getFollowUpVisitsList(FollowUpVisits followUpVisits,
			QueryInfo queryInfo);

	/**
	 * 得到随访任务列表
	 * 
	 * @param followUpVisits
	 * @param queryInfo
	 * @return
	 */
	PageObject myFollowUpVisitsList(FollowUpVisits followUpVisits,
			QueryInfo queryInfo);

	/***
	 * 修改随访
	 * 
	 * @param followUpVisits
	 * @param userInfo
	 */
	void updateVisits(FollowUpVisits followUpVisits, UserInfo userInfo);

	String getFollowUpVisitsListFacade(FollowUpVisits followUpVisits,
			QueryInfo queryInfo);

	/**
	 * 根据用户编号查询随访记录
	 */
	PageObject queryFollowList(FollowUpVisits follow, QueryInfo queryInfo,QueryCondition condition);

	/**
	 * 根据任务编号查询随访记录
	 * 
	 * @param tlid
	 * @return
	 */
	PageObject getFollowUpVisitsByTaskId(int tlid, QueryInfo queryInfo);

	List queryFollowUpList(Integer clientId, FollowUpVisits follow);
	/**
	 * 根据用户id获得随访用户列表（已随访）
	 */
	String getFollowUpUserByClientId(Integer clientId);
	
	public void deleteFollowUp(FollowUpVisits followUp);
	/**
	 * 根据随访id获得随访
	 */
	FollowUpVisits getFollowById(Integer id);
	
	public String queryFollowUp(FollowUpVisits follow);
}
