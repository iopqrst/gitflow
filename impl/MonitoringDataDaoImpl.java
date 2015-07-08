package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.UploadExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.MonitoringDataDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ClientInfo;

@Repository
@SuppressWarnings("unchecked")
public class MonitoringDataDaoImpl extends BaseDaoImpl implements MonitoringDataDao{

	public PageObject queryObject(UploadExtend uploadExtend, QueryCondition qc, QueryInfo qi) {
		
		ArrayList args = new ArrayList();

		StringBuffer uploadsql = new StringBuffer("select id,clientId,testDateTime,uploadDateTime,sbp as 'val1'," +
				" dbp as 'val2',state, dispose,1 as type, 0 as stype from m_blood_pressure where 1 = 1");
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,bloodOxygen as 'val1'," +
				" heartbeat as 'val2',state, dispose,2 as type, 0 as stype from m_blood_oxygen where 1 = 1");
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,averageHeartRate as 'val1'," +
				" attachmentUrl as 'val2',state, dispose,3 as type, 0 as stype from m_electrocardiogram where 1 = 1");
		
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");		
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,bloodSugarValue as 'val1'," +
				" '' as 'val2',state, dispose,4 as type,bloodSugarType as stype from m_blood_sugar where 1 = 1");
		
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,temperature as 'val1'," +
				" '' as 'val2',state, dispose,6 as type, 0 as stype from m_temperature where 1 = 1");

		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		
		StringBuffer sql = new StringBuffer("select m.name clientName,m.mobile mobile,m.areaId,m.age age,m.createTime regTime, m.bazzaarGrade, m.type levelId, n.* from t_clientinfo m left join" +
				"("+ uploadsql.toString() +") n on m.id = n.clientId where 1 = 1 and n.id is not null and m.status = ?");
		
		args.add(ClientInfo.STATUS_NORMAL);
		if(qc != null){
			if(!StringUtils.isEmpty(qc.getAreaChain())) {
				String areaChain = qc.getAreaChain();
				String chains[] = areaChain.split("#");
				
				sql.append(" and (");
				
				if(chains.length > 1) {
					System.out.println(chains.length);
					for(int i = 0; i< chains.length; i++) {
						sql.append(" areachain like ?");
						args.add(chains[i]+"%");
						if(i != chains.length-1 ) {
							sql.append(" or ");
						}
					}
				} else {
					sql.append(" areachain like ?");
					args.add(chains[0]+"%");
				}
				sql.append(")");
			}

			if (!StringUtils.isEmpty(qc.getBazzaarGradeQuery())) {
				String bazz = qc.getBazzaarGradeQuery();
				String[] bazzStr = bazz.split(",");
				sql.append(" and (");
				if (bazzStr.length > 1) {
					for (int i = 0; i < bazzStr.length; i++) {
						sql.append(" m.bazzaarGrade = ?");

						args.add(bazzStr[i]);

						if (i != bazzStr.length - 1) {
							sql.append(" or");
						}
					}
				} else {
					sql.append(" m.bazzaarGrade = ?");
					args.add(bazzStr[0]);
				}
				sql.append(")");
			}
		}
		
		if(null != uploadExtend) {
			if(!StringUtils.isEmpty(uploadExtend.getClientName())) {
				sql.append(" and m.name like ? ");
				args.add("%" + uploadExtend.getClientName().trim() + "%");
			}
			if(!StringUtils.isEmpty(uploadExtend.getMobile())) {
				sql.append(" and m.mobile like ? ");
				args.add("%" + uploadExtend.getMobile().trim() + "%");
			}
			if(uploadExtend.getLevelId() != null) {
				sql.append(" and m.type = ? ");
				args.add(uploadExtend.getLevelId());
			}	
			if(null != uploadExtend.getType() && -1 != uploadExtend.getType()) {
				sql.append(" and n.type=? ");
				args.add(uploadExtend.getType());
			}
			
			if(null != uploadExtend.getStype() && 0 != uploadExtend.getStype()) {
				sql.append(" and n.stype=? ");
				args.add(uploadExtend.getStype());
			}
		}
		
		sql.append(" order by n.uploadDateTime desc ");
		
		Map scalars = new LinkedHashMap();
		scalars.put("clientName", StandardBasicTypes.STRING);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("age", StandardBasicTypes.INTEGER);
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("state", StandardBasicTypes.INTEGER);
		scalars.put("clientId", StandardBasicTypes.INTEGER);
		scalars.put("uploadDateTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("regTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("levelId", StandardBasicTypes.INTEGER);
		scalars.put("bazzaarGrade", StandardBasicTypes.INTEGER);
		scalars.put("val1", StandardBasicTypes.STRING);
		scalars.put("val2", StandardBasicTypes.STRING);
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("dispose",StandardBasicTypes.INTEGER);
		scalars.put("areaId", StandardBasicTypes.INTEGER);
		scalars.put("stype", StandardBasicTypes.INTEGER);
		return queryObjectsBySql(sql.toString() , null ,scalars, args.toArray(), qi,UploadExtend.class);
	}
	
	
	public int queryObject(UploadExtend uploadExtend, QueryCondition qc) {
		ArrayList args = new ArrayList();
		StringBuffer uploadsql = new StringBuffer("select id,clientId,testDateTime,uploadDateTime,sbp as 'val1'," +
				" dbp as 'val2',state, dispose,1 as type, 0 as stype from m_blood_pressure where 1 = 1");
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,bloodOxygen as 'val1'," +
				" heartbeat as 'val2',state, dispose,2 as type, 0 as stype from m_blood_oxygen where 1 = 1");
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,averageHeartRate as 'val1'," +
				" attachmentUrl as 'val2',state, dispose,3 as type, 0 as stype from m_electrocardiogram where 1 = 1");
		
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");		
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,bloodSugarValue as 'val1'," +
				" '' as 'val2',state, dispose,4 as type,bloodSugarType as stype from m_blood_sugar where 1 = 1");
		
		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		uploadsql.append(" union ");
		
		uploadsql.append("select id,clientId,testDateTime,uploadDateTime,temperature as 'val1'," +
				" '' as 'val2',state, dispose,6 as type, 0 as stype from m_temperature where 1 = 1");

		if(null != uploadExtend){
			if(null != uploadExtend.getDispose() && -1 != uploadExtend.getDispose()) {
				uploadsql.append(" and dispose = ?");
				args.add(uploadExtend.getDispose());
			}
		}
		if(null != qc) {
			if (null != qc.getBeginTime() ) {
				uploadsql.append(" and uploadDateTime >= ? ");
				args.add(qc.getBeginTime());
			}
			
			if(null != qc.getEndTime()) {
				uploadsql.append(" and uploadDateTime <= ? ");
				args.add(qc.getEndTime());
			}
		}
		
		StringBuffer sql = new StringBuffer("select count(*) from t_clientinfo m left join" +
				"("+ uploadsql.toString() +") n on m.id = n.clientId where m.status = ? and n.id is not null ");
		
		args.add(Constant.STATUS_NORMAL);
		
		if(!StringUtils.isEmpty(qc.getAreaChain())) {
			String areaChain = qc.getAreaChain();
			String chains[] = areaChain.split("#");
			
			sql.append(" and (");
			
			if(chains.length > 1) {
				System.out.println(chains.length);
				for(int i = 0; i< chains.length; i++) {
					sql.append(" m.areachain like ?");
					args.add(chains[i]+"%");
					if(i != chains.length-1 ) {
						sql.append(" or ");
					}
				}
			} else {
				sql.append(" m.areachain like ?");
				args.add(chains[0]+"%");
			}
			sql.append(")");
		}
		
		Object obj = findUniqueResultByNativeQuery(sql.toString(), args.toArray());
		if(obj != null && !obj.equals("")){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
}
