package com.bskcare.ch.service;

import com.bskcare.ch.bo.SystemMessageExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.SystemMessage;

public interface SystemMessageService {

	/**
	 * 增加
	 */
	public SystemMessage save(SystemMessage s);

	/**
	 * 站内信息分页
	 */
	public PageObject<SystemMessage> findMessage(QueryInfo info,
			SystemMessage message);

	/**
	 * 根据用户Id查询查询站内信息
	 */
	public String findMessageByClientId(QueryInfo info, ClientInfo clientInfo,
			SystemMessageExtend message);

	/**
	 * 逻辑删除(修改)
	 */
	public void updateSystemMessage(SystemMessage s);

	/**
	 * 查询单个对象
	 */
	public SystemMessage findSystemMessageById(Integer smId);

	/**
	 * 查询未读消息的条数
	 */
	public Integer findNoReadMessageCount(ClientInfo clientInfo,
			SystemMessageExtend message);

	/**
	 * 公告牌
	 */
	public String findBulletin(QueryInfo queryInfo);
	
	/**
	 * 公告分页
	 */
	public PageObject<SystemMessage> findBulletinMore(QueryInfo queryInfo);
}
