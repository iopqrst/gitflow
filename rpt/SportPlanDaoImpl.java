package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SportPlanDao;
import com.bskcare.ch.vo.rpt.SportPlan;

@SuppressWarnings("unchecked")
@Repository
public class SportPlanDaoImpl extends BaseDaoImpl<SportPlan> implements
		SportPlanDao {

	public SportPlan findSportPlan(Integer rptId, Integer clientId) {
		String hql = "from SportPlan where rptId = ? and clientId = ?";
		Object[] obj = { rptId, clientId };
		List<SportPlan> list = executeFind(hql, obj);
		SportPlan plan = new SportPlan();
		if (!CollectionUtils.isEmpty(list)) {
			plan = list.get(0);
		}
		return plan;
	}

	public int updateSportPlanByField(String field, Integer planId) {
		String sql = "update rpt_sport_plan set " + field + " where id = ?";
		return updateBySql(sql, new Object[] { planId });
	}

	public List<SportPlan> querSportPlanByClientId(Integer clientId,
			Date currentTime) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from `rpt_sport_plan` sp where 1=1 ");
		if (null != clientId) {
			sql.append(" and sp.clientid=? ");
			args.add(clientId);
		}
		if (null != currentTime) {
			sql.append(" and sp.createTime < ? and sp.endTime > ? ");
			args.add(currentTime);
			args.add(currentTime);
		}
		sql.append(" order by sp.createtime desc ");
		return this.executeNativeQuery(sql.toString(), null, null, args
				.toArray(), SportPlan.class);
	}

	public void deletePlanByRptId(Integer rptId){
		String sql = "delete from rpt_sport_plan where rptId = ?";
		deleteBySql(sql, rptId);
	}
}
