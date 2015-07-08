package com.bskcare.ch.dao.impl.rpt;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SrptDietDao;
import com.bskcare.ch.vo.rpt.SrptDiet;

@Repository
public class SrptDietDaoImpl extends BaseDaoImpl<SrptDiet> implements SrptDietDao{
	
	public SrptDiet querySrptDiet(Integer srptId){
		String hql = "from SrptDiet where srptId = ?";
		List<SrptDiet> lstDiet = executeFind(hql, srptId);
		SrptDiet diet = null;
		if(!CollectionUtils.isEmpty(lstDiet)){
			diet = lstDiet.get(0);
		}
		return diet;
	}
	
	public int updateDiets(String field,Integer dietId) {
		String sql = "update srpt_diet set "+ field +" where id=?";
		return this.updateBySql(sql, new Object[]{dietId});
	}
}
