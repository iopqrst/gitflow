package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.HospitalRecordsExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.HospitalRecords;

public interface HospitalRecordsService {
	/**
	 *保存
	 * 
	 * @param h
	 */
	public HospitalRecords save(HospitalRecords h);

	/**
	 * 住院记录扩展类集合
	 * 
	 * @param clientId
	 * @return
	 */
	public List<HospitalRecordsExtend> horecordslist(Integer clientId);

	/**
	 * 住院扩展类对象
	 * 
	 * @param id
	 * @return
	 */
	public HospitalRecordsExtend findHosDetailById(Integer id);

	/**
	 * 住院扩展类对象
	 * 
	 * @param id
	 * @param clientId
	 * @return
	 */
	public HospitalRecords findHospitalRecordsById(Integer id, Integer clientId);

	/**
	 * 住院记录集合分页
	 */
	public PageObject<HospitalRecordsExtend> messagelist(Integer clientId,
			QueryInfo info);
	
	/**
	 * 住院集合
	 */
	public List<HospitalRecords> queryHospital(Integer clientId); 
}
