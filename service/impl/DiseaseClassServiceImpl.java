package com.bskcare.ch.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.DiseaseClassDao;
import com.bskcare.ch.service.DiseaseClassService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.search.DiseaseClass;

@Service("DiseaseClassService")
public class DiseaseClassServiceImpl implements DiseaseClassService {
   
	@Autowired
	private DiseaseClassDao diseaseClassDao;
	
	public List<DiseaseClass> queryDiseaseClassAll() {
		
		return diseaseClassDao.queryClassAll();
	}
	public List<DiseaseClass> queryById(Integer id){
		return diseaseClassDao.queryById(id);
		
	}
	public String queryByIdAndriod(Integer bid) {
		// TODO Auto-generated method stub
		List<DiseaseClass> list=this.queryById(bid);
		JSONObject jo = new JSONObject();
		String json = null ;
		if(!CollectionUtils.isEmpty(list)){
			list.remove(0);
			if(list.size()>1){
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",JsonUtils.getJsonString4JavaList(list));
				json=jo.toString();
			}else{
				jo.put("msg","数据库不存在二级菜单");
				jo.put("code",Constant.INTERFACE_FAIL);
				json=jo.toString();
			}
		}else{
			jo.put("msg","数据库不存在二级菜单");
			jo.put("code",Constant.INTERFACE_FAIL);
			json=jo.toString();
		}
		return json;
	}
	public List<DiseaseClass> queryOneMenuAll(int length) {
		return diseaseClassDao.queryOneMenuAll(length);
	}
	
}
