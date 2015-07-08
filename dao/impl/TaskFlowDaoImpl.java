package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.TaskFlowDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.TaskFlow;
@Repository("taskFlowDao")
public class TaskFlowDaoImpl extends BaseDaoImpl<TaskFlow> implements TaskFlowDao {
	/**
	 * 添加任务流程
	 */
	public void addFlow(TaskFlow taskFlow) {
		this.add(taskFlow);
	}
	/**
	 * 获得任务流程列表  分页
	 */
	public PageObject<TaskFlow> getTaskFlowList(QueryInfo info) {
		return this.queryPagerObjects("from TaskFlow",null,  info);
	}
	/**
	 * 删除任务流程
	 */
	public void deleteFlow(int id) {
		this.delete(id);
	}
	/**
	 * 获得任务流程列表 （自动分配任务使用）
	 */
	public List<TaskFlow> getTaskFlowList() {
		return executeFind("from TaskFlow");
	}

}
