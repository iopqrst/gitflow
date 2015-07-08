package com.bskcare.ch.dao.impl.medal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.medal.NTgMedalGradingDao;
import com.bskcare.ch.vo.medal.NTgMedalGrading;

@Repository
@SuppressWarnings("unchecked")
public class NTgMedalGradingDaoImpl extends BaseDaoImpl<NTgMedalGrading> implements NTgMedalGradingDao{
	
	public List<NTgMedalGrading> queryMedalGrading(Integer medalId){
		String hql = " from NTgMedalGrading where medalId = ?";
		List args = new ArrayList();
		args.add(medalId);
		
		return executeFind(hql, args.toArray());
	}
	
	public NTgMedalGrading queryMedalGrading(Integer medalId, Integer level){
		String hql = " from NTgMedalGrading where medalId = ?";
		List args = new ArrayList();
		args.add(medalId);
		if(level != null){
			hql += " and level = ?";
			args.add(level);
		}
		List<NTgMedalGrading> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
}
