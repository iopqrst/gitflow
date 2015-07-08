package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.PhysicalDetailDao;
import com.bskcare.ch.service.PhysicalDetailService;
import com.bskcare.ch.vo.client.PhysicalDetail;

@Service
public class PhysicalDetailServiceImpl implements PhysicalDetailService {

	@Autowired
	private PhysicalDetailDao physicalDetailDao;

	public List<PhysicalDetail> findDetail(PhysicalDetail pd) {
		return physicalDetailDao.findDetail(pd);
	}

	public PhysicalDetail findDetailById(Integer pdId) {
		return physicalDetailDao.load(pdId);
	}

	public Integer findMinPdIdByClientId(Integer clientId){
		return physicalDetailDao.findMinPdIdByClientId(clientId);
	}
}
