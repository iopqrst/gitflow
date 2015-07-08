package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientComment;

@SuppressWarnings("unchecked")
public interface ClientCommentDao extends BaseDao<ClientComment>{
	
	/**
	 * 查询用户id查询的备注信息
	 */
	public List<ClientComment> queryClinetComment(ClientComment comment);
	
	public List queryClinetCommentUserName(Integer clientId,ClientComment comment);
	
	public PageObject queryClientCommentUserInfo(ClientComment comment,QueryInfo queryInfo);
}
