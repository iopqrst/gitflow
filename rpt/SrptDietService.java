package com.bskcare.ch.service.rpt;

import com.bskcare.ch.vo.rpt.SrptDiet;

public interface SrptDietService {
	
	public SrptDiet querySrptDiet(Integer srptId);
	
	/**
	 * 修改
	 */
	public int updateDiets(String field,Integer dietId);
}
