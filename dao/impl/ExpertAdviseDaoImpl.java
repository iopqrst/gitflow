package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ExpertAdviseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ExpertAdvise;

@Repository("expertAdviseDao")
@SuppressWarnings("unchecked")
public class ExpertAdviseDaoImpl extends BaseDaoImpl<ExpertAdvise> implements ExpertAdviseDao{

	public PageObject<ExpertAdvise> getExpertAdvise(ExpertAdvise expertAdvise,QueryInfo queryInfo) {
		StringBuffer sql = new StringBuffer("select * from m_expertadvise t where 1 = 1 and t.clientId = ? and t.typeId = ?");
		ArrayList args = new ArrayList();
		
		args.add(expertAdvise.getClientId()) ;	
		args.add(expertAdvise.getTypeId()) ;	
		
		queryInfo.setSort(" t.dateTime ") ;
		queryInfo.setOrder(" DESC ") ;
		
		return queryObjectsBySql(sql.toString(),null,null,args.toArray(),queryInfo,ExpertAdvise.class);
	}

	public ExpertAdvise getLastExpertAdvise(ExpertAdvise expertAdvise) {
		StringBuffer sql = new StringBuffer("select * from m_expertadvise t where 1 = 1 and t.clientId = ? and t.typeId = ? and t.dateTime = (select max(dateTime) from m_expertadvise where clientId = t.clientId and typeId=t.typeId)");
		ArrayList args = new ArrayList();
		
		args.add(expertAdvise.getClientId()) ;	
		args.add(expertAdvise.getTypeId()) ;
		List list = executeNativeQuery(sql.toString(),args.toArray(),ExpertAdvise.class) ;
		if(!CollectionUtils.isEmpty(list)){
			return  (ExpertAdvise)list.get(0) ;
		}
		return null;
	}

	public void addExpertAdvise(ExpertAdvise expertAdvise) {
		// TODO Auto-generated method stub
		this.add(expertAdvise);
	}
	
}
