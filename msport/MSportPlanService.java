package com.bskcare.ch.service.msport;

import com.bskcare.ch.vo.msport.MSportPlan;

public interface MSportPlanService {
	
	/**
	 * 添加运动计划
	 */
	public void addMSportPlan(MSportPlan mplan);
	
	/**
	 * 根据用户id,计划时间查询运动计划数据
	 */
	public MSportPlan queryMSportPlan(Integer cid);
	
	public void addMSportPlanSport(MSportPlan mplan);
	
	/**保存运动时间**/
	public void savePlanDay(Integer clientId,int planDay);
	/**保存步长**/
	public void saveStepWidth(Integer clientId,double stepWidth);
}
