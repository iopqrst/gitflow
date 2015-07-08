package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.SrptLifePrinciple;

public interface SrptLifePrincipleDao extends BaseDao<SrptLifePrinciple>{
	
	public SrptLifePrinciple queryLifePrinciple(Integer id);
	
	public List<SrptLifePrinciple> queryListLifePriciple(String ids);
}	
