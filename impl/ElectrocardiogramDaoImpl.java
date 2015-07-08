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
import com.bskcare.ch.bo.ElectrocardiogramAD;
import com.bskcare.ch.bo.MonitoringDataExtends;
import com.bskcare.ch.dao.ElectrocardiogramDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.Electrocardiogram;


@Repository("electrocardiogramDao")
@SuppressWarnings("unchecked")
public class ElectrocardiogramDaoImpl extends BaseDaoImpl<Electrocardiogram> implements ElectrocardiogramDao{

	public PageObject getListByElectrocardiogram(
			Electrocardiogram electrocardiogram, QueryInfo queryInfo,QueryCondition queryCondition) {
		
		StringBuffer sql = new StringBuffer("select * from m_electrocardiogram t where 1 = 1 and t.clientId = ?");
		ArrayList args = new ArrayList();
		
		args.add(electrocardiogram.getClientId()) ;	
		
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
		
		return queryObjectsBySql(sql.toString(),null,null,args.toArray(),queryInfo,Electrocardiogram.class);
	}

	public void addElectrocardiogram(Electrocardiogram electrocardiogram) {
		this.add(electrocardiogram) ;
	}
	
	public PageObject getAbnormalBSList(String areaChain,AbnormalCondition abnormalCondition,Electrocardiogram electrocardiogram,QueryInfo queryInfo) {		
		StringBuffer sql = new StringBuffer("select ci.id,ci.`name` clientName, ");
		sql.append(" ci.type,ai.`NAME` areaName,ci.gender,ci.age,ci.mobile, ");
		sql.append(" bp.testDateTime,bp.uploadDateTime,bp.dispose,bp.state,bp.averageHeartRate,bp.id dataId, ");
		sql.append(" ci.areaChain from m_electrocardiogram bp LEFT JOIN t_clientinfo ci on ci.id = bp.clientId ");
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
			if(abnormalCondition.getState()!=null&&abnormalCondition.getState()==1){
				sql.append(" and bp.state != 0 ") ;
			}else if(abnormalCondition.getState()!=null&&abnormalCondition.getState()==0){
				sql.append(" and bp.state = 0 ") ;
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
		scalars.put("averageHeartRate", StandardBasicTypes.INTEGER);
		scalars.put("dataId", StandardBasicTypes.INTEGER);
		
		PageObject page = this.queryObjectsBySql(sql.toString(),null,scalars, args.toArray(), queryInfo,MonitoringDataExtends.class);
		
		return page;
	}

	public Electrocardiogram getLastUploadDateTime(Integer clientId) {
		StringBuffer  sql = new StringBuffer("select * from m_electrocardiogram b ");
		sql.append(" where b.clientId = ? and  b.uploadDateTime = (SELECT MAX(uploadDateTime) FROM m_electrocardiogram WHERE clientId = b.clientId) ") ;
		
		ArrayList args = new ArrayList();
		args.add(clientId) ;
		List list =  (List)executeNativeQuery(sql.toString(),null,null,args.toArray(),Electrocardiogram.class) ;
		if(CollectionUtils.isEmpty(list)){
			return null ;
		}else{
			return (Electrocardiogram) list.get(0);			
		}		
	}
	
	public List<Electrocardiogram> getTodayUploadDateTimeList(Integer clientId) {
		StringBuffer  sql = new StringBuffer("select * from m_electrocardiogram b ");
		sql.append(" where b.clientId = ? and DATE(b.uploadDateTime) = DATE(NOW()) ") ;
		sql.append(" ORDER BY b.uploadDateTime DESC ");
		
		ArrayList args = new ArrayList();
		args.add(clientId) ;
		List list =  (List)executeNativeQuery(sql.toString(),null,null,args.toArray(),Electrocardiogram.class) ;
		return list;
	}

	public List<Electrocardiogram> queryElectrocardiogramData(Integer clientId) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from m_electrocardiogram e ");
		sql.append(" where e.clientid=? ");
		args.add(clientId);
		sql.append(" and e.testdatetime between date_sub(now(),interval 3 month) and now() ");
		sql.append(" order by e.state desc,e.testdatetime desc ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), Electrocardiogram.class);
	}
	
	public Electrocardiogram lod(int electrocardiogramId){
		return 	this.load(electrocardiogramId) ;
	}
	
	public Object queryElectrocardiogramAverage(QueryCondition qc, Electrocardiogram ed){
		String sql = "select AVG(averageHeartRate) from m_electrocardiogram where 1=1 ";

		ArrayList args = new ArrayList();
		if(null != ed) {
			if(null != ed.getClientId()) {
				sql += " and clientId = ?";
				args.add(ed.getClientId());
			}
		}
		
		if(null != qc) {
			if(null != qc.getBeginTime()) {
				sql += " and uploadDateTime >= ?";
				args.add(qc.getBeginTime());
			}
			if(null != qc.getEndTime()) {
				sql += " and uploadDateTime <= ?";
				args.add(qc.getEndTime());
			}
		}
		
		return findUniqueResultByNativeQuery(sql, args.toArray());
	}

	public PageObject getEDAlarmData(QueryInfo queryInfo) {
		return  this.queryObjectsBySql("SELECT a.id,b.id as clientId,a.averageHeartRate ,a.testDateTime,a.uploadDateTime,b.name,b.mobile  FROM m_electrocardiogram as a,t_clientinfo as b where a.state<>2 and  (a.averageHeartRate<50 or a.averageHeartRate>120 ) and a.dispose = 0 and b.id=a.clientid order by uploadDateTime desc",null,null,null,queryInfo,ElectrocardiogramAD.class) ;
	}
	
}
