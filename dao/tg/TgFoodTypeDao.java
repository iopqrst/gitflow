package com.bskcare.ch.dao.tg;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.tg.TgFoodType;

public interface TgFoodTypeDao extends BaseDao<TgFoodType>{

	public List<TgFoodType> queryFoodType();
}
