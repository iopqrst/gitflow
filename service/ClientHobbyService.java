package com.bskcare.ch.service;

import com.bskcare.ch.vo.client.ClientHobby;

/**
 * 用户习惯及嗜好
 * @author Administrator
 *
 */
public interface ClientHobbyService {

	void saveOrUpdate(ClientHobby cd);
	ClientHobby getClientHobby(Integer clientId);
	
	/**
	 * 修改健康档案之生活习惯android接口
	 * @param cd
	 */
	public void saveOrUpdateAndroid(ClientHobby cd);
	/**
	 * 保持运动信息统一处理
	 * @param newStr
	 * @param oldStr
	 * @return
	 */
	public String sportTypeSole(String newStr , String oldStr);
	
	public String queryClientHobby(Integer cid);
}
