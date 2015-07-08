package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.PhysicalItem;

public interface PhysicalItemDao extends BaseDao<PhysicalItem>{
	
	/**
	 * 添加信息
	 */
	public void addPhysicalItem(PhysicalItem physicalItem);

	/**
	 * 根据用户id和指标id查询某个指标
	 */
	public List<PhysicalItem> findPhysicalItemByid(Integer clientId,Integer pdId);
	
	public List<PhysicalItem> findPhysicalItemBycId(Integer clientId);
	
	/**
	 * 查询某个客户某个体检体检指标的详细信息
	 */
	public List<PhysicalItem> findPhysicalItemBycIdpId(Integer clientId,Integer physicalId);
}
