package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientPhysicalDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.ClientPhysical;

@Repository
public class ClientPhysicalDaoImpl extends BaseDaoImpl<ClientPhysical>
		implements ClientPhysicalDao {

	public PageObject<ClientPhysical> findClientPhysical(Integer clientId,
			QueryInfo queryInfo) {
		String hql = "from ClientPhysical where clientId=? and status=? order by id desc";
		Object[] obj = { clientId, ClientPhysical.STATUS_NORMAL };
		return queryObjects(hql, obj, queryInfo);
	}

	public void addClientPhysical(ClientPhysical physical) {
		add(physical);
	}

	public ClientPhysical findClientPhysicalById(Integer id, Integer clientId) {
		String hql = "from ClientPhysical where id=? and clientId=?";
		Object[] obj = { id, clientId };
		List<ClientPhysical> lst = executeFind(hql, obj);

		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public List<ClientPhysical> queryPhysical(Integer clientId) {
		String hql = "from ClientPhysical where clientId=? and status=? order by physicalTime asc, id asc";
		Object[] args = { clientId, ClientPhysical.STATUS_NORMAL };
		return executeFind(hql, args);
	}
}
