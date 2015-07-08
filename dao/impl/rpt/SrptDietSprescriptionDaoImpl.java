package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SrptDietSprescriptionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.rpt.SrptDietSprescription;

@Repository
@SuppressWarnings("unchecked")
public class SrptDietSprescriptionDaoImpl extends BaseDaoImpl<SrptDietSprescription> implements
		SrptDietSprescriptionDao {
	
	public PageObject<SrptDietSprescription> queryAll(SrptDietSprescription dietSprescription,QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		String hql = "from SrptDietSprescription where 1=1 ";
		if (null != dietSprescription) {
			if (!StringUtils.isEmpty(dietSprescription.getName())) {
				hql += " and name like ?";
				args.add("%" + dietSprescription.getName().trim() + "%");
			}
		}
		return this.queryObjects(hql, args.toArray(), queryInfo); 
	}
	
	public List<SrptDietSprescription> queryDiet(String ids){
		String sql = "select * from srpt_diet_sprescription where id in ("+ids+")";
		return executeNativeQuery(sql, null, SrptDietSprescription.class);
	}
}
