package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.UserAgentDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.UserAgentService;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.UserAgent;

@Service
@SuppressWarnings("unchecked")
public class UserAgentServiceImpl implements UserAgentService{
	
	@Autowired
	private UserAgentDao agentDao;
	
	public List<UserAgent> queryUserAgent(Integer id){
		if(id != null){
			List<UserAgent> lstAgent = agentDao.queryUserAgent(id);
			return lstAgent;
		}
		return null;
	}
	
	public PageObject queryUserAgentInfo(UserAgent agent,QueryInfo queryInfo){
		return agentDao.queryUserAgentInfo(agent, queryInfo);
	}
	
	/**
	 * 取消用户代理
	 */
	public void delUserAgent(UserAgent agent){
		if(agent != null){
			agentDao.delUserAgent(agent);
		}
	}
	
	public void saveUserAgent(UserAgent agent,String agentUserIds){
		if(agent != null &&!StringUtils.isEmpty(agentUserIds)){
			Integer userId = agent.getUserId();
			agentDao.deleteUserAgentByUserId(userId);
			
			String [] ids = agentUserIds.split(",");
			for (String agentId : ids) {
				UserAgent userAgent = new UserAgent();
				userAgent.setUserId(agent.getUserId());
				if(!StringUtils.isEmpty(agentId)){
					Integer agentUserId = Integer.parseInt(agentId);
					userAgent.setAgentUserId(agentUserId);
				}
				userAgent.setCreateTime(new Date());
				agentDao.add(userAgent);
			}
		}
	}
}
