package com.bskcare.ch.dao.tg;

import java.util.List;
import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgCookedFood;

@SuppressWarnings("unchecked")
public interface TgCookedFoodDao extends BaseDao<TgCookedFood> {

	/* public List<TgCookedFood> queryTgCookedFood(String foodType,String join); */

	/** 随机查询一个熟食中有某类食物的熟食 */
	/* public TgCookedFood queryCookedFood(String foodType ,String join); */

	public PageObject queryTgCookedFood(TgCookedFood cookedFood,
			QueryInfo queryInfo);

	public TgCookedFood queryCookedFood(TgCookedFood cookedFood,
			String contain, String cjoin, String noContain, String njoin,
			String disease);

	public List<TgCookedFood> queryCookByName(String name);
	
	public List<TgCookedFood> queryCookedFood(String disease);
}
