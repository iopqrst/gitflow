package com.bskcare.ch.service.rpt;

import java.util.Date;

import com.bskcare.ch.vo.rpt.SportPlan;

public interface SportPlanService {
	
	/**
	 *添加运动运动计划 
	 */
	public SportPlan addSportPlan(SportPlan plan);
	
	/**
	 * 根据用户id和健康报告id查询某个用户一周的运动课程表
	 */
	public SportPlan findSportPlan(Integer rptId, Integer clientId);
	
	/**
	 *修改运动课程表
	 */
	public void updateSportPlanByField(String field,Integer planId);
	
	public void updateSportPlanTime(String field,Integer planId,String fields,Integer sportId);
	
	/**
	 * 根据用户查询查询某个用户当天的运动课程
	 */
	public String findSportPlanByClientId(Integer clientId,Date currentTime);
}
