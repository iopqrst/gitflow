package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.OutpatientRecordsExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.OutpatientRecords;

public interface OutpatientRecordsDao extends BaseDao<OutpatientRecords> {
	/**
	 * 增加
	 * 
	 * @param t
	 */
	public OutpatientRecords save(OutpatientRecords t);

	/**
	 * 门诊记录集合
	 */
	public List<OutpatientRecordsExtend> recordslist(Integer clientId);

	/**
	 *门诊对象
	 * 
	 * @param id
	 */
	public OutpatientRecordsExtend findPaDetailById(Integer id);

	/**
	 * 后台门诊记录分页
	 */
	public PageObject<OutpatientRecordsExtend> patientlist(Integer clientId,
			QueryInfo info);
	
	/**
	 * 门诊集合
	 */
	public List<OutpatientRecords> queryOutpatient(Integer clientId);

}
