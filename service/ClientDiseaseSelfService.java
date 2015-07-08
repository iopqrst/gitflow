package com.bskcare.ch.service;


import com.bskcare.ch.vo.client.ClientDiseaseSelf;

public interface ClientDiseaseSelfService {

	/**
	 * 增加
	 */
	public void add(ClientDiseaseSelf clientDiseaseSelf);

	
	/**根据客户id查询客户所有的疾病信息**/
	public String queryDiseaseSelf(Integer clientId);

	/**
	 * 根据json添加
	 */
	public void addOrUpdateByJson(String json,Integer cid);
	/**
	 * 根据用户id获得疾病信息，返回jsonString
	 * @throws Exception 
	 */
	public String queryDiseaseSelfByClientId(Integer cid) throws Exception ;
	public String queryDiseaseSelfByClientIdMobile(Integer cid) throws Exception ;
	/**
	 * 根据cid删除用户所有个人疾病
	 */
	public void deleteDiseaseByCid(Integer cid);
	/**
	 * 根据cid删除用户其他个人疾病
	 */
	public void deleteOtherDiseaseByCid(Integer cid);
	
	
	public void updateDiseaseSelf(ClientDiseaseSelf diseaseSelf,int type);
	/**
	 * 保存黄金档案个人疾病信息
	 */
	public void updateDiseaseSelf(String[] self , Integer cid);
	/**
	 * 保存用户其他疾病
	 */
	public void updateDiseaseSelfOther(ClientDiseaseSelf clientDiseaseSelf,Integer cid);
	/**
	 * 查询用户的其他疾病
	 * @param cid
	 * @return
	 */
	public ClientDiseaseSelf queryDiseaseSelfOther(Integer cid);
}
