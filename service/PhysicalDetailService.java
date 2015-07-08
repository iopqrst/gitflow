package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.client.PhysicalDetail;

public interface PhysicalDetailService {
	/**
	 * 查询体检指标信息
	 */
	public List<PhysicalDetail> findDetail(PhysicalDetail pd);
	
	public PhysicalDetail findDetailById(Integer pdId);
	
	/**
	 * 查询某个用户体检最小的体检项目
	 */
	public Integer findMinPdIdByClientId(Integer clientId);
}
