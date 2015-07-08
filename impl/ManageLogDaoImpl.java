package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ManageLogExtend;
import com.bskcare.ch.dao.ManageLogDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ManageLog;
import com.bskcare.ch.vo.UserInfo;

@Repository
@SuppressWarnings("unchecked")
public class ManageLogDaoImpl extends BaseDaoImpl<ManageLog> implements ManageLogDao {

	public void addLog(ManageLog mlog) {
		this.add(mlog);
	}
	
	
	public List queryLogByClientId(Integer clientId){
		List args = new ArrayList();
		String sql = "select {t1.*},t2.name from t_manage_log t1 join t_manage_log_type t2 on t1.type = t2.id where clientId = ?";
		args.add(clientId);
		
		sql += " order by t1.operateDate desc limit 20";
		
		Map entities = new LinkedHashMap();
		entities.put("t1", ManageLog.class);
		
		Map scalars = new LinkedHashMap();
		scalars.put("name", StandardBasicTypes.STRING);
		
		return executeNativeQuery(sql, entities, scalars,args.toArray(), null);
	}
	
	public List<ManageLogExtend> queryManageLog(ManageLog log){
		List args = new ArrayList();
		String asql = "select h.id,h.clientId,h.operateDate,h.content,h.userId,h.type,h.userIp,f.name from (select * from t_manage_log where 1=1";
		
		if(log != null){
			if(log.getType() != null){
				asql += " and type = ?";
				args.add(log.getType());
			}
			if(log.getClientId() != null){
				asql += " and clientId = ?";
				args.add(log.getClientId());
			}
		}
		
		asql += " order by operateDate desc limit 20) h join (select id,name from t_manage_log_type) f on h.type = f.id";
		
		String csql = "select t1.id,t1.name userName from t_userinfo t1 where t1.status = ?";
		args.add(UserInfo.USER_NORMAL);
		
		String sql = "select m.*,n.* from ("+asql+") m left join ("+csql+") n on m.userId = n.id";
		
		return executeNativeQuery(sql, null, null, args.toArray(), ManageLogExtend.class);
	}
}
