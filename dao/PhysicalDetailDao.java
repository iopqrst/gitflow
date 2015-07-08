package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.client.PhysicalDetail;

public interface PhysicalDetailDao extends BaseDao<PhysicalDetail>{
	
	/**
	 * 查询体检指标信息
	 */
	public List<PhysicalDetail> findDetail(PhysicalDetail pd);
	
	/**
	 * 查询用户参与体检的体检指标信息
	 */
	public List<PhysicalDetail> queryDetailByClientId(Integer clientId);
	
	/**
	 * 查询某个用户体检最小的体检项目
	 */
	public Integer findMinPdIdByClientId(Integer clientId);
	
}
