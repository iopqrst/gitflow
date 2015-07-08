package com.bskcare.ch.service.rpt;

import com.bskcare.ch.vo.rpt.RptMonitoringData;

public interface RptMonitoringDataService {

	public RptMonitoringData add(RptMonitoringData m);

	public RptMonitoringData load(Integer id);
	
	public int updateRptBySingleField(String field,String content,Integer mid);
	
	/**
	 * 修改数据检测的默认图片
	 * @param bloodPressureChart
	 * @param bloodSugarChart
	 * @param bloodSugar2hChart
	 * @param bloodOxygenChart
	 * @param electrocardiogram
	 * @param type 
	 */
	public void updateDefaultPic(String bloodPressureChart,
			String bloodSugarChart, String bloodSugar2hChart,
			String bloodOxygenChart, String electrocardiogram,String rptId, String type);

}
