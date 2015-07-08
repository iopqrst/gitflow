package com.bskcare.ch.dao;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.TgAgentUser;

public interface TgAgentUserDao extends BaseDao<TgAgentUser>{

	/**
	 * 查询推广来源用户是否已注册
	 */
	public String findTgAgentUser(String url, Integer type);
	/**
	 * 
	 */
	public List<TgAgentUser> queryAgentUserByTime(Date createTime);
}
