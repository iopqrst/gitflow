package com.bskcare.ch.dao.impl.online;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.online.OnlineSatisfactionDao;
import com.bskcare.ch.vo.online.OnlineSatisfaction;

@Repository
@SuppressWarnings("unchecked")
public class OnlineSatisfactionDaoImpl extends BaseDaoImpl<OnlineSatisfaction>
		implements OnlineSatisfactionDao {

	public Object queryScore(Integer userId) {
		return queryScore(userId , null);
	}
	
	public Object queryScore(Integer expertId, Integer socre) {
		String hql = "select count(o.id) from OnlineSatisfaction o where 1 = 1 ";
		ArrayList args = new ArrayList();
		if(null != expertId) {
			hql += " and o.expertId = ?";
			args.add(expertId);
		}
		
		if(null != socre) {
			hql += " and o.score >= ?";
			args.add(socre);
		}
		
		return this.findUniqueResult(hql, args.toArray());
	}

}
