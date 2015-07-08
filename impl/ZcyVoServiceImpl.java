package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ZcyVoDao;
import com.bskcare.ch.service.ZcyVoService;
import com.bskcare.ch.vo.search.ZcyVo;
@Service("zcyVoService")
public class ZcyVoServiceImpl implements  ZcyVoService{
    @Autowired
    private ZcyVoDao zcyVoDao;
	public List<ZcyVo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		return zcyVoDao.queryByCbm(cbm);
	}
	public List<ZcyVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		return zcyVoDao.queryByIdMsg(id);
	}

}
