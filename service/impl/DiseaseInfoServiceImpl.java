package com.bskcare.ch.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.DiseaseInfoDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.DiseaseInfoService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.search.DiseaseInfo;
@Service("diseaseInfoService")
public class DiseaseInfoServiceImpl implements DiseaseInfoService{
    @Autowired
	private DiseaseInfoDao diseaseInfoDao;
	/**
	 * 查询疾病信息
	 */
	public List<DiseaseInfo> queryCnr(String cbm) {
		// TODO Auto-generated method stub
		return diseaseInfoDao.queryCnr(cbm);
	}
	/**
	 * 搜索疾病信息
	 */
	public List<DiseaseInfo> queryByStr(String str){		
		return diseaseInfoDao.queryByStr(str);
	}
	/**
	 * 搜索疾病名称
	 */
	public List<DiseaseInfo> queryCmc(String str) {
		// TODO Auto-generated method stub
		return diseaseInfoDao.queryCmc(str);
	}
	/**
	 * 
	 * 信息转化成json对象
	 * 
	 */
	public String queryByPage(String str, QueryInfo queryInfo) {
			PageObject<DiseaseInfo> p=diseaseInfoDao.queryByPage(str, queryInfo);
			JSONObject jo=new JSONObject();
			
			jo.put("total",p.getTotalRecord());
			jo.put("infoList", JsonUtils.getJsonString4JavaList(p.getDatas()));
			
			return jo.toString();
	}
	/**
	 * andriod 接口返回 String
	 */
	public String queryMsgAndriod(String cbm) {
		List<DiseaseInfo> listInfo = queryCnr(cbm);
		JSONObject jo = new JSONObject();
		String json = null ;
		
		if(listInfo.size()>0){
			jo.put("msg","数据库请求成功 ");
			jo.put("code",Constant.INTERFACE_SUCC);
			listInfo.get(0).setCnr(listInfo.get(0).getCnr().replaceAll(" +", "<br><br>"));
				jo.put("data",JsonUtils.getJsonString4JavaList(listInfo));
				json=jo.toString();		
		}else{
			jo.put("msg","数据库不存在疾病信息");
			jo.put("code",Constant.EXPENSE_SERVICE_FAIL);
			json=jo.toString();
			
		}
		return json;
	}
}
