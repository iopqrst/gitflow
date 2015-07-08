package com.bskcare.ch.service.impl.tg;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.tg.TgEvaluatedDao;
import com.bskcare.ch.service.tg.TgEvaluatedService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.tg.TgEvaluated;

@Service
public class TgEvaluatedServiceImpl implements TgEvaluatedService{
	
	@Autowired
	private TgEvaluatedDao tgEvaluatedDao;
	
	public void addTgEvaluated(String mobile){
		if(!StringUtils.isEmpty(mobile) ){
			Long mo = Long.parseLong(mobile.trim());
			TgEvaluated tgEvaluated = new TgEvaluated();
			tgEvaluated.setMobile(mo);
			
			tgEvaluatedDao.add(tgEvaluated);
		}
	}
	
	public String isEvaluated(String mobile){
		JSONObject jo = new JSONObject();
		if(!StringUtils.isEmpty(mobile) ){
			Long mo = Long.parseLong(mobile.trim());
			TgEvaluated tgEvaluated = tgEvaluatedDao.queryTgEvaluated(mo);
			if(tgEvaluated != null){
				jo.put("code", 1);
				jo.put("msg", "已经领取过");
			}else{
				jo.put("code", 2);
				jo.put("msg", "未领取过");
			}
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数错误");
		}
		
		return jo.toString();
	}
}
