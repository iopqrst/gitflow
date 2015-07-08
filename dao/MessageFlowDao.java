package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.MessageFlow;
import com.bskcare.ch.vo.MessagePush;

public interface MessageFlowDao extends BaseDao<MessageFlow> {

	PageObject<MessageFlow> queryMessageFlow(QueryInfo queryInfo,
			MessageFlow flow);

	List<MessageFlow> getMessageFlowList();

	int createMessagePush(String sqldata);

	int queryUnReadMessages(Integer clientId);

	List<MessagePush> queryMessages(Integer clientId);

	public int updateMsgStatus(int status, Integer clientId, Integer id) ;

	/**
	 * 更新客户消息状态
	 */
	public int updateMsgStatus(int status, Integer clientId);
}
