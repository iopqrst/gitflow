package com.bskcare.ch.service;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.ClientPhysical;

public interface ClientPhysicalService {
	
	/**
	 * 查询所有的客户体检记录信息
	 */
	public PageObject<ClientPhysical> findClientPhysical(Integer clientId,QueryInfo queryInfo);
	
	/**
	 * 添加客户体检记录信息
	 */
	public void addClientPhysical(ClientPhysical physical,String details);
	
	/**
	 * 根据体检id和客户id查询体检信息
	 */
	public ClientPhysical findClientPhysicalById(Integer id,Integer clientId);
	
	
	/**
	 * 查询某个客户某个体检记录的详细信息
	 */
	public ClientPhysical findPhysicalItemById(Integer clientId,Integer physicalId);
	

}
