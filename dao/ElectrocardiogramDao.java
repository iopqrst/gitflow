package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Electrocardiogram;

@SuppressWarnings("unchecked")
public interface ElectrocardiogramDao extends BaseDao<Electrocardiogram> {

	/**
	 * 查询心电图信息
	 * 如果  queryInfo 不为空    表示查询 list分页查询   
	 * 如果  queryInfo 为 null 表示查询血压图，可以通过 传入开始时间和结束时间 来查询
	 * @param Electrocardiogram
	 * @param queryInfo
	 * @param eDate 
	 * @param sDate 
	 * @return
	 * @throws Exception 
	 */
	public	PageObject getListByElectrocardiogram(Electrocardiogram electrocardiogram, QueryInfo queryInfo,QueryCondition queryCondition);
	/**
	 * 插入心电图信息
	 * @param electrocardiogram
	 */
	public void addElectrocardiogram(Electrocardiogram electrocardiogram);
	/**
	 * 获取异常心电图
	 * @param abnormalCondition
	 * @param electrocardiogram
	 * @param userInfo 
	 * @param queryInfo
	 * @return
	 */
	public PageObject getAbnormalBSList(String areaChain,AbnormalCondition abnormalCondition,
			Electrocardiogram electrocardiogram,QueryInfo queryInfo);
	
	/**
	 * 查询心电图对象
	 * @param clientId
	 * @return
	 */
	public Electrocardiogram getLastUploadDateTime(Integer clientId);
	/**
	 *  查询用户今天上传心电监测数据集合  
	 */
	public List<Electrocardiogram> getTodayUploadDateTimeList(Integer clientId);
	
	/**
	 * 查询距离当前3个月的数据最后一次异常和正常数据
	 */
	public List<Electrocardiogram> queryElectrocardiogramData(Integer clientId);
	/**
	 * 查询出这次心电图的数据
	 * @param electrocardiogramId
	 * @return
	 */
	public Electrocardiogram lod(int electrocardiogramId);
	
	/**
	 * 查询某一时间段内用户心率的平均值
	 */
	public Object queryElectrocardiogramAverage(QueryCondition qc, Electrocardiogram ed);
	/**
	 * 得到心电图的报警数据
	 * @param queryInfo
	 * @return
	 */
	public PageObject getEDAlarmData(QueryInfo queryInfo);
}
