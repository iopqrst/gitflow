package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.ClientHobby;

/**
 * 用户习惯及嗜好
 * 
 * @author houzhiqing
 */
public interface ClientHobbyDao extends BaseDao<ClientHobby> {
	public ClientHobby getClientHobby(Integer clientId);
}
