package com.bskcare.ch.dao.rpt;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.SrptSummaryReport;

public interface SrptSummaryReportDao extends BaseDao<SrptSummaryReport> {
	
	/**根据简易报告id查询简易报告运动部分信息**/
	public SrptSummaryReport querySSRBySrptId(Integer srptId);
	
	/**根据简易报告id修改报告部分属性**/
	public int updateReportProperty(String content,String field,Integer dietId);
}
