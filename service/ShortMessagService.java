package com.bskcare.ch.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ShortMessage;

public interface ShortMessagService extends BaseDao<ShortMessage> {
	/**
	 * 发送短信  并且 存入数据库记录
	 * @param shortMessage
	 */
	int sendMessage(ShortMessage shortMessage, String count);
	/**
	 * 保存发送记录
	 * @param shortMessage
	 */
	ShortMessage addShortMessag(ShortMessage shortMessage,ClientInfo clientInfo, String source) throws UnsupportedEncodingException;
	/**
	 * 获取短信列表
	 * @param shortMessage
	 * @return
	 */
	PageObject getShortMessag(ShortMessage shortMessage,QueryInfo queryInfo);
	/**
	 * 取消发送
	 * @param shortMessage
	 */
	void canleSend(ShortMessage shortMessage);
	/**
	 * 修改短信
	 * @param shortMessage
	 * @return
	 */
	void updateShortMessage(ShortMessage shortMessage);
	/**
	 * 我的短信
	 * @param shortMessage
	 * @param queryInfo
	 * @return
	 */
	String myShortMessge(ShortMessage shortMessage, QueryInfo queryInfo);
	/**
	 * 执行定时任务短信
	 * @throws UnsupportedEncodingException 
	 */
	void sendTimerMSM();
	/**
	 * 获得今天发送的手机号的数量
	 * @param mobile
	 * @return
	 */
	int getMobileSum(String mobile);
	
	/**
	 * 查询用户的短信信息
	 * @param clientId
	 * @return
	 */
	public List<ShortMessage> queryShortMessage(Integer clientId);
	
	/**
	 * 发送推送信息，保存数据库
	 */
	public void addShortMessage(ShortMessage shortMessage);
	
	/**
	 * 查询短信通过手机号
	 * @author mayi
	 * @version 2014-11-4  下午05:37:44
	 * @param clientSms
	 * @return
	 */
	public PageObject queryShortMessageByMobile(ShortMessage shortMessage,QueryInfo queryInfo);
}
