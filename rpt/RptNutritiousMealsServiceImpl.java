package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.rpt.RptNutritiousMealsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.RptDiseaseService;
import com.bskcare.ch.service.rpt.RptNutritiousMealsService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.rpt.RptFood;
import com.bskcare.ch.vo.rpt.RptNutritiousMeals;
@Service
@SuppressWarnings("unchecked")
public class RptNutritiousMealsServiceImpl implements RptNutritiousMealsService{

	@Autowired
	RptNutritiousMealsDao mealsDao;
	@Autowired
	private RptDiseaseService rptDiseaseService;
	
	public RptNutritiousMeals findMealsById(Integer id) {
		return mealsDao.load(id);
	}
	
	public void deleteNutritiousById(Integer id){
	    mealsDao.delete(id);
	}
	
	public RptNutritiousMeals addNutritious(RptNutritiousMeals meals){
		if(meals != null){
			String breakfast = getTag(meals.getBreakfast());
			String zaojia = getTag(meals.getZaojia());
			String lunch = getTag(meals.getLunch());
			String wujia = getTag(meals.getWujia());
			String dinner = getTag(meals.getDinner());
			String wanjia = getTag(meals.getWanjia());
			String taboo = getTag(meals.getTaboo());
			
			meals.setBreakfast(breakfast);
			meals.setZaojia(zaojia);
			meals.setLunch(lunch);
			meals.setWujia(wujia);
			meals.setDinner(dinner);
			meals.setWanjia(wanjia);
			meals.setTaboo(taboo);
			return mealsDao.add(meals);
		}
		return null;
	}
	
	private String getTag(String tag) {
		if (!StringUtils.isEmpty(tag)) {
			String suffix = tag.substring(tag.length() - 1, tag.length());
			if (Constant.RPT_TAG_SPLIT.equals(suffix)) {
				tag = tag.substring(0, tag.length() - 1);
			}
		}
		return tag;
	}
	
	public PageObject findMealsPage(RptNutritiousMeals meals,QueryInfo queryInfo){
		return mealsDao.findMealsPage(meals, queryInfo);
	}
	
	public void updateMeals(RptNutritiousMeals meals){
		if(meals != null){
			String breakfast = getTag(meals.getBreakfast());
			String zaojia = getTag(meals.getZaojia());
			String lunch = getTag(meals.getLunch());
			String wujia = getTag(meals.getWujia());
			String dinner = getTag(meals.getDinner());
			String wanjia = getTag(meals.getWanjia());
			String taboo = getTag(meals.getTaboo());
			
			meals.setBreakfast(breakfast);
			meals.setZaojia(zaojia);
			meals.setLunch(lunch);
			meals.setWujia(wujia);
			meals.setDinner(dinner);
			meals.setWanjia(wanjia);
			meals.setTaboo(taboo);
			mealsDao.updateMeals(meals);
		}
	}
	
	
	public RptNutritiousMeals findNutritiousByDisease(RptNutritiousMeals meals, String medical) {
		String condition = rptDiseaseService.getMedicalCondition(medical, RptFood.EATING_MORE);
		RptNutritiousMeals rm = mealsDao.findNutritiousByDisease(meals, condition);
		
		if(null == rm) {
			condition = rptDiseaseService.getMedicalCondition(medical, RptFood.EATING_NORMAL);
			rm = mealsDao.findNutritiousByDisease(meals, condition);
		}
		return rm;
	}
	
	public void addMeals(String fields,String values){
		mealsDao.addMeals(fields, values);
	}
	
	public String findMealById(Integer id){
		List list = mealsDao.findMealsById(id);
		JSONObject jo = new JSONObject();
		jo.put("rptMeals", JsonUtils.getJONSArray4JavaList(list));
		return jo.toString();
	}
	
	
	public void updateMealsById(String fields,Integer id){
		mealsDao.updateMealsById(fields, id);
	}
}
