package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ZyVoDao;
import com.bskcare.ch.service.ZyVoService;
import com.bskcare.ch.vo.search.ZyVo;


@Service("zyVoService")
public class ZyVoServiceImpl implements ZyVoService  {
   @Autowired
	private ZyVoDao zyVoDao;
	public List<ZyVo> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		return zyVoDao.queryByCbm(cbm);
	}
	public List<ZyVo> queryByIdMsg(String id) {
		// TODO Auto-generated method stub
		return zyVoDao.queryByIdMsg(id);
	}

}
