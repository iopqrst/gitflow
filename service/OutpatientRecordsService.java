package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.OutpatientRecordsExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.OutpatientRecords;
import com.sun.org.apache.bcel.internal.generic.LSTORE;

public interface OutpatientRecordsService {
	/**
	 * 添加
	 */
	public OutpatientRecords add(OutpatientRecords t);

	/**
	 * 门诊扩展类集合
	 */
	public List<OutpatientRecordsExtend> oprecordslist(Integer clientId);

	/**
	 * 门诊扩展类对象
	 */
	public OutpatientRecordsExtend findPaDetailById(Integer id);

	/**
	 * 门诊记录集合分页
	 */
	public PageObject<OutpatientRecordsExtend> patientlist(Integer clientId,
			QueryInfo info);
	
	/**
	 * 门诊集合
	 */
	public List<OutpatientRecords> queryOutpatient(Integer clientId);
}
