package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.TaskListPage;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.TaskFlow;
import com.bskcare.ch.vo.TaskList;
import com.bskcare.ch.vo.rpt.RptBaseInfo;

@SuppressWarnings("unchecked")
public interface TaskListService {
	void addTask(TaskList task);

	PageObject<TaskListPage> myTaskList(TaskList task, QueryInfo info, List<String> types,
			ClientInfo clientInfo,QueryCondition condition);

	PageObject<TaskListPage> myTaskListhistory(TaskList task, QueryInfo info,
			List<String> types,ClientInfo clientInfo,QueryCondition condition);

	PageObject newTaskList(int clientId, QueryInfo info);

	void addFlow(TaskFlow taskFlow);

	void updatask(TaskList task);

	TaskList getTaskById(int taskList);

	PageObject getTaskListById(int id, QueryInfo info);

	PageObject getTaskFlowList(QueryInfo info);

	void deleteTask(int id);

	void deleteFlow(int id);

	/**
	 * 自动任务系统
	 */
	void automaticallyAssignTasks();

	void levelUp(int areaId, int clientId);

	public String queryBacklog(String menus, String areaChain, Integer userId);
	
	public RptBaseInfo dealRptTask(Integer clientId, Integer taskId);
	/**
	 * 体验用户注册提醒
	 * @param areaId
	 * @param clientId
	 */
	void registerRemind(int areaId, int clientId);
}
