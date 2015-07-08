package com.bskcare.ch.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientMentalHealthDao;
import com.bskcare.ch.service.ClientMentalHealthService;
import com.bskcare.ch.vo.client.ClientMentalHealth;

@Service
public class ClientMentalHealthServiceImpl implements ClientMentalHealthService {

	@Autowired
	private ClientMentalHealthDao cmhDao;
	
	public ClientMentalHealth getClientMentalHealth(Integer clientId) {
		if(null == clientId) return null;
		return cmhDao.getClientMentalHealth(clientId);
	}

	public void saveOrUpdate(ClientMentalHealth cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientMentalHealth clp = getClientMentalHealth(cd.getClientId());
			if(null != clp) {
				BeanUtils.copyProperties(cd, clp, new String[]{"createTime"});
				cmhDao.update(clp);
			} else {
				cd.setCreateTime(new Date());
				cmhDao.add(cd);
			}
		} 
	}

	
	
}
