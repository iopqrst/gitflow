package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientDoctorDao;
import com.bskcare.ch.vo.client.ClientDoctor;
@Repository
public class ClientDoctorDaoImpl extends BaseDaoImpl<ClientDoctor> implements ClientDoctorDao{

	public ClientDoctor save(ClientDoctor c) {
		// TODO Auto-generated method stub
		return this.add(c);
	}

	public List<ClientDoctor> find(Integer id) {
		String hql = " from ClientDoctor doctor where doctor.secId=? ";
		
		return this.executeFind(hql, id);
	}

	public ClientDoctor find(String name) {
		String hql = " from ClientDoctor doctor where doctor.docName=? ";
		
		return (ClientDoctor) this.findUniqueResult(hql, name);
	}

}
