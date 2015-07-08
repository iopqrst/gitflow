package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientHospitalDao;
import com.bskcare.ch.service.ClientHospitalService;
import com.bskcare.ch.vo.client.ClientHospital;
@Service
public class ClientHospitalServiceImpl implements ClientHospitalService{

	@Autowired
	private ClientHospitalDao chdao;
	
	public ClientHospital save(ClientHospital c) {
		return chdao.add(c);
	}

	public List<ClientHospital> find() {
		return chdao.find();
	}
}
