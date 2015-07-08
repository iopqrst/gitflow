package com.bskcare.ch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.SystemconfigDao;
import com.bskcare.ch.service.SystemconfigService;
import com.bskcare.ch.util.CompressorJsp;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.Systemconfig;

@Service("systemconfigService")
@SuppressWarnings("unchecked")
public class SystemconfigServiceImpl implements SystemconfigService {

	@Autowired
	private SystemconfigDao systemconfigDao;
	private static Map<String, String> systemconfigMap = new HashMap<String, String>(); 

	public String getValue(String key) {
		String valueStr = "";
		valueStr = systemconfigMap.get(key);
		if(StringUtils.isEmpty(valueStr)) {
			System.out.println("键为Null或键值为Null");
		}
		System.out.println("Key=" + key + ",value=" + valueStr);
		return valueStr;
	}

	public void initMethod() {
		//System.out.println("====================initMethod====================");
//		String hql = " from Systemconfig ";
//		List<Systemconfig> list = systemconfigDao.executeFind(hql);
//		if (!CollectionUtils.isEmpty(list)) {
//			for (Systemconfig scf : list) {
//				systemconfigMap.put(scf.getKey(), scf.getValue());
//			}
//		}
		
		if(StringUtils.isDevelopment()) {//如果是开发环境
			String classRootPath = this.getClass().getResource("/").getPath();
			System.out.println(classRootPath);
			
			//String jspPath = classRootPath.substring(0, classRootPath.indexOf("WEB-INF"));
			
			if(classRootPath.indexOf("webapps") > 0 && classRootPath.indexOf("classes") > 0) {
				CompressorJsp.doCompressor(classRootPath.replace("classes", "jsp"));
			}
		
		}
	}
	
	public void getSystemConfig(){
		for(Map.Entry entry:systemconfigMap.entrySet()) {
			System.out.println("static-key:" + entry.getKey() + ",static-value:" + entry.getValue());
		}
	}
}