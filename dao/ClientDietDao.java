package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientDiet;

/**
 * 用户饮食习惯
 * 
 * @author houzhiqing
 */
public interface ClientDietDao extends BaseDao<ClientDiet> {
	public ClientDiet getClientDiet(Integer clientId);
}
