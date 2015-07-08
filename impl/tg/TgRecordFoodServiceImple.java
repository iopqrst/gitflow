package com.bskcare.ch.service.impl.tg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.tg.TgFoodRememberDao;
import com.bskcare.ch.dao.tg.TgRecommendCalorieDao;
import com.bskcare.ch.dao.tg.TgRecordFoodDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.tg.TgRecordFoodService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.ListUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.tg.TgFoodRember;
import com.bskcare.ch.vo.tg.TgRecommendCalorie;
import com.bskcare.ch.vo.tg.TgRecordFood;

@Service
public class TgRecordFoodServiceImple implements TgRecordFoodService{

	@Autowired
	private TgRecordFoodDao recordFoodDao;
	@Autowired
	private TgFoodRememberDao foodRemberDao;
	@Autowired
	private TgRecommendCalorieDao calorieDao;
	
	public String addRecordFood(TgRecordFood recordFood, String date, Integer cid){
		JSONObject jo = new JSONObject();
		if(recordFood != null && cid != null){
			if(!StringUtils.isEmpty(recordFood.getFoodId()) 
					&& recordFood.getCalorie() != 0 
						&& recordFood.getCanci() != null){
				Date createTime = null;
				if(!StringUtils.isEmpty(date)){
					createTime = DateUtils.parseDate(date, "yyyy-MM-dd");
				}else{
					createTime = DateUtils.getDateByType(new Date(), "yyyy-MM-dd");
				}
				recordFood.setClientId(cid);
				TgRecordFood rf = recordFoodDao.queryRecordFood(recordFood, createTime);
				if(rf != null){
					String foodId = rf.getFoodId();
					int calorie = 0;
					if(!foodId.contains(recordFood.getFoodId())){
						foodId += "," + recordFood.getFoodId();
						calorie = rf.getCalorie() + recordFood.getCalorie();
					}
					rf.setCalorie(calorie);
					rf.setFoodId(foodId);
					rf.setCreateTime(new Date());
					recordFoodDao.update(rf);
				}else{
					recordFood.setClientId(cid);
					recordFood.setCreateTime(new Date());
					recordFoodDao.add(recordFood);
				}
			}
			
			return queryRecordFood(cid, date);
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数错误");
			jo.put("data", "");
			
			return jo.toString();
		}
		
	}
	
	
	public String queryRecordFood(Integer cid, String date){
		JSONObject jo = new JSONObject();
		if(cid != null){
			Date createTime = null;
			if(!StringUtils.isEmpty(date)){
				createTime = DateUtils.parseDate(date, "yyyy-MM-dd");
			}else{
				createTime = DateUtils.getDateByType(new Date(), "yyyy-MM-dd");
			}
			List<TgRecordFood> lst = recordFoodDao.queryClientRecordFood(cid, createTime);
			List<TgFoodRember> lstFood = foodRemberDao.queryFoodRember(null);
			if(!CollectionUtils.isEmpty(lst)){
				int sumCalorie = 0;
				JSONArray ja = new JSONArray();
				for (TgRecordFood recordFood : lst) {
					String foodId = recordFood.getFoodId();
					if(!StringUtils.isEmpty(foodId)){
						String[] foodIds = foodId.split(",");
						for (String fid : foodIds) {
							for (TgFoodRember foodRember : lstFood) {
								JSONObject json = new JSONObject();
								int foid = Integer.parseInt(fid);
								if(foodRember.getId() == foid){
									json.put("foodName", foodRember.getFoodName());
									json.put("calorie", recordFood.getCalorie());
									json.put("canci", recordFood.getCanci());
									ja.add(json.toString());
								}
							}
						}
					}
					sumCalorie += recordFood.getCalorie();
				}
				JSONObject json = new JSONObject();
				json.put("list", ja.toArray());
				json.put("sumCalorie", sumCalorie);
				TgRecommendCalorie rc = calorieDao.queryRecommendCalorie(cid);
				if(rc != null){
					json.put("recommendCalorie", rc.getCalorie());
				}else{
					json.put("recommendCalorie", 0);
				}
				
				jo.put("code", 1);
				jo.put("msg", "操作成功");
				jo.put("data",json.toString());
			}else{
				JSONObject json = new JSONObject();
				json.put("list", "");
				json.put("sumCalorie", 0);
				TgRecommendCalorie rc = calorieDao.queryRecommendCalorie(cid);
				if(rc != null){
					json.put("recommendCalorie", rc.getCalorie());
				}else{
					json.put("recommendCalorie", 0);
				}
				jo.put("code", 1);
				jo.put("msg", "没有数据");
				jo.put("data", json.toString());
			}
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数错误");
			jo.put("data", "");
		}
		
		return jo.toString();
	}
	
	//查询客户某餐次的食物信息
	public String queryClientFoodRember(Integer cid, TgFoodRember foodRember, String date, QueryInfo queryInfo){
		JSONObject jo = new JSONObject();
		//查询客户某天某个餐次的食物信息
		Date createTime = null;
		if(!StringUtils.isEmpty(date)){
			createTime = DateUtils.parseDate(date, "yyyy-MM-dd");
		}else{
			createTime = DateUtils.getDateByType(new Date(), "yyyy-MM-dd");
		}
		TgRecordFood rfood = new TgRecordFood();
		rfood.setClientId(cid);
		String canci = foodRember.getCanci();
		rfood.setCanci(Integer.parseInt(canci));
		TgRecordFood rf = recordFoodDao.queryRecordFood(rfood, createTime);
		
		JSONObject jsb = new JSONObject();
		String baseUrl = SystemConfig.getString("image_base_url");
		jsb.put("baseUrl", baseUrl);
		
		JSONArray ja = new JSONArray();
		foodRember.setCanci("");
		if(rf == null){
			PageObject<TgFoodRember> obj = foodRemberDao.queryFoodRember(foodRember, queryInfo, null);
			if(obj != null){
				List<TgFoodRember> lst = obj.getDatas();
				if(!CollectionUtils.isEmpty(lst)){
					for (TgFoodRember tgFoodRember : lst) {
						JSONObject json = new JSONObject();
						json.put("id", tgFoodRember.getId());
						json.put("foodName", tgFoodRember.getFoodName());
						json.put("calorie", tgFoodRember.getCalorie());
						if(!StringUtils.isEmpty(tgFoodRember.getPicUrl())){
							json.put("picUrl", tgFoodRember.getPicUrl());
						}else{
							json.put("picUrl", "");
						}
						//客户是否选择 0：未选择   1：已选择
						json.put("isClient", 0);
						
						ja.add(json.toString());
					}
				}
			}
		}else{
			String foodId = rf.getFoodId();
			if(queryInfo.getPageOffset() == 0){
				List<TgFoodRember> lstFood = foodRemberDao.queryFoodRember(foodRember);
				String[] foodIds = foodId.split(",");
				for (String fid : foodIds) {
					for (TgFoodRember food : lstFood) {
						JSONObject json = new JSONObject();
						int foid = Integer.parseInt(fid);
						if(food.getId() == foid){
							json.put("id", food.getId());
							json.put("foodName", food.getFoodName());
							json.put("calorie", food.getCalorie());
							if(!StringUtils.isEmpty(food.getPicUrl())){
								json.put("picUrl", food.getPicUrl());
							}else{
								json.put("picUrl", "");
							}
							json.put("isClient", 1);
							ja.add(json.toString());
						}
					}
					
				}
			}
			
			PageObject<TgFoodRember> obj = foodRemberDao.queryFoodRember(foodRember, queryInfo, foodId);
			if(obj != null){
				List<TgFoodRember> lst = obj.getDatas();
				if(!CollectionUtils.isEmpty(lst)){
					for (TgFoodRember food : lst) {
						JSONObject json = new JSONObject();
						json.put("id", food.getId());
						json.put("foodName", food.getFoodName());
						json.put("calorie", food.getCalorie());
						if(!StringUtils.isEmpty(food.getPicUrl())){
							json.put("picUrl", food.getPicUrl());
						}else{
							json.put("picUrl", "");
						}
						json.put("isClient", 0);
						ja.add(json.toString());
					}
				}
			}
		}
		
		jsb.put("list", ja.toArray());
		
		jo.put("code", 1);
		jo.put("msg", "查询成功");
		jo.put("data", jsb.toString());
		return jo.toString();
	}
}
