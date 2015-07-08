package com.bskcare.ch.service.rpt;


import com.bskcare.ch.vo.rpt.SrptSummaryReport;

public interface SrptSummaryReportService {
	
	public void generateReport(Integer clientId, int type);
	public void generateReportByDay(Integer clientId, int type ,int Const);
	
	
	/**根据简易报告id查询简易报告运动部分信息**/
	public SrptSummaryReport querySSRBySrptId(Integer srptId);
	
	/**根据简易报告id修改报告部分属性**/
	public int updateReportProperty(String content,String field,Integer dietId);
}
