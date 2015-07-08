package com.bskcare.ch.dao.impl.rpt;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SrptDietPrincipleDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.rpt.SrptDietPrinciple;

@Repository
@SuppressWarnings("unchecked")
public class SrptDietPrincipleDaoImpl extends BaseDaoImpl<SrptDietPrinciple>
		implements SrptDietPrincipleDao {

	public PageObject queryDietPrinciple(SrptDietPrinciple dietPrinciple,
			QueryInfo queryInfo) {
		String hql = "from SrptDietPrinciple where 1=1 ";
		ArrayList args = new ArrayList();
		if (null != dietPrinciple) {
			if (!StringUtils.isEmpty(dietPrinciple.getDisease())) {
				hql += " and disease=? ";
				args.add(dietPrinciple.getDisease().trim());
			}
		}
		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public SrptDietPrinciple queryDietPrincipleByName(String name) {
		String hql = "from SrptDietPrinciple where disease = ?";
		List<SrptDietPrinciple> lstDiet = executeFind(hql, name);
		SrptDietPrinciple diet = null;
		if (!CollectionUtils.isEmpty(lstDiet)) {
			diet = lstDiet.get(0);
		}
		return diet;
	}

	public List<SrptDietPrinciple> queryDietPrincipleByDisease(String diseaseName) {
		List args = new ArrayList();
		String sql = "select * from srpt_diet_principle where 1=1 ";
		if (!StringUtils.isEmpty(diseaseName)) {
			String [] dn = diseaseName.split(",");
			for (int i = 0; i < dn.length; i++) {
				sql += " and disease like ?";
				args.add("%" + dn[i].trim() + "%");
			}
		}
		return executeNativeQuery(sql, args.toArray(), SrptDietPrinciple.class);
	}
}
