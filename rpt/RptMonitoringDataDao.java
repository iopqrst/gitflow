package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.rpt.RptMonitoringData;

public interface RptMonitoringDataDao extends BaseDao<RptMonitoringData>{
	/**
	 * 获取报告的血压 血氧 血糖 的三个月数据
	 * @param clientInfo
	 * @param rptId 
	 * @return
	 */
	List<RptMonitoringData> getReportMonitoringData(ClientInfo clientInfo, Integer rptId);
	/**
	 * 修改默认图片的编号
	 * @param bloodPressureChart
	 * @param bloodSugarChart
	 * @param bloodSugar2hChart
	 * @param bloodOxygenChart
	 * @param electrocardiogram
	 * @param rptId
	 */
	void updateDefaultPic(String bloodPressureChart, String bloodSugarChart,
			String bloodSugar2hChart, String bloodOxygenChart,
			String electrocardiogram, String rptId,String type);
	
	/**
	 * 修改某一字段
	 * @param field 字段名称
	 * @param content 内容
	 * @param mid 该条报告数据Id
	 */
	public int updateRptBySingleField(String field,String content,Integer mid);
	
	/**根据健康报告id删除信息**/
	public void deleteMonDataByRptId(Integer rptId);
}
