package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.client.ClientDoctor;

public interface ClientDoctorService {
	/**
	 * 增加
	 */
	public ClientDoctor save(ClientDoctor c);
	/**
	 * 查询所有医生
	 */
	public List<ClientDoctor> find(Integer id);
	
}
