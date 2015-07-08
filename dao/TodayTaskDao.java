package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.TodayTask;

public interface TodayTaskDao extends BaseDao<TodayTask> {

	/**
	 * 前台查询当前用户的今日任务和共同任务并分页
	 */
	public PageObject<TodayTask> findCp(Integer clientId, QueryInfo queryInfo);

	/**
	 * 后台查询当前用户的今日任务并分页
	 */
	public PageObject<TodayTask> findMp(Integer clientId, QueryInfo queryInfo);
}
