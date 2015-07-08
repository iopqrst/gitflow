package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.ClientPcard;

public interface ClientPcardDao extends BaseDao<ClientPcard> {

	/**
	 * 添加客户产品关系
	 * 
	 * @author yangtao
	 * 
	 */
	public void addClientPcard(ClientPcard clientPcard);
	
	public Object getCodeNum(String pcCode);
		
	public Object getAllowCount(String code);
	
	public void deleteFamilyInfoByClientId(int clientId);
		
}