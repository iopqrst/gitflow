package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.AuditRecordDao;
import com.bskcare.ch.service.rpt.AuditRecordService;

@Service
@SuppressWarnings("unchecked")
public class AuditRecordServiceImpl implements AuditRecordService{
	@Autowired
	private AuditRecordDao auditDao;
	
	public List findAuditById(Integer rptId,int rptType){
		return auditDao.findAuditById(rptId, rptType);
	}
}
