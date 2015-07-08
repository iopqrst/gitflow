package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.bo.TaskListPage;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.TaskList;

@SuppressWarnings("unchecked")
public interface TaskListDao {
	/**
	 * 添加任务
	 * 
	 * @param task
	 */
	void addTask(TaskList task);

	/**
	 * 更改任务
	 * 
	 * @param task
	 */
	void updateTask(TaskList task);

	/**
	 * 获得我的任务列表
	 * 
	 * @param task
	 *            任务条件
	 * @param info
	 * @param status
	 *            状态（1、未完成任务）
	 * @param types
	 *            任务类型筛选
	 * @return
	 */
	PageObject<TaskListPage> myTaskList(TaskList task, QueryInfo queryInfo, int status,
			List<String> types, ClientInfo clientInfo,QueryCondition condition);

	/**
	 * 根据角色id获得定时任务列表
	 * 
	 * @param userId
	 *            角色id
	 * @param info
	 * @return
	 */
	PageObject newTaskList(int userId, QueryInfo info);

	/**
	 * 
	 * @param id
	 * @param info
	 * @return
	 */
	PageObject getTaskListById(int id, QueryInfo info);

	TaskList getTask(int taskid);

	public int releaseSelfTask();

	/**
	 * 删除任务
	 */
	void deleteTask(int id);
	
	public void updateTaskById(Integer taskId);
	
	public int myTaskList(TaskList task, int status, List<String> types,
			ClientInfo clientInfo, QueryCondition condition);
}
