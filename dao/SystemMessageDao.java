package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.SystemMessageExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.SystemMessage;

public interface SystemMessageDao extends BaseDao<SystemMessage>{
	
	/**
	 * 站内信息分页
	 */
	public PageObject<SystemMessage> findMessage(QueryInfo info,SystemMessage message);
	/**
	 * 根据用户Id查询查询站内信息
	 */
	public PageObject<SystemMessageExtend> findMessageByClientId(QueryInfo info,ClientInfo clientInfo,SystemMessageExtend message);
	/**
	 * 根据用户Id查询未读消息条数
	 */
	public Integer findNoReadMessageCount(ClientInfo clientInfo,SystemMessageExtend message);
	
	/**
	 * 公告牌 
	 */
	public PageObject<SystemMessage> findBulletin(QueryInfo queryInfo);
}
