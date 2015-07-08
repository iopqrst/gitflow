package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.PhysicalStandardDao;
import com.bskcare.ch.vo.client.PhysicalStandard;

@Repository
public class PhysicalStandardDaoImpl extends BaseDaoImpl<PhysicalStandard>
		implements PhysicalStandardDao {

	public List<PhysicalStandard> findStandardBypdId(Integer pdId) {
		String hql = "from PhysicalStandard where pdId=?";
		List<PhysicalStandard> lst = executeFind(hql, pdId);
		return lst;
	}

	public double findMaxStandard(Integer pdId) {
		String hql = "select max(up) from PhysicalStandard where pdId=?";
		double maxStandard = -1;
		Object obj = findUniqueResult(hql, pdId);
		if (obj != null) {
			maxStandard = Double.parseDouble(obj.toString());
		} else {
			maxStandard = -1;
		}

		return maxStandard;
	}

}
