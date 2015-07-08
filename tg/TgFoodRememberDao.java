package com.bskcare.ch.dao.tg;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgFoodRember;

public interface TgFoodRememberDao extends BaseDao<TgFoodRember>{
	
	public List<TgFoodRember> queryFoodRember(TgFoodRember foodRember);
	
	public PageObject<TgFoodRember> queryFoodRember(TgFoodRember foodRember, QueryInfo queryInfo, String foodIds);
	
	public TgFoodRember queryFoodRemberByName(TgFoodRember foodRember);
}
