package com.bskcare.ch.service.tg;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgFoodRember;

public interface TgFoodRemberService {
	
	public PageObject<TgFoodRember> queryFoodRember(TgFoodRember foodRember, QueryInfo queryInfo);
	
	public void deleteFoodRember(Integer id);
	
	public TgFoodRember queryFoodRemberById(Integer id);
	
	public TgFoodRember queryFoodRemberByName(TgFoodRember foodRmber);

	public void addFoodRember(TgFoodRember foodRember);
	
	public void updateFoodRember(TgFoodRember foodRember);
	
}
