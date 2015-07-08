package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.TgAgentUserDao;
import com.bskcare.ch.vo.TgAgentUser;

@Repository
@SuppressWarnings("unchecked")
public class TgAgentUserDaoImpl extends BaseDaoImpl<TgAgentUser> implements
		TgAgentUserDao {

	public String findTgAgentUser(String url, Integer type) {
		ArrayList args = new ArrayList();
		String hql = " select * from t_agent_user au ";
		hql += " inner join t_clientinfo ci ON ";
		hql += " au.clientId=ci.id ";
		hql += " where 1=1 ";
		hql += " and au.name=? and au.type=? ";
		if (null != url && type != -1) {
			args.add(url);
			args.add(type);
		}
		String str = "";
		List list = this.executeNativeQuery(hql, args.toArray());
		if (null != list && list.size() != 0) {
			str = "0";
		} else {
			str = "1";
		}
		return str;
	}

	public List<TgAgentUser> queryAgentUserByTime(Date createTime) {
		ArrayList args = new ArrayList();
		String hql = " select * from `t_agent_user` where 1=1 ";
		if (null != createTime) {
			hql += " and createTime > ? and createTime < NOW()";
			args.add(createTime);
		}
		List list = this.executeNativeQuery(hql, null, null, args.toArray(),
				TgAgentUser.class);
		return list;
	}
}
