package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientLinkDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.ClientLink;

@Repository
public class ClientLinkDaoImpl extends BaseDaoImpl<ClientLink> implements
		ClientLinkDao {

	public void addClientLink(ClientLink link) {
		add(link);
	}

	public ClientLink findLinkByClientLinkId(Integer id, Integer clientId) {
		String hql = "from ClientLink where id=? and clientId=?";
		Object[] obj = { id, clientId };
		List<ClientLink> lst = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public PageObject<ClientLink> findClientLink(Integer clientId,
			QueryInfo queryInfo) {
		String hql = "from ClientLink where clientId=? and status=? order by id desc";
		Object[] obj = { clientId, ClientLink.STATUS_NORMAL };
		return queryObjects(hql, obj, queryInfo);
	}

	public void deleteClientLink(Integer id, Integer clientId) {
		String sql = "delete from t_client_link where id=? and clientId=?";
		Object[] obj = { id, clientId };
		deleteBySql(sql, obj);
	}

	public void updateClientLink(ClientLink clientLink) {
		update(clientLink);
	}

}
