package com.bskcare.ch.service;

import com.bskcare.ch.vo.ClientInfo;

public interface CrmClientInfoService {
	/**
	 * 增加用户和crm同步
	 */
	public void addCrmClientInfo(ClientInfo clientInfo);
	
	
	/**
	 * 修改用户信息是和crm同步
	 */
	public void updateCrmClientInfo(ClientInfo clientInfo,String source);
}
