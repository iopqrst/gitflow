package com.bskcare.ch.service;

import com.bskcare.ch.vo.client.ClientDiet;

/**
 * 用户饮食习惯
 * @author Administrator
 *
 */
public interface ClientDietService {

	void saveOrUpdate(ClientDiet cd);
	ClientDiet getClientDiet(Integer clientId);
	
}
