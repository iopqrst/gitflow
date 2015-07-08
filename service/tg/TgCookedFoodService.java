package com.bskcare.ch.service.tg;

import java.util.List;

import com.bskcare.ch.bo.RiskResultBean;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.tg.TgMeals;
import com.bskcare.ch.vo.tg.TgCookedFood;

@SuppressWarnings("unchecked")
public interface TgCookedFoodService {

	/** 添加熟食库 */
	public TgCookedFood addTgCookedFood(TgCookedFood cookedFood);

	/** 修改熟食库信息 */
	public void UpdateTgCookedFood(TgCookedFood cookedFood);

	/** 根据id查询熟食库的信息 */
	public TgCookedFood queryTgCookedFoodById(Integer id);

	public PageObject queryTgCookedFood(TgCookedFood cookedFood,
			QueryInfo queryInfo);

	// public void getMealsInfo();
	public TgMeals getMealsInfo(Integer clientId, double calories, int disease,
			String hebingDisease, RiskResultBean risk);

	/** 验证名称唯一性 **/
	public List<TgCookedFood> queryCookByName(String name);

	/** 删除 **/
	public void delete(Integer id);
}
