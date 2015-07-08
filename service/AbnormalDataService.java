package com.bskcare.ch.service;


import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.BloodSugar;
import com.bskcare.ch.vo.Electrocardiogram;

@SuppressWarnings("unchecked")
public interface AbnormalDataService {
	/**
	 * 得到异常和者正常 血压
	 * @param queryInfo
	 * @return
	 */
	PageObject getAbnormalBPList(String areaChain,AbnormalCondition abnormalCondition,QueryInfo queryInfo);
	/**
	 * 得到异常血氧数据
	 * @param queryInfo
	 * @return
	 */
	PageObject getAbnormalBOList(String areaChain,AbnormalCondition abnormalCondition,QueryInfo queryInfo);
	/**
	 * 得到异常空腹血糖 数据
	 * @param queryInfo
	 * @return
	 */
	PageObject getAbnormalBSList(String areaChain,AbnormalCondition abnormalCondition,BloodSugar bloodSugar,QueryInfo queryInfo);
	/**
	 * 处理血压异常
	 * @param bloodPressure  2014-02-10注释此段代码 （2个月后可以删除）
	 */
	int disposeBloodPressure(Object tmp,String table);
	/**
	 * 得到心电图数据
	 * @param abnormalCondition
	 * @param electrocardiogram
	 * @param queryInfo
	 * @return
	 */
	PageObject getAbnormalEDList(String areaChain,AbnormalCondition abnormalCondition,
			Electrocardiogram electrocardiogram, QueryInfo queryInfo);
	/**
	 *  得到报警血压数据
	 * @return
	 */
	PageObject getBPAlarmData(QueryInfo queryInfo);
	/**
	 * 得到报警的血氧数据
	 * @param queryInfo
	 * @return
	 */
	PageObject getBOAlarmData(QueryInfo queryInfo);
	/**
	 * 得到报警的血糖数据
	 * @param queryInfo
	 * @return
	 */
	PageObject getBSAlarmData(QueryInfo queryInfo);
	/**
	 * 得到心电图的报警数据
	 * @param queryInfo
	 * @return
	 */
	PageObject getEDAlarmData(QueryInfo queryInfo);
	
}
