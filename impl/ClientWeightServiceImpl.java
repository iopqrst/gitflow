package com.bskcare.ch.service.impl;

import java.util.Date;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bskcare.ch.dao.ClientWeightDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientWeightService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.ClientWeight;

@Service
@SuppressWarnings("unchecked")
public class ClientWeightServiceImpl implements ClientWeightService{

	@Autowired
	private ClientWeightDao weightDao;
	
	public String addClientWeight(ClientWeight weight){
		JSONObject jo = new JSONObject();
		//添加之前首先查询当天是否上传体重信息
		if(weight != null && weight.getClientId() != null){
			ClientWeight cw = weightDao.queryWeightClient(weight);
			//如果当天上传数据修改，反之添加
			if(cw != null){
				cw.setWeight(weight.getWeight());
				cw.setUploadTime(new Date());
				weightDao.update(cw);
			}else{
				weight.setUploadTime(new Date());
				weightDao.add(weight);
			}
			jo.put("code", 1);
			jo.put("msg", "操作成功");
			jo.put("data", "");
		}else{
			jo.put("code", 0);
			jo.put("msg", "参数错误");
			jo.put("data", "");
		}
	
		return jo.toString();
	}
	
	
	public PageObject queryWeightByClientId(Integer clientId, int month, QueryInfo queryInfo, String uploadDate){
		queryInfo.setPageSize(999999);
		Date startDate = null;
		Date endDate = null;
		if(month == 3){
			month = -2;
			startDate = DateUtils.getBeforeMonth(uploadDate, month);
			String date = DateUtils.format(startDate);
			startDate = DateUtils.parseDate(date, "yyyy-MM");
			endDate = DateUtils.parseDate(uploadDate, "yyyy-MM");
		}else{
			startDate = DateUtils.getBeforeDay(uploadDate, -90);
			endDate = DateUtils.parseDate(uploadDate, "yyyy-MM-dd");
		}
		
		return weightDao.queryWeightByClientId(clientId, startDate, endDate, queryInfo,month);
	}
}
