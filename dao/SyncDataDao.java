package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.SyncData;

public interface SyncDataDao extends BaseDao<SyncData>{
	
	/**
	 * 根据类型type 和数据id查询是否有记录
	 */
	public SyncData querySyncByTypeAndDataId(SyncData syncData);
	
	/**
	 * 查询未同步的客户数据
	 */
	public List<SyncData> queryNotSyncData();
		
}
