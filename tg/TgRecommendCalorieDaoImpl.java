package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgRecommendCalorieDao;
import com.bskcare.ch.vo.tg.TgRecommendCalorie;

@Repository
@SuppressWarnings("unchecked")
public class TgRecommendCalorieDaoImpl extends BaseDaoImpl<TgRecommendCalorie> implements TgRecommendCalorieDao{

	public TgRecommendCalorie queryRecommendCalorie(Integer cid){
		List args = new ArrayList();
		String hql = "from TgRecommendCalorie where 1 = 1";
		if(cid != null){
			hql += " and clientId = ?";
			args.add(cid);
		}
		
		List<TgRecommendCalorie> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
}
