package com.bskcare.ch.service.impl.rpt;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.dao.rpt.RptFoodDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.RptDiseaseService;
import com.bskcare.ch.service.rpt.RptFoodService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.rpt.RptFood;

@Service
@SuppressWarnings("unchecked")
public class RptFoodServiceImpl implements RptFoodService{

	@Autowired
	private RptFoodDao rptFoodDao;
	
	@Autowired
	private RptDiseaseService rptDiseaseService;
	
	public String queryRptFood(RptFood food, String medical, int amount) {
		String condition = rptDiseaseService.getMedicalCondition(medical, amount);
		List<String> foodNames = rptFoodDao.queryRptFoodName(food,condition);
		
		if(!CollectionUtils.isEmpty(foodNames)) {
			return ArrayUtils.join(foodNames.toArray(),"、");
		}
		
		return "";
	}
	
	public PageObject<RptFood> findFoodPage(RptFood food,QueryInfo queryInfo){
		return rptFoodDao.findFoodPage(food, queryInfo);
	}
	
	public void addFood(String fields,String values){
		rptFoodDao.addFood(fields,values);
	}
	
	public void delFoodById(Integer id){
		rptFoodDao.delete(id);
	}

	
	public void updateFoodById(String fields,Integer id){
		rptFoodDao.updateFoodById(fields, id);
	}
	
	public String findFoodById(Integer id){
		List list = rptFoodDao.findFoodById(id);
		JSONObject jo = new JSONObject();
		jo.put("rptFood", JsonUtils.getJONSArray4JavaList(list));
		return jo.toString();
	}
	
	
	public RptFood findFood(Integer id){
		return rptFoodDao.load(id);
	}
}
