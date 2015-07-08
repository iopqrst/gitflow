package com.bskcare.ch.dao.rpt;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.SrptDiet;

public interface SrptDietDao extends BaseDao<SrptDiet>{
	
	/**跟据简易报告id查询饮食部分**/
	public SrptDiet querySrptDiet(Integer srptId);
		
	/**修改**/
	public int updateDiets(String field,Integer dietId);
}
