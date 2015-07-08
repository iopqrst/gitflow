package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.BloodSugarTargetDao;
import com.bskcare.ch.vo.BloodSugarTarget;

@Repository
@SuppressWarnings("unchecked")
public class BloodSugarTargetDaoImpl extends BaseDaoImpl<BloodSugarTarget> implements BloodSugarTargetDao  {

	public BloodSugarTarget quertTarget(BloodSugarTarget target) {
		String hql = " from BloodSugarTarget where 1 = 1 ";
		List args = new ArrayList();
		if(target != null){
			if(target.getClientId() != null){
				hql += " and clientId = ? ";
				args.add(target.getClientId());
			}
		}
		
		List<BloodSugarTarget> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}

}
