package com.bskcare.ch.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.SyncDataDao;
import com.bskcare.ch.service.CrmClientInfoService;
import com.bskcare.ch.service.SyncDataService;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.SyncData;

@Service
public class SyncDataServiceImpl implements SyncDataService{
	
	@Autowired
	private CrmClientInfoService crmClientService;
	
	@Autowired
	private ClientInfoDao clientInfoDao;
	
	@Autowired
	private SyncDataDao syncDataDao;
	
	public void updataSyncData(){
		List<SyncData> lstSyncData = syncDataDao.queryNotSyncData();
		
		if(!CollectionUtils.isEmpty(lstSyncData)){
			for (SyncData syncData : lstSyncData) {
				if(syncData != null && syncData.getDealType() != null){
					if(syncData.getType().equals(SyncData.TYPE_CLIENTINFO)){
						ClientInfo clientInfo = clientInfoDao.load(syncData.getDataId());
						if(clientInfo != null){
							if(syncData.getDealType().equals(SyncData.DEAL_TYPE_UPDATE)){
								crmClientService.updateCrmClientInfo(clientInfo,"Timer");
							}else{
								crmClientService.addCrmClientInfo(clientInfo);
							}
						}
					}
				}
			}
		}
	}
}
