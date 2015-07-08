package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SrptDietPrinciple;

@SuppressWarnings("unchecked")
public interface SrptDietPrincipleDao extends BaseDao<SrptDietPrinciple>{
	
	public PageObject queryDietPrinciple(SrptDietPrinciple dietPrinciple,QueryInfo queryInfo);
	
	public SrptDietPrinciple queryDietPrincipleByName(String name);
	
	/**按疾病名称模糊查询**/
	public List<SrptDietPrinciple> queryDietPrincipleByDisease(String diseaseName);
}
