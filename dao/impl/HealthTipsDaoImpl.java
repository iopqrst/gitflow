package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.HealthTipsDao;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.HealthReport;
import com.bskcare.ch.vo.HealthTips;

@Repository
@SuppressWarnings("unchecked")
public class HealthTipsDaoImpl extends BaseDaoImpl<HealthReport> implements HealthTipsDao{

	public HealthTips getHealthTips(HealthTips healthTips) {
		ArrayList<Object> olist = new ArrayList<Object>() ;
		olist.add(healthTips.getType()) ;
		olist.add(healthTips.getDegree()) ;
		olist.add(DateUtils.getSeasonInt(new Date())) ;
		
		List list = this.executeNativeQuery("select * from m_health_tips where type = ? and degree = ? and (season = ? or season = 5)  ORDER BY RAND() LIMIT 1",olist.toArray(),HealthTips.class) ;
		if(!CollectionUtils.isEmpty(list)){
			return (HealthTips) list.get(0);			
		}else{
			return null ;
		}
	}
	
}
