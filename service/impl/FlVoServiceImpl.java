package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.FlVoDao;
import com.bskcare.ch.service.FlVoService;
import com.bskcare.ch.vo.search.FlVo;
@Service("flVoService")
public class FlVoServiceImpl implements FlVoService {
    
	@Autowired
	private FlVoDao flVoDao;

	public List<FlVo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		return flVoDao.queryByCbm(cbm);
	}

	public String queryAnyOne(String cypbm, Integer id) {
		// TODO Auto-generated method stub
		return flVoDao.queryAnyOne(cypbm, id);
	}

	public List<FlVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		return flVoDao.queryByIdMsg(id);
	}

	
	


}
