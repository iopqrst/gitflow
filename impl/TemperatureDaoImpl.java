package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.TemperatureDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.Temperature;

@Repository
@SuppressWarnings("unchecked")
public class TemperatureDaoImpl extends BaseDaoImpl<Temperature> implements TemperatureDao{
	
	public PageObject queryTemperatureList(Temperature temperature,QueryInfo queryInfo,QueryCondition queryCondition) {
		StringBuffer sql = new StringBuffer("select * from m_temperature t where 1 = 1 and t.clientId = ?");
		ArrayList args = new ArrayList();
		
		args.add(temperature.getClientId()) ;	
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
		
		return queryObjectsBySql(sql.toString(),null,null,args.toArray(),queryInfo,Temperature.class);
	}
}
