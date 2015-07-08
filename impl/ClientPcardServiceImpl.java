package com.bskcare.ch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientPcardDao;
import com.bskcare.ch.service.ClientPcardService;
import com.bskcare.ch.vo.ClientPcard;

@Service
public class ClientPcardServiceImpl implements ClientPcardService {
	@Autowired
	private ClientPcardDao clientPcardDao;

	public ClientPcard add(ClientPcard cp){
		return clientPcardDao.add(cp);
	}
}
