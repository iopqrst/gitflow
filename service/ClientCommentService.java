package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientComment;

@SuppressWarnings("unchecked")
public interface ClientCommentService {

	
	/**
	 * 查询用户id查询的备注信息
	 */
	public List<ClientComment> queryClinetComment(ClientComment comment);
	
	
	/**
	 * 根据用户id给用户添加用户备注
	 */
	public void addClientComment(ClientComment comment);
	
	public List queryClinetCommentUserName(Integer clientId,ClientComment comment);
	
	public PageObject queryClientCommentUserInfo(ClientComment comment,QueryInfo queryInfo);
	
	/**查询用户备注**/
	public String ajaxQueryClientComment(Integer clientId,ClientComment comment);
	
	public String queryClientComment(ClientComment comment);
}
