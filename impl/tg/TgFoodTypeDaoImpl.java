package com.bskcare.ch.dao.impl.tg;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.tg.TgFoodTypeDao;
import com.bskcare.ch.vo.tg.TgFoodType;

@Repository
public class TgFoodTypeDaoImpl extends BaseDaoImpl<TgFoodType> implements TgFoodTypeDao{
	
	public List<TgFoodType> queryFoodType(){
		String hql = " from TgFoodType where status = 0";
		return executeFind(hql);
	}
}
