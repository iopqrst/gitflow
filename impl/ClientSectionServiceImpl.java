package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientSectionDao;
import com.bskcare.ch.service.ClientSectionService;
import com.bskcare.ch.vo.client.ClientSection;
@Service
public class ClientSectionServiceImpl implements ClientSectionService{

	@Autowired
	private ClientSectionDao sectionDao;
	
	public ClientSection save(ClientSection c) {
		return sectionDao.add(c);
	}

	public List<ClientSection> find(Integer id) {
		return sectionDao.find(id);
	}

}
