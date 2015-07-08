package com.bskcare.ch.dao;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ShortMessage;

public interface ShortMessageDao extends BaseDao<ShortMessage> {
	/**
	 * 获得短信
	 * @return
	 */
	public PageObject getShortMessage(ShortMessage shortMessage,QueryInfo queryInfo) ;
	/***
	 * 添加短信记录
	 * @param shortMessage
	 */
	public ShortMessage addShortMessage(ShortMessage shortMessage) ;
	/**
	 * 发送信息
	 * @param shortMessage
	 * @param clientInfo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
//	public int sendMessag(ShortMessage shortMessage,ClientInfo clientInfo) throws UnsupportedEncodingException ;
	/**
	 * 修改短信状态
	 * @param shortMessage
	 */
	public void updateShortMessage(ShortMessage shortMessage);
	/**
	 * 获得未发送短信
	 * @return
	 */
	public List<ShortMessage> getNotSendMessage();
	/**
	 * 获得发送手机号的数量
	 * @param mobile
	 * @return
	 */
	public Object getMobileSum(String mobile);
	
	/**
	 * 查询给用户已发送的短信
	 * @param clientId
	 * @return
	 */
	public List<ShortMessage> queryShortMessage(Integer clientId);
	
	/**
	 * 查询邮件内容通过手机号
	 * @author mayi
	 * @version 2014-11-4  下午05:37:44
	 * @param clientSms
	 * @return
	 */
	public PageObject selectShortMessageByMobile(String  mobile,QueryInfo queryInfo);
	
	
	public List<ShortMessage> queryDoctorInviteClientSms(String type, String mobile);
}
