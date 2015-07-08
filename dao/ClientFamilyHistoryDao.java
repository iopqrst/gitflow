package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientFamilyHistory;

/**
 * 用户家族疾病史
 * 
 * @author houzhiqing
 */
public interface ClientFamilyHistoryDao extends BaseDao<ClientFamilyHistory> {
	
	public ClientFamilyHistory getClientFamilyHistory(Integer clientId);
	/**
	 * 全查
	 */
	public List<ClientFamilyHistory> queryAll();
	
	public void updateHelathFamily(Integer clientId,Integer isHasFamilyHealth);
}
