package com.bskcare.ch.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.TgAgentUserDao;
import com.bskcare.ch.dao.UserInfoDao;
import com.bskcare.ch.service.TgAgentUserService;
import com.bskcare.ch.util.Base64;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.TgAgentUser;
@Service
public class TgAgentUserServiceImpl implements TgAgentUserService{

	@Autowired
	TgAgentUserDao tgAgentUserDao;
	@Autowired
	UserInfoDao userInfoDao;
	@Autowired
	TgAgentUserDao agentUserDao;
	@Autowired
	ClientInfoDao clientInfoDao;
	
	protected transient final Logger log = Logger.getLogger(getClass());
	
	public TgAgentUser add(TgAgentUser TgAgentUser) {
		return tgAgentUserDao.add(TgAgentUser);
	}
	
	public String findTgAgentUser(String url, Integer type) {
		return tgAgentUserDao.findTgAgentUser(url,type);
	}

	public void tgAgentUserRegister(Integer minAreaId,String agent,String url,String category,ClientInfo vClient) throws NumberFormatException, IOException {
		// 解密类型和代理商所属用户参数
		int type = -1;
		
		try {
			type = StringUtils.isEmpty(category) ? -1 : Integer.parseInt(Base64.decode(category));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 代理商、代理商所属用户、社会人员记录
		if (type == 4 && !"".equals(agent) && !"".equals(url) && null != vClient) {
			log.info(LogFormat.f("进入推广来源信息代理商所属用户记录..."));
			if (null != minAreaId) {
				// 保存推广来源信息
				TgAgentUser ag = save(url,agent,type,vClient); 
				log.info(">>>>>>>>>> 代理商所属区域>>>>推广用户[" + ag.getName() + "]已注册");
			} else {
				log.info(">>>>>>>>>> 代理商不存在");
			}
		}

		if (type == 5 && !"".equals(url) && null != vClient) {
			log.info(LogFormat.f("进入推广来源信息社会人员记录..."));
			// 保存推广来源信息
			TgAgentUser ag = save(url,agent,type,vClient);
			log.info(">>>>>>>>>> 推广来源>>>>社会人员[" + ag.getName() + "]已注册");
		}
	}

	public TgAgentUser save(String url,String agent,int type,ClientInfo vClient) {
		String name = StringUtils.isEmpty(vClient.getAccount()) ? vClient.getMobile() : vClient.getAccount(); 
		TgAgentUser auInfo = new TgAgentUser(name, url, new Date(), type);
		TgAgentUser ag = agentUserDao.add(auInfo);
		return ag;
	}

	public List<TgAgentUser> queryAgentUserByTime(Date createTime) {
		return agentUserDao.queryAgentUserByTime(createTime);
	}
}
