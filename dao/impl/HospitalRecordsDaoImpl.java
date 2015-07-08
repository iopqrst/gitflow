package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.HospitalRecordsExtend;
import com.bskcare.ch.dao.HospitalRecordsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.client.HospitalRecords;

@Repository
@SuppressWarnings("unchecked")
public class HospitalRecordsDaoImpl extends BaseDaoImpl<HospitalRecords>
		implements HospitalRecordsDao {

	public HospitalRecords save(HospitalRecords h) {
		return this.add(h);
	}

	public List<HospitalRecordsExtend> recordlist(Integer clientId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.* ,tci.name clientName, h.hisName hName, s.secName sName , d.docName dName"
						+ " from t_case_hospital_records r inner join t_clientinfo tci on r.clientId=tci.id ");
		sql.append(" left join t_case_hospital h on h.hisId=r.hisId ");
		sql.append(" left join t_case_section s on s.secId=r.secId ");
		sql.append(" left join t_case_doctor d on d.docId=r.docId ");
		sql.append(" where r.clientId=?");
		ArrayList args = new ArrayList();
		args.add(clientId);
		List list = this.executeNativeQuery(sql.toString(), null, null, args.toArray(), HospitalRecordsExtend.class);
		return list;
	}

	public HospitalRecordsExtend findHosDetailById(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.* ,tci.name clientName, h.hisName hName, s.secName sName , d.docName dName"
						+ " from t_case_hospital_records r inner join t_clientinfo tci on r.clientId=tci.id ");
		sql.append(" left join t_case_hospital h on h.hisId=r.hisId ");
		sql.append(" left join t_case_section s on s.secId=r.secId ");
		sql.append(" left join t_case_doctor d on d.docId=r.docId ");
		sql.append(" where r.id=?");
		ArrayList args = new ArrayList();
		args.add(id);
		List list = this.executeNativeQuery(sql.toString(), null, null, args.toArray(), HospitalRecordsExtend.class);
		if (list != null) {
			return (HospitalRecordsExtend) list.get(0);
		} else {
			return null;
		}
	}

	public HospitalRecords findHospitalRecordsById(Integer id, Integer clientId) {
		String hql = "from HospitalRecords where id=? and clientId=?";
		Object[] obj = { id, clientId };
		List<HospitalRecords> lst = executeFind(hql, obj);
		if (!CollectionUtils.isEmpty(lst)) {
			return lst.get(0);
		}
		return null;
	}

	public PageObject<HospitalRecordsExtend> messagelist(Integer clientId,
			QueryInfo info) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.* ,tci.name clientName, h.hisName hName, s.secName sName , d.docName dName"
						+ " from t_case_hospital_records r inner join t_clientinfo tci on r.clientId=tci.id ");
		sql.append(" left join t_case_hospital h on h.hisId=r.hisId ");
		sql.append(" left join t_case_section s on s.secId=r.secId ");
		sql.append(" left join t_case_doctor d on d.docId=r.docId ");
		sql.append(" where r.clientId=?");
		ArrayList args = new ArrayList();
		args.add(clientId);
		return this.queryObjectsBySql(sql.toString(), null, null, args.toArray(), info, HospitalRecordsExtend.class);
	}

	public List<HospitalRecords> queryHospital(Integer clientId) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" select * from t_case_hospital_records chr where 1=1 ");
		if(null != clientId) {
			sql.append(" and chr.clientId=? ");
			args.add(clientId);
		}
		sql.append(" order by chr.id desc ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), HospitalRecords.class);
	}
}
