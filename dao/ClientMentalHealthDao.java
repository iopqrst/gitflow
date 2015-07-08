package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientMentalHealth;

/**
 * 用户心理健康
 * 
 * @author houzhiqing
 */
public interface ClientMentalHealthDao extends BaseDao<ClientMentalHealth> {
	
	public ClientMentalHealth getClientMentalHealth(Integer clientId);
	
}
