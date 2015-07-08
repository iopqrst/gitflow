package com.bskcare.ch.dao.msport;
import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.msport.MSportPlan;

public interface MSportPlanDao extends BaseDao<MSportPlan>{
	
	/**
	 * 根据用户id,计划时间查询运动计划数据
	 */
	public MSportPlan queryMSportPlan(Integer clientId);
	
	public void updateMsportPlan(MSportPlan plan);
	
	public void updatePlanDay(MSportPlan plan);
}
