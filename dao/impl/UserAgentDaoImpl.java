package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.UserAgentDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.UserAgent;
import com.bskcare.ch.vo.UserInfo;

@Repository
@SuppressWarnings("unchecked")
public class UserAgentDaoImpl extends BaseDaoImpl<UserAgent> implements UserAgentDao{

	public List<UserAgent> queryUserAgent(Integer id){
		String hql = "from UserAgent where status = ?";
		List args = new ArrayList();
		args.add(UserAgent.AGENT_NORMAL);
		if(id != null){
			hql += " and userId = ?";
			args.add(id);
		}
		
		return executeFind(hql, args.toArray());
	}
	
	
	public PageObject queryUserAgentInfo(UserAgent agent,QueryInfo queryInfo){
		
		List args = new ArrayList();
		String asql = "select f.id,f.userId,h.userName,f.`status` from (select t1.id,t1.userId,t1.`status`" +
				" from t_user_agent t1 where t1.`status`=?) f join (select t2.id,t2.name userName from" +
				" t_userinfo t2 where t2.status = ?) h on f.userId = h.id";
		
		args.add(UserAgent.AGENT_NORMAL);
		args.add(UserInfo.USER_NORMAL);
		
		String csql = "select f.id,f.agentUserId,h.agentName,f.createTime from (select t1.id,t1.agentUserId,t1.createTime" +
				" from t_user_agent t1 where t1.`status`=?) f join (select t2.id,t2.name agentName from t_userinfo t2 where" +
				" t2.status = ?) h on f.agentUserId = h.id";
		
		args.add(UserAgent.AGENT_NORMAL);
		args.add(UserInfo.USER_NORMAL);
		
		String sql = "select m.id,m.userId,m.userName,m.status,n.agentUserId,n.agentName,n.createTime from ("+asql+") m " +
				" join ("+csql+") n on m.id = n.id";
		
		Map scalars = new LinkedHashMap();
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("userId", StandardBasicTypes.INTEGER);
		scalars.put("userName", StandardBasicTypes.STRING);
		scalars.put("status", StandardBasicTypes.INTEGER);
		scalars.put("agentUserId", StandardBasicTypes.INTEGER);
		scalars.put("agentName", StandardBasicTypes.STRING);
		scalars.put("createTime", StandardBasicTypes.TIMESTAMP);
		
		return queryObjectsBySql(sql, null, scalars, args.toArray(), queryInfo, null);
		
	}
	
	public void delUserAgent(UserAgent agent){
		List args = new ArrayList();
		String sql="update t_user_agent set status = ?";
		args.add(UserAgent.AGENT_CANCLE);
		if(agent != null){
			if(null != agent.getId()){
				sql += " where id = ?";
				args.add(agent.getId());
			}
		}
		updateBySql(sql, args.toArray());
	}
	
	
	public void deleteUserAgentByUserId(Integer userId){
		String sql = "delete from t_user_agent where userId = ?";
		deleteBySql(sql, userId);
	}
}
