package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.SyncDataDao;
import com.bskcare.ch.vo.SyncData;


@Repository
@SuppressWarnings("unchecked")
public class SyncDaoImpl extends BaseDaoImpl<SyncData> implements SyncDataDao{
	
	public SyncData querySyncByTypeAndDataId(SyncData syncData){
		List args = new ArrayList();
		String hql = "from SyncData where 1=1";
		if(syncData != null){
			if(syncData.getType() != null){
				hql += " and type= ?";
				args.add(syncData.getType());
			}
			if(syncData.getDataId() != null){
				hql += " and dataId = ?";
				args.add(syncData.getDataId());
			}
			if(syncData.getDealType() != null){
				hql += " and dealType = ?";
				args.add(syncData.getDealType());
			}
		}
		
		List<SyncData> lst =  executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}else{
			return null;
		}
	}
	
	
	public List<SyncData> queryNotSyncData(){
		String hql = "from SyncData where status = ?";
		return executeFind(hql, SyncData.STATUS_NOT_SYNC);
	}
}
