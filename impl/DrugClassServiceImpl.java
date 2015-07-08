package com.bskcare.ch.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.DrugClassDao;
import com.bskcare.ch.service.DrugClassService;
import com.bskcare.ch.service.FlVoService;
import com.bskcare.ch.service.XyVoService;
import com.bskcare.ch.service.ZcyVoService;
import com.bskcare.ch.service.Zy1VoService;
import com.bskcare.ch.service.ZyVoService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.FamilyRelation;
import com.bskcare.ch.vo.search.DiseaseClass;
import com.bskcare.ch.vo.search.DrugClass;
import com.bskcare.ch.vo.search.FlVo;
import com.bskcare.ch.vo.search.XyVo;
import com.bskcare.ch.vo.search.ZcyVo;
import com.bskcare.ch.vo.search.Zy1Vo;
import com.bskcare.ch.vo.search.ZyVo;
@Service("drugClassService")
public class DrugClassServiceImpl implements DrugClassService {
	
	@Autowired
	private DrugClassDao drugClassDao;
	@Autowired
	private FlVoService flVoService;
	@Autowired
	private XyVoService XyVoService;
	@Autowired
	private ZcyVoService zcyVoService;
	@Autowired
	private Zy1VoService zy1VoService;
	@Autowired
	private ZyVoService zyVoService;
	public List<DrugClass> queryAll(){
		
		return drugClassDao.queryAll();
	}
	public List<DrugClass> queryByCbm(String cbm) {
		// TODO Auto-generated method stub
		return drugClassDao.queryByCbm(cbm);
	}
	public List<DrugClass> queryByOne(String cbm){
		
		return drugClassDao.queryByOne(cbm);
	}
	/**
	 * 查看药品信息
	 */
	public String queryByIdMsgAndriod(String t,String id) {
		String json=null;
		JSONObject jo = new JSONObject();
		if(!StringUtils.isEmpty(t)){
			if (t.equals("1")) {
				List<XyVo> list1 = XyVoService.queryByIdMsg(id);
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",JsonUtils.getJsonString4JavaList(list1));
				json=jo.toString();
			} else if (t.equals("2")) {
				 List<ZcyVo> list2 = zcyVoService.queryByIdMsg(id);
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",JsonUtils.getJsonString4JavaList(list2));
				json=jo.toString();
				
			} else if (t.equals("3")) {
	
				List<Zy1Vo> list3 = zy1VoService.queryByIdMsg(id);	
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",JsonUtils.getJsonString4JavaList(list3));
				json=jo.toString();
			} else if (t.equals("4")) {
				List<ZyVo> list4 = zyVoService.queryByIdMsg(id);
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",JsonUtils.getJsonString4JavaList(list4));
				json=jo.toString();
			} else if (t.equals("5")) {
				List<FlVo> list5 = flVoService.queryByIdMsg(id);
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",JsonUtils.getJsonString4JavaList(list5));
				json=jo.toString();
			} else {
				jo.put("msg","数据库不存在相关药品信息");
				jo.put("code",Constant.INTERFACE_FAIL);
				json=jo.toString();
			}
		}else{
			jo.put("msg","参数有误");
			jo.put("code",Constant.INTERFACE_FAIL);
			jo.put("data","");
			json=jo.toString();
		}
		return json;
	}
	/**
	 * 查看药品分类
	 */
	public String queryByCbmAndriod(String t, String cbm) {
		// TODO Auto-generated method stub
		String json=null;
		JSONObject jo = new JSONObject();
		if(Integer.parseInt(t)>=1&&Integer.parseInt(t)<=5){
		if (!t.equals("3")) {
			List<DrugClass> list = queryByCbm(cbm);
			if (list.size() > 1) {
				list.remove(0);
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",JsonUtils.getJsonString4JavaList(list));
				json=jo.toString();

			} else {
				if(list.size()==1){
				if (list.get(0) != null) {
					if (t.equals("1")) {
						List<XyVo> list1 = XyVoService.queryByCbm(cbm);
						
						if(list1.size()>0){
							//拼接JSON
							JSONArray pArray = new JSONArray();
							for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
								XyVo xyVo = (XyVo) iterator.next();
								JSONObject datajson = new JSONObject();
								datajson.put("id", xyVo.getId()) ;
								datajson.put("cbm", xyVo.getCypbm()) ;
								datajson.put("cmc", xyVo.getCypmc()) ;
								datajson.put("dfl", 0) ;
								datajson.put("ifljb", 0) ;
								pArray.add(datajson) ;
							}
							jo.put("msg","请求成功 ");
							jo.put("code",Constant.INTERFACE_SUCC);
							jo.put("data",pArray);							
														
							json=jo.toString();
							
							}else{
								jo.put("msg","数据库中没有子菜单");
								jo.put("code",Constant.INTERFACE_FAIL);
								json=jo.toString();
							}
					} else if (t.equals("2")) {
						List<ZcyVo> list2 = zcyVoService.queryByCbm(cbm);
						
						if(list2.size()>0){
							//拼接JSON
							JSONArray pArray = new JSONArray();
							for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
								ZcyVo xyVo = (ZcyVo) iterator.next();
								JSONObject datajson = new JSONObject();
								datajson.put("id", xyVo.getId()) ;
								datajson.put("cbm", xyVo.getCypbm()) ;
								datajson.put("cmc", xyVo.getCypmc()) ;
								datajson.put("dfl", 0) ;
								datajson.put("ifljb", 0) ;
								pArray.add(datajson) ;
							}
							jo.put("msg","请求成功 ");
							jo.put("code",Constant.INTERFACE_SUCC);
							jo.put("data",pArray);		
							json=jo.toString();
							
							}else{
								jo.put("msg","数据库中没有子菜单");
								jo.put("code",Constant.INTERFACE_FAIL);
								json=jo.toString();
								
							}
					} else if (t.equals("5")) {
					
						List<FlVo> list5 = flVoService.queryByCbm(cbm);
						if(list5.size()>0){
							//拼接JSON
							JSONArray pArray = new JSONArray();
							for (Iterator iterator = list5.iterator(); iterator.hasNext();) {
								FlVo xyVo = (FlVo) iterator.next();
								JSONObject datajson = new JSONObject();
								datajson.put("id", xyVo.getId()) ;
								datajson.put("cbm", xyVo.getCypbm()) ;
								datajson.put("cmc", xyVo.getCypmb()) ;
								datajson.put("dfl", 0) ;
								datajson.put("ifljb", 0) ;
								pArray.add(datajson) ;
							}
							jo.put("msg","请求成功 ");
							jo.put("code",Constant.INTERFACE_SUCC);
							jo.put("data",pArray);		
							
							json=jo.toString();
							}else{
								jo.put("msg","数据库中没有子菜单");
								jo.put("code",Constant.INTERFACE_FAIL);
								json=jo.toString();
								
							}
					} else if (t.equals("4")) {
						List<ZyVo> list4 = zyVoService.queryByCbm(cbm);
						if(list4.size()>0){
							//拼接JSON
							JSONArray pArray = new JSONArray();
							for (Iterator iterator = list4.iterator(); iterator.hasNext();) {
								ZyVo xyVo = (ZyVo) iterator.next();
								JSONObject datajson = new JSONObject();
								datajson.put("id", xyVo.getId()) ;
								datajson.put("cbm", xyVo.getCypbm()) ;
								datajson.put("cmc", xyVo.getCypmc()) ;
								datajson.put("dfl", 0) ;
								datajson.put("ifljb", 0) ;
								pArray.add(datajson) ;
							}
							jo.put("msg","请求成功 ");
							jo.put("code",Constant.INTERFACE_SUCC);
							jo.put("data",pArray);		
							json=jo.toString();
						}else{
							jo.put("msg","数据库中没有子菜单");
							jo.put("code",Constant.INTERFACE_FAIL);
							json=jo.toString();
							
						}
					}
				}
				}
			}
		} else {
			List<Zy1Vo> list3 = zy1VoService.queryByCbm(cbm);
			if(list3.size()>0){
				//拼接JSON
				JSONArray pArray = new JSONArray();
				for (Iterator iterator = list3.iterator(); iterator.hasNext();) {
					Zy1Vo xyVo = (Zy1Vo) iterator.next();
					JSONObject datajson = new JSONObject();
					datajson.put("id", xyVo.getId()) ;
					datajson.put("cbm", xyVo.getCypbm()) ;
					datajson.put("cmc", xyVo.getCypmc()) ;
					datajson.put("dfl", 0) ;
					datajson.put("ifljb", 0) ;
					pArray.add(datajson) ;
				}
				jo.put("msg","请求成功 ");
				jo.put("code",Constant.INTERFACE_SUCC);
				jo.put("data",pArray);	
				
				json=jo.toString();
				}else{
					jo.put("msg","数据库中没有子菜单");
					jo.put("code",Constant.INTERFACE_FAIL);
					json=jo.toString();
					
				}
		}
		}else{
			jo.put("msg","数据库中没有对应表");
			jo.put("code",Constant.INTERFACE_FAIL);
			json=jo.toString();			
		}
		return json;
	}
	public String queryMenuByT(String aaa) {
		List<DrugClass> list = drugClassDao.queryMenuByT(aaa) ;
		
		JSONObject jo = new JSONObject();
		jo.put("msg","请求成功 ");
		jo.put("code",Constant.INTERFACE_SUCC);
		jo.put("data",JsonUtils.getJsonString4JavaList(list));
		
		return jo.toString();
	}
}
