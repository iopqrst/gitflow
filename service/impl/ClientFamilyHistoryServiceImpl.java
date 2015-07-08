package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientFamilyHistoryDao;
import com.bskcare.ch.service.ClientFamilyHistoryService;
import com.bskcare.ch.vo.client.ClientFamilyHistory;

@Service
public class ClientFamilyHistoryServiceImpl implements ClientFamilyHistoryService {

	@Autowired
	private ClientFamilyHistoryDao cmhDao;
	
	public ClientFamilyHistory getClientFamilyHistory(Integer clientId) {
		if(null == clientId) return null;
		return cmhDao.getClientFamilyHistory(clientId);
	}

	public void saveOrUpdate(ClientFamilyHistory cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientFamilyHistory clp = getClientFamilyHistory(cd.getClientId());
			if(null != clp) {
				BeanUtils.copyProperties(cd, clp, new String[]{"id","createTime"});
				cmhDao.update(clp);
			} else {
				cd.setCreateTime(new Date());
				cmhDao.add(cd);
			}
		} 
	}

	public List<ClientFamilyHistory> queryAll() {
		return cmhDao.queryAll();
	}
}
