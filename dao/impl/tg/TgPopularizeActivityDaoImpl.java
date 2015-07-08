package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgPopularizeActivityDao;
import com.bskcare.ch.vo.tg.TgPopularizeActivity;

@Repository
@SuppressWarnings("unchecked")
public class TgPopularizeActivityDaoImpl extends BaseDaoImpl<TgPopularizeActivity> implements TgPopularizeActivityDao{

	public TgPopularizeActivity queryPopularizeByClientId(Integer clientId){
		List args = new ArrayList();
		
		String hql = " from TgPopularizeActivity where clientId = ?";
		args.add(clientId);
	
		List<TgPopularizeActivity> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
}
