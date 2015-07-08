package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.client.ClientFamilyHistory;

/**
 * 用户家族疾病史
 */
public interface ClientFamilyHistoryService {

	void saveOrUpdate(ClientFamilyHistory clp);
	ClientFamilyHistory getClientFamilyHistory(Integer clientId);
	
	/**
	 * 全查
	 */
	public List<ClientFamilyHistory> queryAll();
}
