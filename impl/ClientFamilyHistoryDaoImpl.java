package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientFamilyHistoryDao;
import com.bskcare.ch.vo.client.ClientFamilyHistory;
import com.bskcare.ch.vo.client.ClientMedicalHistory;

@Repository
@SuppressWarnings("unchecked")
public class ClientFamilyHistoryDaoImpl extends BaseDaoImpl<ClientFamilyHistory>
		implements ClientFamilyHistoryDao {

	public ClientFamilyHistory getClientFamilyHistory(Integer clientId) {
		String hql = "from ClientFamilyHistory where clientId = ?";
		List<ClientFamilyHistory> list = executeFind(hql, clientId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<ClientFamilyHistory> queryAll() {
		String hql = "from ClientFamilyHistory ";
		List<ClientFamilyHistory> list = executeFind(hql);
		return list;
	}
	
	public void updateHelathFamily(Integer clientId,Integer isHasFamilyHealth){
		String sql = "update t_client_family_health set isHasFamilyHealth = ? where clientId = ?";
		List args = new ArrayList();
		args.add(isHasFamilyHealth);
		args.add(clientId);
		updateBySql(sql, args.toArray());
	}

}
