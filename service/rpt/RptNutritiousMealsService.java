package com.bskcare.ch.service.rpt;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptNutritiousMeals;

@SuppressWarnings("unchecked")
public interface RptNutritiousMealsService {
	
	/**
	 * 查找单个对象
	 */
	public RptNutritiousMeals findMealsById(Integer id);
	
	/**
	 * 根据id删除营养套餐
	 */
	public void deleteNutritiousById(Integer id);
	
	/**
	 * 添加营养套餐
	 */
	public RptNutritiousMeals addNutritious(RptNutritiousMeals meals);
	
	/**
	 * 查询所有的营养套餐的信息
	 */
	public PageObject findMealsPage(RptNutritiousMeals meals,QueryInfo queryInfo);
	
	public void updateMeals(RptNutritiousMeals meals);
	
	/**
	 * 随机查询出一个营养套餐根据疾病
	 */
	public RptNutritiousMeals findNutritiousByDisease(RptNutritiousMeals meals, String medical);
	
	/**
	 * 添加营养套餐
	 * @param fields 字段名称
	 * @param values 字段对应的值
	 */
	public void addMeals(String fields,String values);
	
	public String findMealById(Integer id);
	
	/**
	 * 根据id修改营养套餐信息
	 */
	public void updateMealsById(String fields,Integer id);

}
