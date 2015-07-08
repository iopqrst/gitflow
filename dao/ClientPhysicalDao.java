package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.ClientPhysical;

public interface ClientPhysicalDao extends BaseDao<ClientPhysical>{
	
	/**
	 * 查询所有的客户体检记录信息
	 */
	public PageObject<ClientPhysical> findClientPhysical(Integer clientId,QueryInfo queryInfo);
	
	/**
	 * 添加客户体检记录信息
	 */
	public void addClientPhysical(ClientPhysical physical);
	
	/**
	 * 根据体检id和客户id查询体检信息
	 */
	public ClientPhysical findClientPhysicalById(Integer id,Integer clientId);
	
	public List<ClientPhysical> queryPhysical(Integer clientId) ;
	
	
}
