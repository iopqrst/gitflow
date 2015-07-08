package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptFood;

@SuppressWarnings("unchecked")
public interface RptFoodService {
	
	/**
	 * 根据条件查询相应的食物
	 * @param food 食物对象
	 * @param medical 对应疾病信息，多个用,分割
	 * @param amount 吃多吃少 -1：少吃； 0：普通   1：多吃
	 */
	public String queryRptFood(RptFood food, String medical, int amount);
	
	/**
	 * 添加食物
	 */
	public void addFood(String fields,String values);
	
	/**
	 * 查询全部的食物
	 */
	public PageObject<RptFood> findFoodPage(RptFood food,QueryInfo queryInfo);
	
	/**
	 * 根据id删除食物
	 */
	public void delFoodById(Integer id);
	
	/**
	 *根据id查询食物的信息 
	 */
	public String findFoodById(Integer id);
	
	/**
	 * 根据id修改食物的信息
	 */
	public void updateFoodById(String fields,Integer id);
	
	public RptFood findFood(Integer id);
	
}
