package com.bskcare.ch.dao.impl.msport;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.msport.MSportPlanDao;
import com.bskcare.ch.vo.msport.MSportPlan;

@Repository
@SuppressWarnings("unchecked")
public class MSportPlanDaoImpl extends BaseDaoImpl<MSportPlan> implements MSportPlanDao{

	public MSportPlan queryMSportPlan(Integer clientId){
		List args = new ArrayList();
		String hql = "from MSportPlan where 1=1";
		if(clientId != null){
			hql += " and clientId = ?";
			args.add(clientId);
		}
		
		List<MSportPlan> list = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	
	public void updateMsportPlan(MSportPlan plan){
		String sql = "update m_sport_plan set stepWidth = ? ,createTime = ? where clientId = ?";
		List args = new ArrayList();
		args.add(plan.getStepWidth());
		args.add(plan.getCreateTime());
		args.add(plan.getClientId());
		updateBySql(sql, args.toArray());
	}
	
	public void updatePlanDay(MSportPlan plan){
		String sql = "update m_sport_plan set planDay = ? ,createTime = ? where clientId = ?";
		List args = new ArrayList();
		args.add(plan.getPlanDay());
		args.add(plan.getCreateTime());
		args.add(plan.getClientId());
		updateBySql(sql, args.toArray());
	}
}
