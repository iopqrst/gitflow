package com.bskcare.ch.service.impl.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.SrptLifePrincipleDao;
import com.bskcare.ch.service.rpt.SrptLifePrincipleService;
import com.bskcare.ch.vo.rpt.SrptLifePrinciple;

@Service
public class SrptLifePrincipleServiceImpl implements SrptLifePrincipleService{
	
	@Autowired
	private SrptLifePrincipleDao lifePrincipleDao;
	
	public SrptLifePrinciple queryLifePrinciple(Integer id){
		return lifePrincipleDao.queryLifePrinciple(id);
	}
}
