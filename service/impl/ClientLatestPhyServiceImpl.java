package com.bskcare.ch.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientLatestPhyDao;
import com.bskcare.ch.service.ClientLatestPhyService;
import com.bskcare.ch.vo.client.ClientLatestPhy;

@Service
public class ClientLatestPhyServiceImpl implements ClientLatestPhyService {

	@Autowired
	private ClientLatestPhyDao clientLatestPhyDao;
	
	public ClientLatestPhy getClientLatestPhy(Integer clientId) {
		if(null == clientId) return null;
		return clientLatestPhyDao.getClientLastestPhy(clientId);
	}

	public void saveOrUpdate(ClientLatestPhy cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientLatestPhy clp = getClientLatestPhy(cd.getClientId());
			if(null != clp) {
				BeanUtils.copyProperties(cd, clp, new String[]{"createTime"});
				clientLatestPhyDao.update(clp);
			} else {
				cd.setCreateTime(new Date());
				clientLatestPhyDao.add(cd);
			}
		} 
	}

	public void saveOrUpdateAndroid(ClientLatestPhy cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientLatestPhy clp = getClientLatestPhy(cd.getClientId());
			if(null != clp) {
				BeanUtils.copyProperties(cd, clp, new String[]{"id","tc","tlc","hdl",
						"ldl","sgpt","sgot","alb","tbil","dbil","scre","bun",
						"bk","natrium","createTime"});
				clientLatestPhyDao.update(clp);
			} else {
				cd.setCreateTime(new Date());
				clientLatestPhyDao.add(cd);
			}
		} 
	}
	
	
	public void saveOrUpdateSport(ClientLatestPhy cd) {
		if(null != cd && null != cd.getClientId()) {
			ClientLatestPhy clp = getClientLatestPhy(cd.getClientId());
			if(null != clp) {
				clientLatestPhyDao.updateClientLatestPhy(cd);
			} else {
				cd.setCreateTime(new Date());
				clientLatestPhyDao.add(cd);
			}
		} 
	}
}
