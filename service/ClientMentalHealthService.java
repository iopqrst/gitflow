package com.bskcare.ch.service;

import com.bskcare.ch.vo.client.ClientMentalHealth;

/**
 * 用户心理健康
 * @author Administrator
 *
 */
public interface ClientMentalHealthService {

	void saveOrUpdate(ClientMentalHealth clp);
	ClientMentalHealth getClientMentalHealth(Integer clientId);
	
}
