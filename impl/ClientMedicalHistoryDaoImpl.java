package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientMedicalHistoryDao;
import com.bskcare.ch.vo.client.ClientMedicalHistory;

@Repository
@SuppressWarnings("unchecked")
public class ClientMedicalHistoryDaoImpl extends BaseDaoImpl<ClientMedicalHistory>
		implements ClientMedicalHistoryDao {

	public ClientMedicalHistory getClientMedicalHistory(Integer clientId) {
		String hql = "from ClientMedicalHistory where clientId = ?";
		List<ClientMedicalHistory> list = executeFind(hql, clientId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<ClientMedicalHistory> queryAll() {
		String hql = "from ClientMedicalHistory ";
		List<ClientMedicalHistory> list = executeFind(hql);
		return list;
	}
	
	public void updateClientHealth(Integer clientId,Integer isHasMedical){
		String sql = "update t_client_health set isHasMedical = ? where clientId = ?";
		List args = new ArrayList();
		args.add(isHasMedical);
		args.add(clientId);
		updateBySql(sql, args.toArray());
	}

}
