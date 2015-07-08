package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.bo.OutpatientRecordsExtend;
import com.bskcare.ch.dao.OutpatientRecordsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.OutpatientRecordsService;
import com.bskcare.ch.vo.client.OutpatientRecords;

@Service
public class OutpatientRecordsServiceImpl implements OutpatientRecordsService {

	@Autowired
	private OutpatientRecordsDao oprecordsDao;

	public OutpatientRecords add(OutpatientRecords t) {
		return oprecordsDao.add(t);
	}

	public List<OutpatientRecordsExtend> oprecordslist(Integer clientId) {
		return oprecordsDao.recordslist(clientId);
	}

	public OutpatientRecordsExtend findPaDetailById(Integer id) {
		return oprecordsDao.findPaDetailById(id);
	}

	public PageObject<OutpatientRecordsExtend> patientlist(Integer clientId,
			QueryInfo info) {
		return oprecordsDao.patientlist(clientId, info);
	}

	public List<OutpatientRecords> queryOutpatient(Integer clientId) {
		return oprecordsDao.queryOutpatient(clientId);
	}

}
