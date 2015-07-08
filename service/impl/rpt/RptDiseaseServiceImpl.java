package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.dao.rpt.RptDiseaseDao;
import com.bskcare.ch.dao.rpt.RptFoodDao;
import com.bskcare.ch.dao.rpt.RptNutritiousMealsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.RptDiseaseService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.RptDisease;
import com.bskcare.ch.vo.rpt.RptFood;

@Service
public class RptDiseaseServiceImpl implements RptDiseaseService {

	@Autowired
	private RptDiseaseDao rdDao;
	@Autowired
	private RptFoodDao foodDao;
	@Autowired
	private RptNutritiousMealsDao mealsDao;

	public RptDisease addRptDisease(RptDisease rptDisease) {
		return rdDao.add(rptDisease);
	}

	public String getMedicalCondition(String medical, int amount) {
		List<RptDisease> list = rdDao.queryDisease(medical);
		StringBuffer msg = new StringBuffer(" and (");

		String join = "";
		String opreator = "=";
		if (RptFood.EATING_MORE == amount || RptFood.EATING_NORMAL == amount) {
			join = " and ";
			opreator = " >= ";
		} else if (RptFood.EATING_LESS == amount) {
			join = " or ";
		}

		if (!CollectionUtils.isEmpty(list)) {

			for (int i = 0; i < list.size(); i++) {
				String spell = list.get(i).getSpell();
				if (!StringUtils.isEmpty(spell)) {
					msg.append(spell + opreator + amount);
					if (list.size() - 1 != i) {
						msg.append(join);
					}
				}
			}
			msg.append(")");
		} else {// 没有匹配上有两种情况：1. 当前疾病不在疾病库，所以无法做出判断（这种方式无法判断，故当成正常计算） 2. 身体正常
			msg.append(" zhengchang >= " + amount);
			msg.append(")");
		}
		return msg.toString();
	}

	public List<RptDisease> queryDisease(String medical) {
		return rdDao.queryDisease(medical);
	}

	public List<RptDisease> queryAllDisease() {
		return rdDao.queryAllDisease();
	}

	public int addDiseaseAndFood(RptDisease disease) {
		int num = 1;
		try {
			foodDao.addProperty(disease.getSpell());
		} catch (Exception e) {
			num = 0;
		}
		if (num != 0) {
			try {
				mealsDao.addProperty(disease.getSpell());
			} catch (Exception e) {
				foodDao.delProperty(disease.getSpell());
				num = 0;
			}
		}
		if (num != 0) {
			rdDao.add(disease);
		}
		return num;
	}

	public PageObject<RptDisease> querypagerObject(QueryInfo queryInfo,
			RptDisease disease) {
		queryInfo.setOrder(QueryInfo.ORDER_ASC);
		queryInfo.setSort("sort");
		return rdDao.querypagerObject(queryInfo, disease);
	}

	public RptDisease loadDisease(Integer integer) {
		return rdDao.load(integer);
	}

	public void updateDisease(RptDisease disease) {
		rdDao.updateDisease(disease);
	}

	public int deleteDisease(Integer integer) {
		int num = 1;
		RptDisease disease = rdDao.loadDisease(integer);// 拿到疾病对象
		rdDao.deleteDisease(integer); // 删除数据库疾病对象
		try {
			foodDao.delProperty(disease.getSpell()); // 删除食物表字段
		} catch (Exception e) {
			rdDao.addDisease(disease); // 如果删除食物表字段失败，把疾病对象在添加到数据库
			num = 0; // 标识0
		}
		if (num != 0) { // 如果不是零说明第一个字段删除成功
			try {
				mealsDao.delProperty(disease.getSpell());// 删除第二个字段
			} catch (Exception e) {
				foodDao.addProperty(disease.getSpell());// 如果删除第二个字段失败就添加对象和第一个字段
				rdDao.addDisease(disease);
				num = 0; // 标识0
			}
		}
		return num;

	}

	public List<RptDisease> queryAllDisease(RptDisease disease) {
		return rdDao.queryAllDisease(disease);
	}
	
	public String getIdByDiseaseName(String diseaseName) {
		return rdDao.getIdByDiseaseName(diseaseName);
	}


	public String queryRptDiseaseSelf(){
		JSONObject json = new JSONObject();
		List<RptDisease> lst = rdDao.queryRptDiseaseSelf();
		if(!CollectionUtils.isEmpty(lst)){
//			for (RptDisease rptDisease : lst) {
//				JSONObject jo = new JSONObject();
//				
//			}
			json.put("lst", JsonUtils.getJsonString4JavaList(lst));
		}
		return json.toString();
	}


}
