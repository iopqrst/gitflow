package com.bskcare.ch.service.tg;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.tg.TgFood;

@SuppressWarnings("unchecked")
public interface TgFoodService {

	/** 添加熟食库成分信息 */
	public TgFood addTgFood(TgFood tgFood);

	/** 修改熟食库成分信息 */
	public void updateTgFood(TgFood tgFood);

	/** 根据id查询熟食库成分信息 */
	public TgFood queryTgFoodById(Integer id);

	public PageObject queryTgFood(TgFood tgFood, QueryInfo queryInfo);

	/** 根据name查询验证name唯一性 **/
	public int findByName(String name);

	/** 删除食物 **/
	public void delete(int id);

	/** 根据类型查询 **/
	public List<TgFood> queryFoodByType(Integer type);
}
