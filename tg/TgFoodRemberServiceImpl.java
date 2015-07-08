package com.bskcare.ch.service.impl.tg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.tg.TgFoodRememberDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.tg.TgFoodRemberService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgFoodRember;

@Service
public class TgFoodRemberServiceImpl implements TgFoodRemberService{
	
	@Autowired
	private TgFoodRememberDao foodRemberDao;
	
	
	public PageObject<TgFoodRember> queryFoodRember(TgFoodRember foodRember, QueryInfo queryInfo){
		PageObject<TgFoodRember> obj = foodRemberDao.queryFoodRember(foodRember, queryInfo, null);
		return obj;
	}
	
	public void deleteFoodRember(Integer id){
		foodRemberDao.delete(id);
	}
	
	public TgFoodRember queryFoodRemberById(Integer id){
		if(id != null){
			return foodRemberDao.load(id);
		}
		return null;
	}
	
	public TgFoodRember queryFoodRemberByName(TgFoodRember foodRember){
		if(foodRember != null && !StringUtils.isEmpty(foodRember.getFoodName())){
			return foodRemberDao.queryFoodRemberByName(foodRember);
		}
		return null;
	}
	
	public void addFoodRember(TgFoodRember foodRember){
		if(foodRember != null){
			foodRemberDao.add(foodRember);
		}
	}
	
	public void updateFoodRember(TgFoodRember foodRember){
		if(foodRember != null){
			foodRemberDao.update(foodRember);
		}
	}
}
