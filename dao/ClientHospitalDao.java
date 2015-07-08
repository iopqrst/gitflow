package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientHospital;

public interface ClientHospitalDao extends BaseDao<ClientHospital> {
	
	/**
	 * 医院集合
	 * @return
	 */
	public List<ClientHospital> find();
	
}
