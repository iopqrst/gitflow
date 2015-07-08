package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgEvaluatedDao;
import com.bskcare.ch.vo.tg.TgEvaluated;

@Repository
@SuppressWarnings("unchecked")
public class TgEvaluatedDaoImpl extends BaseDaoImpl<TgEvaluated> implements TgEvaluatedDao{

	public TgEvaluated queryTgEvaluated(Long mobile){
		String hql = "from TgEvaluated where mobile = ?";
		List args = new ArrayList(); 
		args.add(mobile);
		
		List<TgEvaluated> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
}
