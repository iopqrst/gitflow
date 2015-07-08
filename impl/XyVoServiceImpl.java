package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.XyVoDao;
import com.bskcare.ch.service.XyVoService;
import com.bskcare.ch.vo.search.XyVo;
@Service("xyVoService")
public class XyVoServiceImpl implements XyVoService{
    
	@Autowired
	private XyVoDao xyVoDao;

	public List<XyVo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		return xyVoDao.queryByCbm(cbm);
	}

	public List<XyVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		return xyVoDao.queryByIdMsg(id);
	}
	
	
}
