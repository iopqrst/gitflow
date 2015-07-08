package com.bskcare.ch.service;

import com.bskcare.ch.vo.client.ClientLatestPhy;

/**
 * 用户最近一次体检记录
 * @author Administrator
 *
 */
public interface ClientLatestPhyService {

	void saveOrUpdate(ClientLatestPhy clp);
	ClientLatestPhy getClientLatestPhy(Integer clientId);
	
	/**
	 * 修改一次体检记录  android接口
	 * @param cd
	 */
	public void saveOrUpdateAndroid(ClientLatestPhy cd);
	
	/**
	 * 修改一次体检记录运动数据部分
	 * @param cd
	 */
	public void saveOrUpdateSport(ClientLatestPhy cd);
	
}
