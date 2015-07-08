package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientHobbyDao;
import com.bskcare.ch.vo.client.ClientHobby;

@Repository
public class ClientHobbyDaoImpl extends BaseDaoImpl<ClientHobby> implements ClientHobbyDao{

	public ClientHobby getClientHobby(Integer clientId) {
		String hql = "from ClientHobby where clientId = ?";
		List<ClientHobby> list = executeFind(hql, clientId);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}
