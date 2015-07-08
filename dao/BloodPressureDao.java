package com.bskcare.ch.dao;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.BloodPressure;


@SuppressWarnings("unchecked")
public interface BloodPressureDao extends BaseDao<BloodPressure>{
	
	/**
	 * 查询血压信息
	 * 如果  queryInfo 不为空    表示查询 list分页查询   
	 * 如果  queryInfo 为 null 表示查询血压图，可以通过 传入开始时间和结束时间 来查询
	 * @param monitoringData
	 * @param queryInfo
	 * @param myDate2 
	 * @param sDate 
	 * @return
	 * @throws Exception 
	 */
	public PageObject  getListByBloodPressure(BloodPressure bloodPressure,QueryInfo queryInfo,QueryCondition q) ;
	
	/**
	 * 查询最后时间上传的血压信息
	 * @return
	 */
	public BloodPressure getLastUploadDateTime(int clientId);
	/***
	 * 得到血压的 最后上传时间 和第一次上传时间
	 * @param type 
	 * @param bloodPressure
	 * @return
	 */
	public Date[] getLastMonthDate(int clientId,String table, int type,Integer interval);
	/**
	 * 插入 血压
	 * @param bloodpressure
	 */
	public boolean addBloodPressure(BloodPressure bloodpressure);
	/***
	 * 获得 异常 血压的人
	 * @param userInfo 
	 * @param queryInfo 
	 */
	public PageObject getAbnormalBPList(String areaChain,AbnormalCondition abnormalCondition, QueryInfo queryInfo);
	/**
	 * 处理血压异常
	 * @param bloodPressure
	 */
	public int updateDispose(String table,int clientId,Date testDate,int bloodSugarType);
	/**
	 * 查询用户今天上传血压监测数据集合
	 */
	public List<BloodPressure> getTodayUploadDateTimeList(int clientId);
	
	/**
	 * 查询某一时间段内用户高压、低压极限值
	 * @param qc 时间条件
	 * @param bp 血压条件
	 * @return
	 */
	public Object queryLimitPressure(QueryCondition qc, BloodPressure bp);
	
	/**
	 * 查询某一时间段内用户高压、低压的平均值
	 */
	public Object queryPressureAverage(QueryCondition qc, BloodPressure bp);
	/**
	 * 血压报警信息
	 * @return
	 */
	public PageObject getBloodPressureAlarmData(QueryInfo queryInfo);
			
	public List<BloodPressure> queryBloodPressureCount(Integer clientId,Date date);
	
	public int queryBloodPressure(Integer clientId,int sbpMin,int sbpMax,int dbpMin,int dbpMax,Date date);
	
	/**
	 * 查询血压近三个月的舒张压和收缩压数据
	 */
	public PageObject<BloodPressure> queryBloodByThreeMonth(Integer clientId,QueryInfo queryInfo,QueryCondition qc);

	public List queryBloodPressure(Integer clientId, Date startDate, Date endDate);
}
