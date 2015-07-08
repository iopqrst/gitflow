package com.bskcare.ch.service.impl.msport;
import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.ScoreConstant;
import com.bskcare.ch.dao.msport.MSportCalorieDao;
import com.bskcare.ch.dao.msport.MSportDao;
import com.bskcare.ch.service.msport.MSportCalorieService;
import com.bskcare.ch.service.score.ScoreRecordService;
import com.bskcare.ch.vo.msport.MSport;
import com.bskcare.ch.vo.msport.MSportCalorie;

@Service
public class MSportCalorieServiceImpl implements MSportCalorieService{

	@Autowired
	private MSportDao msportDao;
	
	@Autowired
	private MSportCalorieDao msportCalorieDao;
	
	@Autowired
	private ScoreRecordService scoreService;
	
	public String addMsportCalorie(MSport msport, MSportCalorie msportCalorie){
		JSONObject jo = new JSONObject();
		Integer mid = null;
		if(msport !=null && msportCalorie != null){
			MSport ms = msportDao.querySport(msport.getClientId(), msport.getTestDate());
			if(ms != null){
				ms.setCalorie(msport.getCalorie());
				msportDao.update(ms);
				mid = ms.getId();
			}else{
				msport.setUploadDate(new Date());
				MSport msp = msportDao.add(msport);
				mid = msp.getId();
			}
			
			if(mid != null){
				msportCalorie.setMid(mid);
				msportCalorie.setCreateTime(new Date());
				msportCalorieDao.add(msportCalorie);
			}
			
			jo.put("code", 1);
			jo.put("msg", "添加成功");
			JSONObject json = new JSONObject();
			json.put("calorie", msport.getCalorie());
			jo.put("data", json.toString());
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数错误");
			jo.put("data", "");
		}
		return jo.toString();
	}
}
