package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.bo.ManageLogExtend;
import com.bskcare.ch.vo.ManageLog;

@SuppressWarnings("unchecked")
public interface ManageLogDao {
	public void addLog(ManageLog mlog);
	
	/**
	 * 根据用户id查询日志记录
	 */
	public List queryLogByClientId(Integer clientId);
	
	/**
	 * 查询日志的信息
	 */
	public List<ManageLogExtend> queryManageLog(ManageLog log);
}
