package com.bskcare.ch.dao.impl;

import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientPcardDao;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ClientPcard;

@Repository
public class ClientPcardDaoImpl extends BaseDaoImpl<ClientPcard> implements
		ClientPcardDao {

	public void addClientPcard(ClientPcard clientPcard) {
		this.add(clientPcard);
	}
	public Object getCodeNum(String pcCode) {
		String hql = "select count(*) from ClientPcard where pcCode= ? and status=? and type=?";
		Object[] obj = { pcCode,ClientPcard.STATUS_NORMAL,ClientInfo.TYPE_FAMILY};
		return findUniqueResult(hql, obj);
	}

	public Object getAllowCount(String code) {
		String hql = "select allowActiveCount from ProductCard where code= ?";
		return findUniqueResult(hql, code);
	}
	
	//根据亲情账号id修改产品用户中间表状态
	public void deleteFamilyInfoByClientId(int clientId) {
		String hql = "update ClientPcard set status=? where clientId=?";
		Object[] obj = { ClientPcard.STATUS_DELETE, clientId };
		updateByHql(hql, obj);
	}
}
