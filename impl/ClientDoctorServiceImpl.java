package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientDoctorDao;
import com.bskcare.ch.service.ClientDoctorService;
import com.bskcare.ch.vo.client.ClientDoctor;
@Service
public class ClientDoctorServiceImpl implements ClientDoctorService{

	@Autowired
	private ClientDoctorDao doctorDao;

	/**
	 * 增加
	 */
	public ClientDoctor save(ClientDoctor c) {
		return doctorDao.add(c);
	}
	
	/**
	 * 查询所有医生
	 */
	public List<ClientDoctor> find(Integer id) {
		
		return doctorDao.find(id);
	}

}
