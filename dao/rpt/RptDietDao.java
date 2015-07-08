package com.bskcare.ch.dao.rpt;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.RptDiet;

public interface RptDietDao extends BaseDao<RptDiet>{
	
	public RptDiet findRptDiet(Integer rptId,Integer clientId);
	int updateDietBySingleField(String field, String content, Integer dietId);
	/**
	 * 修改多个字段的值
	 * @param fields
	 * @param dietId
	 */
	public int updateDietByFields(String fields, Integer dietId);
	
	public RptDiet findRptDietByType(Integer rptId, Integer clientId,int type) ;
	
	/**根据健康报告id删除信息*/
	public void deleteDietByRptId(Integer rptId);
}
