package com.bskcare.ch.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.FamilyRelation;

public interface ClientFamilyService {
	/***
	 * 查询出用户关系页面
	 * @param familyRelation
	 * @param queryInfo
	 * @return
	 */
	List getFamilyList(FamilyRelation familyRelation, QueryInfo queryInfo) ;
	/***
	 * 添加信息
	 * @param familyRelation
	 * @param clientInfo
	 * @throws UnsupportedEncodingException 
	 */
	int addFamily(FamilyRelation familyRelation, ClientInfo clientInfo,ClientInfo myInfo) throws UnsupportedEncodingException;
	/**
	 * 修改 亲情号码
	 * @param familyRelation
	 */
	void updateFamily(FamilyRelation familyRelation);
	/***
	 * 删除亲情号码
	 * @param familyRelation
	 */
	void deleteFamily(FamilyRelation familyRelation);
	/**
	 * 管理亲情号码
	 * @param queryInfo
	 * @return
	 */
	PageObject getManegeFamily(FamilyRelation familyRelation,QueryInfo queryInfo);
	
	/**把三个对象的集合数组 转换为JSON格式的信息
	Map entities = new LinkedHashMap();
	entities.put("tt", ClientInfo.class);
	entities.put("tf", FamilyRelation.class);
	entities.put("tp", OrderProduct.class);
	
	 * @param list
	 * @return
	 */
	String getFamilyList(List list);	
}
