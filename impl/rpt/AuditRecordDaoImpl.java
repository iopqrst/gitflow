package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.AuditRecordDao;
import com.bskcare.ch.vo.rpt.RptAuditRecord;

@Repository
@SuppressWarnings("unchecked")
public class AuditRecordDaoImpl extends	BaseDaoImpl<RptAuditRecord> implements AuditRecordDao{
	
	public List findAuditById(Integer rptId,int rptType){
		String sql = "select {t1.*},t2.name userName from rpt_audit_record t1, t_userinfo t2 where rptId = ?  and t1.rptType = ? and t1.userId = t2.id";
		
		Map entities = new HashedMap();
		entities.put("t1", RptAuditRecord.class);
		Map scalars = new HashedMap();
		scalars.put("userName", StandardBasicTypes.STRING);
		return executeNativeQuery(sql, entities, scalars, new Object[]{rptId,rptType},null);
		 
	}
	
	public void deleteAuditByRptId(Integer rptId,int rptType){
		String sql = "delete from rpt_audit_record where rptId = ? and rptType = ?";
		List args = new ArrayList();
		args.add(rptId,rptType);
		deleteBySql(sql, args.toArray());
	}
}
