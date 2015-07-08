package com.bskcare.ch.dao.impl.rpt;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.RptDietDao;
import com.bskcare.ch.vo.rpt.RptDiet;

@Repository
public class RptDietDaoImpl extends BaseDaoImpl<RptDiet> implements RptDietDao {

	public RptDiet findRptDiet(Integer rptId, Integer clientId) {
		String hql = "from RptDiet where clientId = ? and rptId = ?";
		Object[] obj = { clientId, rptId };
		List<RptDiet> list = executeFind(hql, obj);
		RptDiet diet = new RptDiet();
		if (!CollectionUtils.isEmpty(list)) {
			diet = list.get(0);
		}
		return diet;
	}

	
	public RptDiet findRptDietByType(Integer rptId, Integer clientId,int type) {
		
		String hql = "from RptDiet where clientId = ? and rptId = ? and type = ?";
		Object[] obj = {clientId,rptId,type};
		List<RptDiet> list = executeFind(hql, obj);
		RptDiet diet = new RptDiet();
		if (!CollectionUtils.isEmpty(list)) {
			diet = list.get(0);
		}
		return diet;
	}

	
	
	public int updateDietBySingleField(String field, String content,
			Integer dietId) {
		String sql = "update rpt_diet set " + field + " = ? where id = ?";
		return updateBySql(sql, new Object[] { content, dietId });
	}
	
	public int updateDietByFields(String fields, Integer dietId) {
		String sql = "update rpt_diet set " + fields + " where id = ?";
		return updateBySql(sql, new Object[] { dietId });
	}

	public void deleteDietByRptId(Integer rptId){
		String sql = "delete from rpt_diet where rptId = ?";
		deleteBySql(sql, rptId);
	}
}
