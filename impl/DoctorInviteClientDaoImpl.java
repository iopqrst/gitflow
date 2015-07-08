package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.DoctorInviteClientDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.DoctorInviteClient;

@Repository
@SuppressWarnings("unchecked")
public class DoctorInviteClientDaoImpl extends BaseDaoImpl<DoctorInviteClient> implements DoctorInviteClientDao{

	public DoctorInviteClient queryDoctorInviteClient(String mobile, Integer doctorId){
		List args = new ArrayList();
		String hql ="from DoctorInviteClient where 1 = 1";
		if(!StringUtils.isEmpty(mobile)){
			hql += " and mobile = ?";
			args.add(mobile.trim());
		}
		if(doctorId != null){
			hql += " and doctorId = ?";
			args.add(doctorId);
		}
		List<DoctorInviteClient> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		
		return null;
	}
}
