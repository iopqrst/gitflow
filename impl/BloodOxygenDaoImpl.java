package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.AbnormalCondition;
import com.bskcare.ch.bo.BloodOxygenAD;
import com.bskcare.ch.bo.MonitoringDataExtends;
import com.bskcare.ch.dao.BloodOxygenDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.BloodOxygen;


@Repository("bloodOxygenDao")
@SuppressWarnings("unchecked")
public class BloodOxygenDaoImpl extends BaseDaoImpl<BloodOxygen> implements BloodOxygenDao{

	public PageObject getListByBloodOxygen(BloodOxygen bloodOxygen,QueryInfo queryInfo,QueryCondition queryCondition) {
		StringBuffer sql = new StringBuffer("select * from m_blood_oxygen t where 1 = 1 and t.clientId = ?");
		ArrayList args = new ArrayList();
		
		args.add(bloodOxygen.getClientId()) ;	
		Date sDate = null ; 
		Date eDate = null ;
		if(queryCondition!=null){
			 sDate = queryCondition.getBeginTime() ;
			 eDate = queryCondition.getEndTime() ;			
		}
		if(sDate!=null&&eDate!=null){
			sql.append(" and t.testDateTime >= ?  ") ;	
			sql.append(" and t.testDateTime <= ? ") ;	

		}		
		
		if(sDate!=null&&eDate!=null){
			//如果选择的开始日小于结束日期，开始日期和结束日期 对换
			if(!sDate.before(eDate)){
				Date d = sDate ;
				sDate = eDate ;
				eDate = d ;
			}
			args.add(sDate) ;	
			args.add(DateUtils.getAppointDate(eDate, 1)) ;	
		}
		
		return queryObjectsBySql(sql.toString(),null,null,args.toArray(),queryInfo,BloodOxygen.class);
	}

	public void addBloodOxygen(BloodOxygen bloodOxygen) {
		this.add(bloodOxygen) ;
	}

	public PageObject getAbnormalBOList(String areaChain,AbnormalCondition abnormalCondition,QueryInfo queryInfo) {
		StringBuffer sql = new StringBuffer("select ci.id,ci.`name` clientName, ");
		sql.append(" ci.type,ai.`NAME` areaName,ci.gender,ci.age,ci.mobile, ");
		sql.append(" bp.testDateTime,bp.uploadDateTime,bp.dispose,bp.state,bp.bloodOxygen,bp.heartbeat, ");
		sql.append(" ci.areaChain from m_blood_oxygen bp LEFT JOIN t_clientinfo ci on ci.id = bp.clientId ");
		sql.append(" LEFT JOIN ");
		sql.append(" (select t2.id areaId,t2.name from t_areainfo t2 ");
		sql.append(" where t2.`status` = 0 AND t2.id IS NOT NULL) ai ");
		sql.append(" on ci.areaId = ai.areaId ");		
		sql.append(" where 1=1 ") ;
		
		ArrayList args = new ArrayList();
		if(!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			sql.append(" and ( ");
			
			if(areaChains.length > 1){
				for (int i = 0; i < areaChains.length; i++) {
					sql.append(" ci.areaChain like ? ");
					args.add(areaChains[i]+"%");
					if(i != areaChains.length - 1) {
						sql.append(" or");
					}
				}
			} else {
				sql.append(" ci.areaChain like ? ");
				args.add(areaChains[0]+"%");
			}
			sql.append(")");
		}

		if(abnormalCondition!=null){
			if(abnormalCondition.getDispose()!=null&&abnormalCondition.getDispose()>-1){
				sql.append(" and bp.dispose = ? ") ;
				args.add(abnormalCondition.getDispose()) ;
			}
			if(abnormalCondition.getState()!=null&&abnormalCondition.getState()>-1){
				sql.append(" and bp.state = ? ") ;
				args.add(abnormalCondition.getState()) ;
			}
			if(StringUtils.hasLength(abnormalCondition.getName())){
				sql.append(" and ci.name like ? ") ;
				args.add("%"+abnormalCondition.getName().trim()+"%") ;
			}
			if(abnormalCondition.getStestDateTime()!=null&&abnormalCondition.getEtestDateTime()!=null){
				sql.append(" and bp.testDateTime >= ?  ") ;	
				sql.append(" and bp.testDateTime <= ? ") ;	
				args.add(abnormalCondition.getStestDateTime()) ;
				args.add(abnormalCondition.getEtestDateTime()) ;
			}
		}
		
		Map scalars = new LinkedHashMap();
		scalars.put("id", StandardBasicTypes.INTEGER);
		scalars.put("clientName", StandardBasicTypes.STRING);
		scalars.put("type", StandardBasicTypes.INTEGER);
		scalars.put("areaName", StandardBasicTypes.STRING);
		scalars.put("gender", StandardBasicTypes.INTEGER);
		scalars.put("age", StandardBasicTypes.INTEGER);
		scalars.put("mobile", StandardBasicTypes.STRING);
		scalars.put("testDateTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("uploadDateTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("dispose", StandardBasicTypes.INTEGER);
		scalars.put("state", StandardBasicTypes.INTEGER);
		scalars.put("areaChain", StandardBasicTypes.STRING);
		scalars.put("bloodOxygen", StandardBasicTypes.INTEGER);
		scalars.put("heartbeat", StandardBasicTypes.INTEGER);
		
		PageObject page = this.queryObjectsBySql(sql.toString(),null,scalars, args.toArray(), queryInfo,MonitoringDataExtends.class);
		return page;
	}

	public BloodOxygen getLastUploadDateTime(int clientId) {
		StringBuffer  sql = new StringBuffer("select * from m_blood_oxygen b ");
		sql.append(" where b.clientId = ? and b.uploadDateTime = (SELECT MAX(uploadDateTime) FROM m_blood_oxygen WHERE clientId = b.clientId) ") ;
		
		ArrayList args = new ArrayList();
		args.add(clientId) ;
		List list =  (List)executeNativeQuery(sql.toString(),null,null,args.toArray(),BloodOxygen.class) ;
		if(CollectionUtils.isEmpty(list)){
			return null ;
		}else{
			return (BloodOxygen) list.get(0);			
		}		
	}	
	
	public List<BloodOxygen> getTodayUploadDateTimeList(int clientId) {
		StringBuffer  sql = new StringBuffer("select * from m_blood_oxygen b ");
		sql.append(" where b.clientId = ? and DATE(b.uploadDateTime) = DATE(NOW()) ") ;
		sql.append(" ORDER BY b.uploadDateTime DESC ");
		
		ArrayList args = new ArrayList();
		args.add(clientId) ;
		List list =  (List)executeNativeQuery(sql.toString(),null,null,args.toArray(),BloodOxygen.class) ;
		return list;
	}

	public Object queryLimiteSpO2(QueryCondition qc, BloodOxygen bo) {
		String sql = "select max(bloodOxygen),min(bloodOxygen),max(heartbeat)," +
				"min(heartbeat) from m_blood_oxygen where 1=1 ";

		ArrayList args = new ArrayList();
		if(null != bo) {
			if(null != bo.getClientId()) {
				sql += " and clientId = ?";
				args.add(bo.getClientId());
			}
		}
		
		if(null != qc) {
			if(null != qc.getBeginTime()) {
				sql += " and testDateTime >= ?";
				args.add(qc.getBeginTime());
			}
			if(null != qc.getEndTime()) {
				sql += " and testDateTime <= ?";
				args.add(qc.getEndTime());
			}
		}
		
		return findUniqueResultByNativeQuery(sql, args.toArray());
	}
	
	public Object queryAverageSpO2(QueryCondition qc, BloodOxygen bo) {
		String sql = "select round(avg(bloodOxygen)),round(avg(heartbeat)) from m_blood_oxygen where 1=1";

		ArrayList args = new ArrayList();
		if(null != bo) {
			if(null != bo.getClientId()) {
				sql += " and clientId = ?";
				args.add(bo.getClientId());
			}
		}
		
		if(null != qc) {
			if(null != qc.getBeginTime()) {
				sql += " and testDateTime >= ?";
				args.add(qc.getBeginTime());
			}
			if(null != qc.getEndTime()) {
				sql += " and testDateTime <= ?";
				args.add(qc.getEndTime());
			}
		}
		
		return findUniqueResultByNativeQuery(sql, args.toArray());
		
	}

	public PageObject getBloodOxygenAlarmData(QueryInfo queryInfo) {
		return  this.queryObjectsBySql("SELECT a.id,b.id as clientId,a.bloodOxygen ,a.heartbeat ,a.testDateTime,a.uploadDateTime,b.name,b.mobile FROM m_blood_oxygen as a,t_clientinfo as b where a.state<>2 and  (a.bloodOxygen<90 or a.heartbeat<50 or a.heartbeat >120) and a.dispose = 0 and b.id=a.clientid order by uploadDateTime desc",null,null,null,queryInfo,BloodOxygenAD.class) ;
	}
	
	/**查询某个用户在某个时间段测量数据的条数**/
	public int queryBloodOxygenCount(Integer clientId,Date date){
		String sql = "select count(*) from m_blood_oxygen where clientId = ? and uploadDateTime > ? and uploadDateTime< NOW()";
		List args = new ArrayList();
		args.add(clientId);
		args.add(date);
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		int count = 0;
		if(obj != null&&!obj.equals("")){
			count = Integer.parseInt(obj.toString());
		}
		return count;
	}
	
	/**查询某个用户某个时间端的血氧值情况**/
	public int queryBloodOxygen(Integer clientId,Date date,int maxVal,int minVal){
		int boCount = 0;
		List args = new ArrayList();
		String sql = "select count(*) from m_blood_oxygen where clientId = ? and uploadDateTime > ? and uploadDateTime< NOW()";
		args.add(clientId);
		args.add(date);
		if(minVal != 0){
			sql += " and bloodOxygen > ?";
			args.add(minVal);
		}
		if(maxVal != 0){
			sql += " and bloodOxygen< ?";
			args.add(maxVal);
		}
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj !=null && !obj.equals("")){
			boCount = Integer.parseInt(obj.toString());
		}
		return boCount;
	}
}
