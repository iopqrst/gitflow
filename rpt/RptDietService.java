package com.bskcare.ch.service.rpt;

import com.bskcare.ch.vo.rpt.RptDiet;

public interface RptDietService {
	
	/**
	 * 保存
	 */
	public RptDiet save(RptDiet diet);
	
	/**
	 * 查找单个对象
	 */
	public RptDiet finById(Integer id);

	public int updateDietBySingleField(String field, String content,
			Integer dietId);
	
	/**
	 * 根据rptId查询的基本信息计算膳食信息
	 */
	public RptDiet findRptDiet(Integer rptId,Integer clientId);

	/**
	 * 修改多个字段的值
	 * @param fields
	 * @param dietId
	 */
	public int updateDietByFields(String fields, Integer dietId);
	
	
	public RptDiet findRptDietByType(Integer rptId, Integer clientId,int type) ;

}
