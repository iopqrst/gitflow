package com.bskcare.ch.dao;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.BloodOxygen;

@SuppressWarnings("unchecked")
public interface BloodOxygenDao extends BaseDao<BloodOxygen>{
	/**
	 * 查询血氧信息  
	 * 如果  queryInfo 不为空    表示查询 list分页查询   
	 * 如果  queryInfo 为 null 表示查询血压图，可以通过 传入开始时间和结束时间 来查询
	 * @param bloodOxygen
	 * @param queryInfo
	 * @return
	 * @throws Exception 
	 */
	public PageObject  getListByBloodOxygen(BloodOxygen bloodOxygen,QueryInfo queryInfo,QueryCondition queryCondition) ;
	/***
	 * 上传血氧信息
	 * @param bloodOxygen
	 */
	public void addBloodOxygen(BloodOxygen bloodOxygen);
	/**
	 * 获得异常和正常 血氧数据
	 * @param queryInfo
	 * @return
	 */
	public PageObject getAbnormalBOList(String areaChain,AbnormalCondition abnormalCondition,QueryInfo queryInfo);
	/***
	 * 获得最后上传时间
	 * @param clientId
	 * @return
	 */
	public BloodOxygen getLastUploadDateTime(int clientId);	
	/**
	 * 查询用户今天上传血氧监测数据集合 
	 */
	public List<BloodOxygen> getTodayUploadDateTimeList(int clientId);
	
	/**
	 * 查询某一时间端内血氧最好值与最低值
	 */
	public Object queryLimiteSpO2(QueryCondition qc, BloodOxygen bo);
	
	/**
	 * 查询某一时间端内血氧平均值
	 * @param qc 查询条件（时间）
	 * @param bo 血氧实体类
	 */
	public Object queryAverageSpO2(QueryCondition qc, BloodOxygen bo);
	/**
	 * 查询血氧报警信息
	 * @param queryInfo
	 * @return
	 */
	public PageObject getBloodOxygenAlarmData(QueryInfo queryInfo);
	
	/**查询某个用户在某个时间段测量数据的条数**/
	public int queryBloodOxygenCount(Integer clientId,Date date);
	
	/**查询某个用户某个时间端的血氧值情况**/
	public int queryBloodOxygen(Integer clientId,Date date,int maxVal,int minVal);
}
