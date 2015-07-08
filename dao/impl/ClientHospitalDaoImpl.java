package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientHospitalDao;
import com.bskcare.ch.vo.client.ClientHospital;
@Repository
public class ClientHospitalDaoImpl extends BaseDaoImpl<ClientHospital> implements ClientHospitalDao{

	public ClientHospital save(ClientHospital c) {
		// TODO Auto-generated method stub
		return this.add(c);
	}

	public List<ClientHospital> find() {
		String hql = " from ClientHospital h ";
		
		return this.executeFind(hql);
	}

	public ClientHospital find(String name) {
		String hql = " from ClientHospital h where h.hisName=? ";
		
		return (ClientHospital) this.findUniqueResult(hql,name);
	}
}
