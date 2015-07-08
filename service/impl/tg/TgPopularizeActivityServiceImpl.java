package com.bskcare.ch.service.impl.tg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.tg.TgPopularizeActivityDao;
import com.bskcare.ch.service.tg.TgPopularizeActivityService;
import com.bskcare.ch.vo.tg.TgPopularizeActivity;

@Service
public class TgPopularizeActivityServiceImpl implements TgPopularizeActivityService{
	
	@Autowired
	private TgPopularizeActivityDao popuDao;
	
	public TgPopularizeActivity queryPopularizeByClientId(Integer clientId){
		
		return popuDao.queryPopularizeByClientId(clientId);
	}
	
	
	public void addClientPopularize(TgPopularizeActivity popu){
		popuDao.add(popu);
	}
}
