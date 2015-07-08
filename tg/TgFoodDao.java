package com.bskcare.ch.dao.tg;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgFood;

@SuppressWarnings("unchecked")
public interface TgFoodDao extends BaseDao<TgFood> {

	public PageObject queryTgFood(TgFood tgFood, QueryInfo queryInfo);

	public int findByName(String name);

	public List<TgFood> queryFoodByType(Integer type);

}
