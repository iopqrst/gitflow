package com.bskcare.ch.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.bskcare.ch.bo.UploadExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.BloodOxygen;
import com.bskcare.ch.vo.BloodPressure;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.Electrocardiogram;
import com.bskcare.ch.vo.Temperature;
import com.bskcare.ch.vo.rpt.RptMonitoringData;

/***
 * 数据 检测 (血压 血氧 血糖 餐后2小时血糖)
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public interface MonitoringDataService {

	/**
	 * 获取血压信息
	 * 
	 * @param monitoringData
	 * @param queryInfo
	 * @param queryCondition
	 */
	public String getBloodPressureList(BloodPressure bloodPressure,
			QueryInfo queryInfo, QueryCondition queryCondition);

	/**
	 * 获取血氧信息
	 * 
	 * @param bloodPressure
	 * @param queryInfo
	 * @return
	 */
	public String getBloodOxygenList(BloodOxygen bloodOxygen,
			QueryInfo queryInfo, QueryCondition queryCondition);

	/**
	 * 获取空腹血糖 或者 餐后 2小时血糖
	 * 
	 * @param bloodSugar
	 * @param queryInfo
	 * @return
	 */
	public String getBloodSugarList(BloodSugar bloodSugar, QueryInfo queryInfo,
			QueryCondition queryCondition);

	/**
	 * 获取心电图
	 * 
	 * @param electrocardiogram
	 * @param queryInfo
	 * @return
	 */
	public String getElectrocardiogram(Electrocardiogram electrocardiogram,
			QueryInfo queryInfo, QueryCondition queryCondition);

	/**
	 * 获取血压信息图表
	 * 
	 * @param bloodPressure
	 * @param queryInfo
	 * @return
	 */
	public String getBloodPressureChart(BloodPressure bloodPressure,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval);

	/***
	 * 获取血氧信息图表
	 * 
	 * @param bloodOxygen
	 * @param queryInfo
	 * @return
	 */
	public String getBloodOxygenChart(BloodOxygen bloodOxygen,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval);

	/**
	 * 获取血糖信息图表
	 * 
	 * @param bloodSugar
	 * @param queryInfo
	 * @return
	 */
	public String getBloodSugarChart(BloodSugar bloodSugar,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval);

	/**
	 * 获取心电图 图表信息
	 * 
	 * @param electrocardiogram
	 * @param queryInfo
	 * @return
	 */
	public String getElectrocardiogramChart(
			Electrocardiogram electrocardiogram, QueryInfo queryInfo);

	/***
	 * 得到今日任务 看今天数据是否被上传
	 * 
	 * @param clientInfo
	 * @param queryInfo
	 * @return
	 */
	public String getTodayTask(ClientInfo clientInfo, QueryInfo queryInfo);

	/**
	 * 得到数据最后上传时间
	 * 
	 * @param clientInfo
	 * @param queryInfo
	 * @return
	 */
	public String monitoringLatUploadDate(ClientInfo clientInfo);

	/**
	 * 查询某一时间段内用户高压、低压极限值
	 */
	public Object queryLimitPressure(QueryCondition qc, BloodPressure bp);

	/**
	 * 查询某一时间端内血氧最好值与最低值
	 */
	public Object queryLimiteSpO2(QueryCondition qc, BloodOxygen bo);

	/**
	 * 报告数据
	 * 
	 * @param clientInfo
	 * @param rptId
	 * @return
	 */
	public List<RptMonitoringData> getReportMonitoringData(
			ClientInfo clientInfo, Integer rptId);

	/**
	 * 查询某一时间端内血糖最高值与最低值
	 */
	public Object queryLimiteSugar(QueryCondition qc, BloodSugar bs);

	/**
	 * 查询距离当前3个月的数据最后一次异常和正常数据
	 */
	public String queryElectrocardiogramData(Integer clientId);

	/**
	 * 查询出当前时间段的心电图数据
	 * 
	 * @param electrocardiogram
	 * @param queryInfo
	 * @param queryCondition
	 * @return
	 */
	public PageObject getElectrocardiogramList(
			Electrocardiogram electrocardiogram, QueryInfo queryInfo,
			QueryCondition queryCondition);

	/**
	 * 获得某段时间内的血压数据
	 * 
	 * @param bloodPressure
	 * @param queryInfo
	 * @param queryCondition
	 * @return
	 */
	public PageObject getListByBloodPressure(BloodPressure bloodPressure,
			QueryInfo queryInfo, QueryCondition queryCondition);

	/**
	 * 处理血压结果
	 * 
	 * @param plist
	 * @param jo
	 * @param bloodPressure
	 */
	public void disposeBloodPressureList(List<BloodPressure> plist,
			JSONObject jo);

	/**
	 * 处理血糖结果
	 * 
	 * @param plist
	 * @param jo
	 * @param eBloodSugar
	 */
	public void disposeBloodSugarList(List<BloodSugar> plist, JSONObject jo);

	/***
	 * 处理专家报告中的 血压表头数据
	 * 
	 * @param plist
	 * @param jo
	 */
	public void disposeRptBloodPressure(List<BloodPressure> plist, JSONObject jo);

	/**
	 * 得到血氧List 返回PageObject
	 * 
	 * @param bloodOxygen
	 * @param queryInfo
	 * @param queryCondition
	 * @return
	 */
	public PageObject getListByBloodOxygen(BloodOxygen bloodOxygen,
			QueryInfo queryInfo, QueryCondition queryCondition);

	/**
	 * 专家报告中需要的字段 最大值 最小值
	 * 
	 * @param datas
	 * @param jo
	 */
	public void disposeRptBloodSugarList(List<BloodSugar> plist, JSONObject jo);

	/**
	 * 获取血糖数据集合
	 * 
	 * @param bloodSugar
	 * @param queryInfo
	 * @param queryCondition
	 * @return
	 */
	public PageObject getListByBloodSugar(BloodSugar bloodSugar,
			QueryInfo queryInfo, QueryCondition queryCondition);

	/***
	 * 统计血氧信息List
	 * 
	 * @param plist
	 * @param jo
	 */
	public void disposeBloodOxygenList(List<BloodOxygen> plist, JSONObject jo);

	/***
	 * 血氧报告中的最大值 最小值统计
	 * 
	 * @param plist
	 * @param jo
	 */
	public void disposeRptBloodOxygenList(List<BloodOxygen> plist, JSONObject jo);

	/**
	 * 统计心电图 正常或者异常次数
	 * 
	 * @param datas
	 * @param jo
	 */
	public void disposeElectrocardiogram(List<Electrocardiogram> pList,
			JSONObject jo);

	/**
	 * 今日上传数据用户列表
	 */
	public PageObject queryObject(UploadExtend uploadExtend, QueryCondition qc,
			QueryInfo qi);

	/**
	 * 查询单个心电数据
	 */
	public Electrocardiogram getElectrocardiogram(Integer elecId);

	/**
	 * 处理上传数据
	 * 
	 * @param type
	 *            1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖
	 * @param dataId
	 *            上传数据的主键Id
	 */
	public void dealMonitoringData(Integer dataId, int type);

	public String queryTemperatureList(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition);

	public String queryTemperatureChart(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval);

	public String queryTemperatureListAndroid(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition);

	public String queryTemperatureChartAndroid(Temperature temperature,
			QueryInfo queryInfo, QueryCondition queryCondition, Integer interval);

	/**
	 * 手机端查询三个月空腹血糖和餐后血糖的信息
	 */
	public String queryBloodSugar(Integer clientId, String testDate,
			QueryInfo queryInfo);

	/**
	 * 查询血压近三个月的舒张压和收缩压数据
	 */
	public String queryBloodByThreeMonth(Integer clientId, QueryInfo queryInfo,
			QueryCondition qc);
	
	/**查询客户最近三个月（除去空腹血糖、餐后两小时血糖其他八种血糖的信息）血糖的信息**/
	public String queryClientBloodSugar(Integer clientId, String testDate,
			QueryInfo queryInfo);
	
	
	public String queryBloodPressure(Integer clientId);
	
	public String queryTgBloodSugar(Integer clientId);
	
	/** 
	 * 本周、本月血糖监测详情
	 * cid为用户编号,type为时间类型(type为setWeek,则查询本周;type为setMonth,则查询本月)
	 */
	public String queryBloodSugarDetail(Integer cid,String type,String testDate);
	
	public String queryBloodSugarDetailInfo(Integer cid, String beginDate, String endDate);
	
	public String queryBloodSugarWeek(Integer cid, String beginDate, String endDate);
	
	/*
	 * 查询本周的血压信息
	 */
	public String queryBloodPressureWeek(Integer clientId, String beginDate, String endDate);
	
	public int isUploadBloodSugar(ClientInfo clientInfo, String beginDate, String endDate);
	
	/**
	 * 查询某段时间内每天(血糖、血压、运动、用药)是否上传过数据
	 * @param cid
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public String queryUpRecord(Integer cid , String beginDate, String endDate);

	/**
	 * 查询具体时间内上传的（血糖、血压、运动、用药数据）
	 * @param cid
	 * @param day
	 * @return
	 */
	public String queryUpRecordByDay(Integer cid, Date day);
	
	/**
	 * 查询客户所有的血糖信息
	 */
	public String queryAllBloodSugar(Integer cid, String beginDate, String endDate);
	
	/**
	 * 通过客户id获取最后一次的测血糖数据
	 * @author sun
	 * @version 2014-12-18 下午03:10:13
	 * @param cid
	 * @return
	 */
	public String getLastBloodSugerForTimeLine(Integer cid);
	
	/**
	 * 查询最后一次的血糖数据，如果时间参数为空，那么将查找该用户的最后一次血糖数据
	 * @author sun
	 * @version 2014-12-22 下午04:42:07
	 * @param clientId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public BloodSugar queryLastBloodSugarByCId(Integer clientId , Date beginDate, Date endDate);
	
	public String queryLastBloodSugar(Integer cid);
	
	public String queryBloodSugarForNtgRpt(Integer cid, Date date);
	
	/**
	 * 查询指定时间内上传血糖高管人数
	 */
	public int queryUploadBloodSugarCount(QueryCondition queryCondition);
	
	/**
	 * 查询指定时间内平均上传血糖高管人数
	 */
	public int queryAvgBloodSugarCount(QueryCondition queryCondition);
	
	/**
	 * 查询某段时间内某个用户上传血糖次数
	 */
	public String queryUploadDays(Integer clientId, Date startDate, Date endDate) ;
}
