package com.bskcare.ch.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.SystemMessageExtend;
import com.bskcare.ch.dao.SystemMessageDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.SystemMessageService;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.SystemMessage;

@Service
public class SystemMessageServiceImpl implements SystemMessageService {

	@Autowired
	private SystemMessageDao systemMessageDao;

	public SystemMessage save(SystemMessage s) {
		return systemMessageDao.add(s);
	}

	public PageObject<SystemMessage> findMessage(QueryInfo info,
			SystemMessage message) {
		return systemMessageDao.findMessage(info, message);
	}

	public String findMessageByClientId(QueryInfo info, ClientInfo clientInfo,
			SystemMessageExtend message) {
		int count = systemMessageDao
				.findNoReadMessageCount(clientInfo, message);
		PageObject<SystemMessageExtend> pager = systemMessageDao
				.findMessageByClientId(info, clientInfo, message);
		List<SystemMessageExtend> messageExtendlist = pager.getDatas();
		JSONObject json = new JSONObject();
		json.put("total", pager.getTotalRecord());
		json.put("messageExtendlist", JsonUtils
				.getJsonString4JavaListDate(messageExtendlist));
		json.put("noreadcount", count);
		return json.toString();
	}

	public void updateSystemMessage(SystemMessage s) {
		systemMessageDao.update(s);
	}

	public SystemMessage findSystemMessageById(Integer smId) {
		return systemMessageDao.load(smId);
	}

	public Integer findNoReadMessageCount(ClientInfo clientInfo,
			SystemMessageExtend message) {
		return systemMessageDao.findNoReadMessageCount(clientInfo, message);
	}

	public String findBulletin(QueryInfo queryInfo) {
		queryInfo.setPageSize(4);
		PageObject<SystemMessage> pager = systemMessageDao.findBulletin(queryInfo);
		List<SystemMessage> bulletinlist = pager.getDatas();
		JSONObject json = new JSONObject();
		json.put("bulletinlist", JsonUtils.getJsonString4JavaListDate(bulletinlist));
		return json.toString();
	}

	public PageObject<SystemMessage> findBulletinMore(QueryInfo queryInfo) {
		return systemMessageDao.findBulletin(queryInfo);
	}

}
