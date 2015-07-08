package com.bskcare.ch.dao.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.TodayTaskDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.TodayTask;

@Repository
@SuppressWarnings("unchecked")
public class TodayTaskDaoImpl extends BaseDaoImpl<TodayTask> implements
		TodayTaskDao {

	public PageObject<TodayTask> findCp(Integer clientId, QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		String hql = "from TodayTask where ";
		if (null != clientId) {
			hql += "(clientId is null or clientId=?) ";
			args.add(clientId);
		}
		hql += "and status = 0 ";
		hql += "order by createTime desc";
		return this.queryObjects(hql, args.toArray(), queryInfo);
	}

	public PageObject<TodayTask> findMp(Integer clientId, QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		String hql = "from TodayTask where ";
		hql += "(clientId is null or clientId=?) ";
		args.add(clientId);
		hql += "order by createTime desc";
		return this.queryObjects(hql, args.toArray(), queryInfo);
	}

}
