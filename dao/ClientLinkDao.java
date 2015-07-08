package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.ClientLink;

public interface ClientLinkDao extends BaseDao<ClientLink>{
	
	/**
	 * 客户添加联系人
	 */
	public void addClientLink(ClientLink link);
	
	/**
	 * 根据客户id和联系人id查询联系人的信息
	 */
	public ClientLink findLinkByClientLinkId(Integer id,Integer clientId);
	
	/**
	 * 根据客户id查询他所拥有的联系人
	 */
	public PageObject<ClientLink> findClientLink(Integer clientId,QueryInfo info);

	/**
	 * 根据客户id和当前联系人id删除联系人
	 */
	public void deleteClientLink(Integer id,Integer clientId);
	
	/**
	 * 根据客户id和当前联系人id修改联系人信息
	 */
	public void updateClientLink(ClientLink clientLink);
}
