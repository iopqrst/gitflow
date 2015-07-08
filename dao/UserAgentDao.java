package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.UserAgent;

@SuppressWarnings("unchecked")
public interface UserAgentDao extends BaseDao<UserAgent>{
	
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
	 * 根据用户id删除用户代理信息
	 * @param userId
	 */
	public void deleteUserAgentByUserId(Integer userId);
	
}
