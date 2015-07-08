package com.bskcare.ch.service;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.TodayTask;

public interface TodayTaskService {
	/**
	 * 增加
	 */
	public TodayTask add(TodayTask task);

	/**
	 * 删除
	 */
	public void delete(TodayTask task);

	/**
	 * 修改
	 */
	public void update(TodayTask task);

	/**
	 * 单个对象
	 */
	public TodayTask findById(Integer id);

	/**
	 * 前台查询当前用户的今日任务和共同任务并分页
	 */
	public PageObject<TodayTask> findCp(Integer clientId, QueryInfo queryInfo);

	/**
	 * 后台查询当前用户的今日任务并分页
	 */
	public PageObject<TodayTask> findMp(Integer clientId, QueryInfo queryInfo);
}
