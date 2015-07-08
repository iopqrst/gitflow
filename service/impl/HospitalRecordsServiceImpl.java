package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.HospitalRecordsExtend;
import com.bskcare.ch.dao.HospitalRecordsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.HospitalRecordsService;
import com.bskcare.ch.vo.client.HospitalRecords;

@Service
public class HospitalRecordsServiceImpl implements HospitalRecordsService {

	@Autowired
	private HospitalRecordsDao hsrecordsDao;

	public HospitalRecords save(HospitalRecords h) {
		return hsrecordsDao.add(h);
	}

	public List<HospitalRecordsExtend> horecordslist(Integer clientId) {

		return hsrecordsDao.recordlist(clientId);
	}

	public HospitalRecordsExtend findHosDetailById(Integer id) {
		return hsrecordsDao.findHosDetailById(id);
	}

	public HospitalRecords findHospitalRecordsById(Integer id, Integer clientId) {
		return hsrecordsDao.findHospitalRecordsById(id, clientId);
	}

	public PageObject<HospitalRecordsExtend> messagelist(Integer clientId,
			QueryInfo info) {
		return hsrecordsDao.messagelist(clientId,info);
	}

	public List<HospitalRecords> queryHospital(Integer clientId) {
		return hsrecordsDao.queryHospital(clientId);
	}

}
