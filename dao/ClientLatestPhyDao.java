package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientLatestPhy;

/**
 * 用户最近一次体检数据
 * 
 * @author houzhiqing
 */
public interface ClientLatestPhyDao extends BaseDao<ClientLatestPhy> {
	
	public ClientLatestPhy getClientLastestPhy(Integer clientId);
	
	public void updateClientLatestPhy(ClientLatestPhy phy);
	
	public void updateLatestPhy(String data, int type, Integer cid);
}
