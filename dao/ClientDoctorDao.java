package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientDoctor;

public interface ClientDoctorDao extends BaseDao<ClientDoctor>{
	
	/**
	 * 医生集合
	 * @param id
	 * @return
	 */
	public List<ClientDoctor> find(Integer id);

}
