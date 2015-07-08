package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ManageLogTypeDao;
import com.bskcare.ch.service.ManageLogTypeService;
import com.bskcare.ch.vo.ManageLogType;

@Service
public class ManageLogTypeServiceImpl implements ManageLogTypeService{
	@Autowired
	private ManageLogTypeDao logTypeDao;
	
	public List<ManageLogType> queryManageLogType(){
		return logTypeDao.queryManageLogType();
	}
	

	public List<ManageLogType> queryLogTypeByClientId(Integer clientId){
		return logTypeDao.queryLogTypeByClientId(clientId);
	}
}
