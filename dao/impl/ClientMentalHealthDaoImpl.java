package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientMentalHealthDao;
import com.bskcare.ch.vo.client.ClientMentalHealth;

@Repository
public class ClientMentalHealthDaoImpl extends BaseDaoImpl<ClientMentalHealth>
		implements ClientMentalHealthDao {

	public ClientMentalHealth getClientMentalHealth(Integer clientId) {
		String hql = "from ClientMentalHealth where clientId = ?";
		List<ClientMentalHealth> list = executeFind(hql, clientId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
