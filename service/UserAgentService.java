package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.UserAgent;

@SuppressWarnings("unchecked")
public interface UserAgentService {
	/**
	 * 根据管理员id查询管理员代理人
	 */
	public List<UserAgent> queryUserAgent(Integer id);
	
	/**
	 * 查询所有的代理信心
	 */
	public PageObject queryUserAgentInfo(UserAgent agent,QueryInfo queryInfo);
	
	/**
	 * 取消用户代理
	 */
	public void delUserAgent(UserAgent agent);
	
	/**
	 * 添加用户代理
	 */
	public void saveUserAgent(UserAgent agent,String agentUserIds);
}
