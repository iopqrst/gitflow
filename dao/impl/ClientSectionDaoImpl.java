package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientSectionDao;
import com.bskcare.ch.vo.client.ClientSection;
@Repository
public class ClientSectionDaoImpl extends BaseDaoImpl<ClientSection> implements ClientSectionDao{

	public ClientSection save(ClientSection c) {
		return this.add(c);
	}

	public List<ClientSection> find(Integer id) {
		String hql = " from ClientSection s where s.hisId=? ";
		return this.executeFind(hql, id);
	}

}
