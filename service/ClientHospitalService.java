package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.client.ClientHospital;

public interface ClientHospitalService {
	/**
	 * 增加
	 */
	public ClientHospital save(ClientHospital c);
	/**
	 * 查询所有医院
	 */
	public List<ClientHospital> find();
}
