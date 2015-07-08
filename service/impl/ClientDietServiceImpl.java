package com.bskcare.ch.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientDietDao;
import com.bskcare.ch.service.ClientDietService;
import com.bskcare.ch.vo.client.ClientDiet;

@Service
public class ClientDietServiceImpl implements ClientDietService {

	@Autowired
	private ClientDietDao clientDietDao;
	
	public ClientDiet getClientDiet(Integer clientId) {
		if(null == clientId) return null;
		return clientDietDao.getClientDiet(clientId);
	}

	public void saveOrUpdate(ClientDiet cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientDiet client = getClientDiet(cd.getClientId());
			if(null != client) {
				client.setContent(cd.getContent());
				clientDietDao.update(client);
			} else {
				cd.setCreateTime(new Date());
				clientDietDao.add(cd);
			}
		} 
	}
	
}