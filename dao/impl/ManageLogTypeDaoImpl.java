package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ManageLogTypeDao;
import com.bskcare.ch.vo.ManageLogType;

@Repository
@SuppressWarnings("unchecked")
public class ManageLogTypeDaoImpl extends BaseDaoImpl<ManageLogType> implements ManageLogTypeDao{
	
	public List<ManageLogType> queryManageLogType(){
		String hql = "from ManageLogType";
		return executeFind(hql);
	}
	
	
	public List<ManageLogType> queryLogTypeByClientId(Integer clientId){
		String sql = "select DISTINCT(t1.type) id,t2.`name` from t_manage_log t1 join t_manage_log_type t2 on t1.type = t2.id where 1=1";
		List args = new ArrayList();
		if(clientId != null){
			sql += " and t1.clientId = ?";
			args.add(clientId);
		}
		return executeNativeQuery(sql, null, null, args.toArray(), ManageLogType.class);
	}
}
