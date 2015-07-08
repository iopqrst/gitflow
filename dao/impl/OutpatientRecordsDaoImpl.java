package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.OutpatientRecordsExtend;
import com.bskcare.ch.dao.OutpatientRecordsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.OutpatientRecords;
@Repository
@SuppressWarnings("unchecked")
public class OutpatientRecordsDaoImpl extends BaseDaoImpl<OutpatientRecords> implements OutpatientRecordsDao{

	public OutpatientRecords save(OutpatientRecords t) {
		return this.add(t);
	}

	public List<OutpatientRecordsExtend> recordslist(Integer clientId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.* ,tci.name clientName, h.hisName hName, s.secName sName , d.docName dName" +
				" from t_case_outpatient_records r inner join t_clientinfo tci on r.clientId=tci.id ");
		sql.append(" left join t_case_hospital h on h.hisId=r.hisId ");
		sql.append(" left join t_case_section s on s.secId=r.secId ");
		sql.append(" left join t_case_doctor d on d.docId=r.docId ");
		sql.append(" where r.clientId=?");
		ArrayList args = new ArrayList();
		args.add(clientId);
		List list = this.executeNativeQuery(sql.toString(), null, null, args.toArray(), OutpatientRecordsExtend.class);
		return list;
	}

	public OutpatientRecordsExtend findPaDetailById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.* ,tci.name clientName, h.hisName hName, s.secName sName , d.docName dName" +
				" from t_case_outpatient_records r inner join t_clientinfo tci on r.clientId=tci.id ");
		sql.append(" left join t_case_hospital h on h.hisId=r.hisId ");
		sql.append(" left join t_case_section s on s.secId=r.secId ");
		sql.append(" left join t_case_doctor d on d.docId=r.docId ");
		sql.append(" where r.id=?");
		ArrayList args = new ArrayList();
		args.add(id);
		List list = this.executeNativeQuery(sql.toString(), null, null, args.toArray(), OutpatientRecordsExtend.class);
		if(list != null) {
			return (OutpatientRecordsExtend) list.get(0);
		} else {
			return null;
		}
	}

	public PageObject<OutpatientRecordsExtend> patientlist(Integer clientId,
			QueryInfo info) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.* ,tci.name clientName, h.hisName hName, s.secName sName , d.docName dName" +
				" from t_case_outpatient_records r inner join t_clientinfo tci on r.clientId=tci.id ");
		sql.append(" left join t_case_hospital h on h.hisId=r.hisId ");
		sql.append(" left join t_case_section s on s.secId=r.secId ");
		sql.append(" left join t_case_doctor d on d.docId=r.docId ");
		sql.append(" where r.clientId=?");
		ArrayList args = new ArrayList();
		args.add(clientId);
		return this.queryObjectsBySql(sql.toString(), null, null, args.toArray(), info, OutpatientRecordsExtend.class);
	}

	public List<OutpatientRecords> queryOutpatient(Integer clientId) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append("select * from `t_case_outpatient_records` cor where 1=1 ");
		if(null != clientId) {
			sql.append(" and cor.clientId=? ");
			args.add(clientId);
		}
		sql.append(" order by cor.id desc ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), OutpatientRecords.class);
	}

}
