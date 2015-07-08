package com.bskcare.ch.service.rpt;

import com.bskcare.ch.vo.rpt.RptSport;

public interface RptSportService {

	/**
	 * 增加
	 */
	public RptSport save(RptSport rptSport);
	
	/**
	 * 根据用户id和健康报告id查询运动信息
	 */
	public RptSport findRptSport(Integer rptId, Integer clientId);
	
	public int updateSportByFields(String field,Integer id);
}
