package com.bskcare.ch.dao.impl.rpt;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.RptSportDao;
import com.bskcare.ch.vo.rpt.RptSport;

@Repository
public class RptSportDaoImpl extends BaseDaoImpl<RptSport> implements RptSportDao{
	
	public RptSport findRptSport(Integer rptId, Integer clientId){
		String hql = "from RptSport where rptId = ? and clientId = ?";
		Object [] obj = {rptId,clientId};
		List<RptSport> list = executeFind(hql, obj);
		RptSport sport = new RptSport();
		if(!CollectionUtils.isEmpty(list)){
			sport = list.get(0);
		}
		return sport;
	}

	public int updateSportByFields(String fields, Integer id) {
		String sql = "update rpt_sport set " + fields + " where id = ?";
		return updateBySql(sql, new Object[] { id });
	}
	
	public void deleteSportByRptId(Integer rptId){
		String sql = "delete from rpt_sport where rptId = ?";
		deleteBySql(sql, rptId);
	}
}
