package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptSubscribe;

public interface RptSubscribeService {

	public void createSubscribeRpt ();
	
	/**
	 * 根据时间查询生成报告时间为查询时间的预约报告
	 * @param genTime yyyy-MM-dd 格式
	 */
	public List<RptSubscribe> queryClientsByTime(String gtime) ;
	/**
	 * 查询预约或已生成的报告
	 */
	public PageObject querySubcribeReport(RptSubscribe rsb,QueryInfo queryInfo);
	
	/**
	 * 查询单个对象
	 */
	public RptSubscribe findById(RptSubscribe subscribe);
	
	/**
	 * 修改
	 */
	public void updateSubcribeTime(RptSubscribe subscribe);
	
}
