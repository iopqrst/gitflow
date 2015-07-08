package com.bskcare.ch.dao.rpt;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.SportPlan;

public interface SportPlanDao extends BaseDao<SportPlan>{
		
	/**
	 * 根据用户id和健康报告id查询某个用户一周的运动课程表
	 */
	public SportPlan findSportPlan(Integer rptId, Integer clientId);
	
	/**
	 *修改运动课程表
	 */
	public int updateSportPlanByField(String field, Integer planId);
	
	/**
	 * 根据用户id查询所有数据
	 */
	public List<SportPlan> querSportPlanByClientId(Integer clientId,Date currentTime);
	
	/**根据健康报告id删除信息**/
	public void deletePlanByRptId(Integer rptId);
	
}
