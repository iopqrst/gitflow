package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientDiseaseFamily;


public interface ClientDiseaseFamilyDao extends BaseDao<ClientDiseaseFamily>{
	
	/**根据用户id查询用户家族病史**/
	public List<Object> queryDiseaseFamily(Integer clientId);
	/**添加家族疾病*/
	public void saveDiseaseFamily(ClientDiseaseFamily clientDiseaseFamily); 
	/**修改家族疾病*/
	public void updateDiseaseFamily(ClientDiseaseFamily clientDiseaseFamily); 
	/**根据用户id查询用户的疾病*/
	public List<ClientDiseaseFamily> queryDiseaseFamilyByClientId(Integer clientId);
	public List queryInitDiseaseFamilyByClientId(Integer clientId);
	public List<ClientDiseaseFamily> queryDiseaseFamilyByClientId(Integer clientId , Integer familyType);
	/**
	 * 根据cid删除用户所有家族疾病
	 */
	public void deleteDiseaseByCid(Integer cid);
	
	/**
	 * 删除用户的自定义疾病
	 * @param familyType 类型 
	 */
	
	public ClientDiseaseFamily queryDiseaseFamily(ClientDiseaseFamily diseaseFamily);
	
	public void deleteDiseaseFamily(ClientDiseaseFamily diseaseFamily);
	/**
	 * 根据家庭类型删除选择的家族疾病
	 */
	public void deleteOtherDisease(Integer cid, Integer familyType,Integer type);
	/**
	 * 根据id familytype删除其他疾病
	 */
}
