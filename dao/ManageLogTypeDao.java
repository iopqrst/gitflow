package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.ManageLogType;

public interface ManageLogTypeDao extends BaseDao<ManageLogType>{

	/**
	 * 查询所有的类型
	 */
	public List<ManageLogType> queryManageLogType();
	
	/**
	 * 根据用户id查询用户所用的日志类型
	 */
	public List<ManageLogType> queryLogTypeByClientId(Integer clientId);
}
