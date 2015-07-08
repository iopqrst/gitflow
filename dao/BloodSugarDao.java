package com.bskcare.ch.dao;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.BloodSugar;

@SuppressWarnings("unchecked")
public interface BloodSugarDao extends BaseDao<BloodSugar>{

	/**
	 * 查询血糖信息
	 * 如果  queryInfo 不为空    表示查询 list分页查询   
	 * 如果  queryInfo 为 null 表示查询血压图，可以通过 传入开始时间和结束时间 来查询
	 * @param bloodOxygen
	 * @param queryInfo
	 * @return
	 * @throws Exception 
	 */
	public	PageObject getListByBloodSugar(BloodSugar bloodSugar,QueryInfo queryInfo,QueryCondition queryCondition);
	/***
	 * 上传血糖
	 * @param bloodSugar
	 */
	public void addBloodSugar(BloodSugar bloodSugar);
	/**
	 * 得到异常血糖的人数
	 * @param bloodSugar 
	 * @param userInfo 
	 * @param queryInfo
	 * @return
	 */
	public PageObject getAbnormalBSList(String areaChain,AbnormalCondition abnormalCondition,BloodSugar bloodSugar,QueryInfo queryInfo);
	/***
	 * 最后上传的血糖
	 * @param clientId
	 * @return
	 */
	public BloodSugar getLastUploadDateTime(int clientId,Integer type);
	/**
	 * 查询用户今天上传血糖监测数据集合  
	 */
	public List<BloodSugar> getTodayUploadDateTimeList(int clientId,Integer type);
	
	public Object queryLimiteSugar(QueryCondition qc, BloodSugar bs);
	/**
	 * 得到报警数据
	 * @param queryInfo
	 * @return
	 */
	public PageObject getBloodSugarAlarmData(QueryInfo queryInfo);
	
	/**查询某个用户在某个时间段测量数据的条数**/
	public List<BloodSugar> queryBloodSugarCount(Integer clientId,Date date,int type);
	
	/**查询某个用户某个时间端的血糖值情况**/
	public int queryBloodSugar(Integer clientId,Date date,double maxVal,double minVal,int type);
	/**
	 * 查询某个时间段的上传数量
	 */
	public int quertBloodSugar(Integer cid,Date beginDate,Date EndDate,Integer stauts);
	
	/** 根据用户编号查询血糖数据**/
	public List<BloodSugar> queryBloodSugar(Integer clientId);
	
	public List<BloodSugar> queryTgBloodSugar(Integer clientId, Date startDate, Date endDate);
	
	public BloodSugar queryTgBloodSugar(Integer clientId);
	
	/** 本周血糖监测详情**/
	public List<BloodSugar> queryBloodSugarDetail(Integer cid,String type,String testDate);
	
	
	public List<BloodSugar> queryBloodSugarDetailInfo(Integer cid, Date beginDate, Date endDate);
	
	public List<BloodSugar> queryBloodSugar(BloodSugar bloodSugar, Date beginDate, Date endDate);
	
	public int queryClientBloodSugar(BloodSugar bloodSugar, Date beginDate, Date endDate);
	
	/**
	 * 查询某一段时间内（血糖、血压、运动、用药） 有上传数据的时间
	 * @param cid
	 * @param beginDate 
	 * @param endDate
	 * @return 所有有上传数据的日期：2014-10-21 、2014-10-22...
	 */
	public List<Object> queryUploadDataRecord(Integer cid, Date beginDate, Date endDate);
	
	/**
	 *  查询某天最后一次上传血糖数据
	 */
	public List<BloodSugar> queryLatestBloodSugar(Integer cid, Date beginDate, Date endDate);
	
	/**
	 * 通过clientId查找他最后一条血糖数据
	 * @author sun
	 * @version 2014-12-18 下午02:26:19
	 * @param clientId
	 * @return
	 */
	public BloodSugar queryLastBloodSugarByCId(Integer clientId , Date beginDate, Date endDate);
	
	/**
	 * 查询指定时间内上传血糖次数
	 */
	public int queryUploadBloodSugarCount(Date startDate, Date endDate);
	
	/**
	 * 查询指定时间内上传血糖次数的平均值
	 */
	public Integer queryAvgBloodSugar(Date startDate, Date endDate);
	
	/**
	 * 查询某段时间内某个用户上传血糖次数
	 */
	public int queryUploadDays(Integer clientId, Date startDate, Date endDate) ;

}
