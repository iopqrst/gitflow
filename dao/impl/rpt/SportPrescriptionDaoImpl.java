package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SportPrescriptionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SportPrescription;

@Repository
@SuppressWarnings("unchecked")
public class SportPrescriptionDaoImpl extends BaseDaoImpl<SportPrescription>
		implements SportPrescriptionDao {

	public List<SportPrescription> querySportPrescription(SportPrescription sp, String ids) {
		String sql = "select * from rpt_sport_prescription p where 1 = 1";
		
		if(!StringUtils.isEmpty(ids)) {
			sql += " and p.id in("+ ids +")";
		}
		ArrayList args = new ArrayList();
		if(null != sp) {
			if(null != sp.getType()) {
				sql += " and p.type = ?";
				args.add(sp.getType());
			}
		}
		return executeNativeQuery(sql, args.toArray(), SportPrescription.class);
	}
	
	public List<SportPrescription> findPrescription(){
		 String hql = "from SportPrescription";
		 return executeFind(hql);
	}

	public PageObject findPrescriptionPage(SportPrescription pres,QueryInfo queryInfo){
		String hql = "from SportPrescription where 1=1";
		List args = new ArrayList();
		if(null != pres){
			if(!StringUtils.isEmpty(pres.getName())){
				hql += " and name like ?";
				args.add("%"+pres.getName().trim()+"%");
			}
			if(null != pres.getType()){
				hql += " and type= ?";
				args.add(pres.getType());
			}
		}
		hql += " order by id desc";
		return queryPagerObjects(hql, args.toArray(), queryInfo);
	}
	
	public String findPresByName(String name){
		String sql = "select calories from rpt_sport_prescription where name = ?";
		Object [] obj = {name};
		Object calories = findUniqueResultByNativeQuery(sql, obj);
		String calory = "";
		if(calories != null){
			calory = calories.toString();
		}
		return calory;
	}
	
}
