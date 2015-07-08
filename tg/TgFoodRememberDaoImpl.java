package com.bskcare.ch.dao.impl.tg;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgFoodRememberDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgFoodRember;

@Repository
@SuppressWarnings("unchecked")
public class TgFoodRememberDaoImpl extends BaseDaoImpl<TgFoodRember> implements TgFoodRememberDao{
	
	public List<TgFoodRember> queryFoodRember(TgFoodRember foodRember){
		List args = new ArrayList();
		String hql = "from TgFoodRember where 1 = 1";
		if(foodRember != null){
			if(!StringUtils.isEmpty(foodRember.getCanci())){
				hql += " and canci like ?";
				args.add("%" + foodRember.getCanci() + "%");
			}
			if(!StringUtils.isEmpty(foodRember.getFoodName())){
				hql += " and foodName like ?";
				args.add("%" + foodRember.getFoodName() + "%");
			}
		}
		return executeFind(hql, args.toArray());
	}
	
	
	public PageObject<TgFoodRember> queryFoodRember(TgFoodRember foodRember, QueryInfo queryInfo, String foodIds){
		List args = new ArrayList();
		String hql = "from TgFoodRember where 1 = 1";
		if(foodRember != null){
			if(!StringUtils.isEmpty(foodRember.getCanci())){
				hql += " and canci like ?";
				args.add("%" + foodRember.getCanci() + "%");
			}
			if(!StringUtils.isEmpty(foodRember.getFoodName())){
				hql += " and foodName like ?";
				args.add("%" + foodRember.getFoodName() + "%");
			}
			if(!StringUtils.isEmpty(foodIds)){
				hql += " and id not in("+foodIds+")";
			}
			if(foodRember.getType() != null){
				hql += " and type = ?";
				args.add(foodRember.getType());
			}
		}
		hql += " order by id desc";
		return queryObjects(hql, args.toArray(), queryInfo);
	}
	
	public TgFoodRember queryFoodRemberByName(TgFoodRember foodRember){
		List args = new ArrayList();
		String hql = "from TgFoodRember where 1 = 1";
		if(foodRember != null){
			if(!StringUtils.isEmpty(foodRember.getFoodName())){
				hql += " and foodName = ?";
				args.add(foodRember.getFoodName());
			}
		}
		List<TgFoodRember> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		return null;
	}
	
}
