package com.bskcare.ch.service.impl.tg;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.tg.TgRecommendCalorieDao;
import com.bskcare.ch.service.tg.TgRecommendCalorieService;
import com.bskcare.ch.vo.tg.TgRecommendCalorie;

@Service
public class TgRecommendCalorieServiceImpl implements TgRecommendCalorieService{
	
	@Autowired
	private TgRecommendCalorieDao recommendCalorieDao;
	
	public String addRecommendCalorie(Integer cid, double calorie){
		JSONObject jo = new JSONObject();
		if(cid != null && calorie != 0){
			TgRecommendCalorie rc = recommendCalorieDao.queryRecommendCalorie(cid);
			if(rc != null){
				//已经有记录，修改
				rc.setCalorie(calorie);
				rc.setCreateTime(new Date());
				recommendCalorieDao.update(rc);
			}else{
				//没有记录，添加
				TgRecommendCalorie trc = new TgRecommendCalorie();
				trc.setClientId(cid);
				trc.setCalorie(calorie);
				trc.setCreateTime(new Date());
				recommendCalorieDao.add(trc);
			}
			jo.put("code", 1);
			jo.put("msg", "添加成功");
			JSONObject json = new JSONObject();
			json.put("calorie", calorie);
			jo.put("data", json.toString());
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数错误");
			jo.put("data", "");
		}
		return jo.toString();
	}
}
