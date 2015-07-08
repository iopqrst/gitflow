package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptFood;

@SuppressWarnings("unchecked")
public interface RptFoodDao extends BaseDao<RptFood> {

	public List<String> queryRptFoodName(RptFood food, String queryCondition);
	
	/**
	 * 添加食物
	 */
	public void addFood(String fields,String values);
	
	/**
	 * 查询全部的食物
	 */
	public PageObject<RptFood> findFoodPage(RptFood food,QueryInfo queryInfo);
	
	/**
	 *根据id查询食物的信息 
	 */
	public List findFoodById(Integer id);
	
	/**
	 * 根据id修改食物的信息
	 */
	public void updateFoodById(String fields,Integer id);
	/**
	 * 添加疾病时添加食物字段
	 */
	public void addProperty(String property);
	/**
	 * 删除疾病史删除字段
	 */
	public void delProperty(String property);
	
}
