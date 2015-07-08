package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.Zy1VoDao;
import com.bskcare.ch.service.Zy1VoService;
import com.bskcare.ch.vo.search.Zy1Vo;
@Service("zy1VoService")
public class Zy1VoServiceImpl implements Zy1VoService {
	@Autowired
    private Zy1VoDao zy1VoDao;
	public List<Zy1Vo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		return zy1VoDao.queryByCbm(cbm);
	}
	public List<Zy1Vo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		return zy1VoDao.queryByIdMsg(id);
	}

}
