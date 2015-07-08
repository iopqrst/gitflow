package com.bskcare.ch.service;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.MessageFlow;

public interface MessageFlowService {

	public MessageFlow add(MessageFlow flow);

	public void delete(Integer id);

	public void modify(MessageFlow flow);

	public PageObject<MessageFlow> queryMessageFlow(QueryInfo queryInfo,
			MessageFlow flow);

	public void automaticallyPushMsg();

	/**
	 * 获取非读通知个数
	 */
	String queryUnreadCount(Integer clientId);

	/**
	 * 通知列表
	 */
	String updateQueryMessages(Integer clientId);

	String markUnreadMsg(Integer clientId, Integer id);

	public String deleteMsg(Integer cid, Integer msgId);
}
