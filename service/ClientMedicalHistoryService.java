package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.client.ClientMedicalHistory;

/**
 * 用户疾病史
 * 
 */
public interface ClientMedicalHistoryService {

	void saveOrUpdate(ClientMedicalHistory clp);

	ClientMedicalHistory getClientMedicalHistory(Integer clientId);

	/**
	 * 修改个人既往病史android接口
	 * 
	 * @param cd
	 */
	public void saveOrUpdateAndroid(ClientMedicalHistory cd);

	/**
	 * 全查
	 */
	public List<ClientMedicalHistory> queryAll();

	/**
	 * 查询单个对象
	 */
	public ClientMedicalHistory findById(String diseaseName);

	/**
	 * 黄金档案更新过敏信息
	 */
	public void saveArchiveAllergy(String[] strs, Integer cid);
}
