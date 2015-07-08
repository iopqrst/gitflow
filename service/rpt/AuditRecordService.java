package com.bskcare.ch.service.rpt;

import java.util.List;


@SuppressWarnings("unchecked")
public interface AuditRecordService {
	/**
	 * 根据rptId查询健康报告审核记录
	 */
	public List findAuditById(Integer rptId,int rptType);
}
