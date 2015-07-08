package com.bskcare.ch.dao.rpt;


import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptNutritiousMeals;

@SuppressWarnings("unchecked")
public interface RptNutritiousMealsDao extends BaseDao<RptNutritiousMeals> {

	/**
	 * 根据id修改营养套餐的信息
	 */
	public void updateMeals(RptNutritiousMeals meals);

	/**
	 * 根据id修改营养套餐单个字段的信息
	 */
	public int updateMealsBySingleField(String field, String content,
			Integer mealsId);
	
	/**
	 * 查询所有的营养套餐的信息
	 */
	public PageObject findMealsPage(RptNutritiousMeals meals,QueryInfo queryInfo);
	
	/**
	 * 随机查询出一个营养套餐根据套餐
	 */
	public RptNutritiousMeals findNutritiousByDisease(RptNutritiousMeals meals,String medical);
	
	/**
	 * 添加营养套餐
	 * @param fields 字段名称
	 * @param values 字段对应的值
	 */
	public void addMeals(String fields,String values);
	
	/**
	 * 根据id查询套餐
	 */
	public List findMealsById(Integer id);
	
	/**
	 * 根据id修改营养套餐信息
	 */
	public void updateMealsById(String fields,Integer id);
	/**
	 * 添加疾病时添加字段
	 */
	public void addProperty(String property);
	/**
	 * 删除疾病史删除字段
	 */
	public void delProperty(String property);
	
}
