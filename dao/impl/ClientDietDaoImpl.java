package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientDietDao;
import com.bskcare.ch.vo.client.ClientDiet;

@Repository
public class ClientDietDaoImpl extends BaseDaoImpl<ClientDiet> implements ClientDietDao{

	public ClientDiet getClientDiet(Integer clientId) {
		String hql = "from ClientDiet where clientId = ?";
		List<ClientDiet> list = executeFind(hql, clientId);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}
