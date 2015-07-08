package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.TaskFlow;

public interface TaskFlowDao {
	/**
	 * 添加任务流程
	 * @param taskFlow
	 */
	 void addFlow(TaskFlow taskFlow);
	 /**
	  * 获得流程列表 （分页）
	  * @param info
	  * @return
	  */
	 PageObject<TaskFlow> getTaskFlowList(QueryInfo info);
	 /**
	  * 获得流程列表（自动分配任务使用）
	  * @return
	  */
	 List<TaskFlow> getTaskFlowList();
	 /**
	  * 删除流程
	  * @param id 任务流程
	  */
	 void deleteFlow(int id);
	 
}
