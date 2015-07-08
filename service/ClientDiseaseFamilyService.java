package com.bskcare.ch.service;

import com.bskcare.ch.vo.client.ClientDiseaseFamily;

public interface ClientDiseaseFamilyService {

	/**
	 * 增加
	 */
	public void add(ClientDiseaseFamily clientDiseaseFamily);

	
	public String queryDiseaseFamily(Integer clientId);

	/**
	 * 根据json添加
	 */
	public void addOrUpdateByJson(String json,Integer cid);
	/**
	 * 根据用户id获得疾病信息，返回jsonString
	 * @throws Exception 
	 */
	public String queryDiseaseFamilyByClientId(Integer cid) throws Exception ;
	/**
	 * 根据用户id获得疾病信息，返回jsonString 移动端接口用
	 * @throws Exception 
	 */
	public String queryDiseaseFamilyByClientIdMobile(Integer cid) throws Exception ;
	/**
	 * 根据cid删除用户所有家族疾病
	 */
	public void deleteDiseaseByCid(Integer cid);
	
	public void updateDiseaseFamily(ClientDiseaseFamily family,int type);
	/**黄金档案更新家族疾病*/
	public void updateDiseaseFamily(String[] diseases, Integer familyType,
			Integer cid);
	/**黄金档案更新其他家族疾病*/
	public void updateDiseaseFamilyOther(String[] familyType,ClientDiseaseFamily diseaseFamily,Integer cid);
	/**
	 * 根据id familytype删除其他疾病
	 */
	public void deletefamilyOther(Integer id,Integer familyType,Integer type);
}
