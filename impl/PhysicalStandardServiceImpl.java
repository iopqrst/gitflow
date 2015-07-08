package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.PhysicalStandardDao;
import com.bskcare.ch.service.PhysicalStandardService;
import com.bskcare.ch.vo.client.PhysicalStandard;

@Service
public class PhysicalStandardServiceImpl implements PhysicalStandardService{
	
	@Autowired
	private PhysicalStandardDao physicalStandardDao;
	
	public String findStandardBypdId(Integer pdId){
		List<PhysicalStandard> lstStandard=new ArrayList<PhysicalStandard>();
		lstStandard=physicalStandardDao.findStandardBypdId(pdId);
		if(!CollectionUtils.isEmpty(lstStandard)){
			JSONArray standardArray = new JSONArray();
			for(PhysicalStandard standard:lstStandard){
				JSONObject obj=new JSONObject();
				obj.put("pdId",standard.getPdId());
				obj.put("name",standard.getName());
				obj.put("up", standard.getUp());
				obj.put("down",standard.getDown());
				obj.put("pgender", standard.getGender());
				obj.put("bgcolor", standard.getBgcolor());
				obj.put("desc", standard.getDesc());
				obj.put("remark", standard.getRemark());
				standardArray.add(obj);
			}
			return standardArray.toString();
		}else{
			return "";
		}
	}
}
