package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.RptAuditRecord;

@SuppressWarnings("unchecked")
public interface AuditRecordDao extends BaseDao<RptAuditRecord>{

	/**
	 * 根据rptId查询健康报告审核记录
	 */
	public List findAuditById(Integer rptId,int rptType);
	
	/**根据rptId删除健康报告审核记录**/
	public void deleteAuditByRptId(Integer rptId,int rptType);
}
