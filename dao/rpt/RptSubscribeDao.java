package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.RptMonitoringData;
import com.bskcare.ch.vo.rpt.RptSubscribe;

public interface RptSubscribeDao extends BaseDao<RptSubscribe>{
	
	/**
	 * 根据时间查询生成报告时间为查询时间的预约报告
	 */
	public List<RptSubscribe> queryClientsByTime(String genTime) ;
	/**
	 * 查询预约或已生成的报告
	 */
	public PageObject querySubcribeReport(RptSubscribe sub,QueryInfo queryInfo);
}
