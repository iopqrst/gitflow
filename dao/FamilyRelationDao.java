package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.bo.FamilyName;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.FamilyRelation;

public interface FamilyRelationDao {
	/**
	 * client查出 亲情号码及其关系
	 * @param familyRelation
	 * @param queryInfo
	 * @return
	 */
	PageObject getFamilyList(FamilyRelation familyRelation, QueryInfo queryInfo) ;
	/**
	 * 增加亲情关系
	 * @param familyRelation
	 * @return
	 */
	FamilyRelation addFamilyRelation(FamilyRelation familyRelation) ;
	/**
	 * 修改亲戚号码内容
	 * @param familyRelation
	 */
	void updateFamily(FamilyRelation familyRelation);
	/**
	 * 删除亲情号码
	 * @param familyRelation
	 */
	void deleteFamily(FamilyRelation familyRelation);
	/**
	 * 通过clientInfo获得所关联的  亲情号码  并且是要发短信的人
	 * @param clientInfo
	 * @return
	 */
	List<FamilyName> getFamilyByClientInfo(ClientInfo clientInfo);
	/**
	 * 获得被绑定人的亲情号码列表
	 * @param clientInfo
	 * @return
	 */
	public List<FamilyName> getFamilyByfamilyId(ClientInfo clientInfo) ;
}
