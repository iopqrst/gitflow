package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ClientWeightDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientWeight;

@Repository
@SuppressWarnings("unchecked")
public class ClientWeightDaoImpl extends BaseDaoImpl<ClientWeight> implements ClientWeightDao{
	
	public ClientWeight queryWeightClient(ClientWeight weight){
		if(weight.getClientId() == null) return null;
		
		String sql = "select * from m_weight where clientId = ? and " +
				"date_format(uploadTime,'%y-%m-%d') = date_format(DATE_SUB(curdate(), INTERVAL 0 day),'%y-%m-%d')";
		List args = new ArrayList();
		args.add(weight.getClientId());
		
		List<ClientWeight>  lst = executeNativeQuery(sql, args.toArray(), ClientWeight.class);
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		
		return null;
	}
	
	
	public PageObject queryWeightByClientId(Integer clientId, Date startDate, Date endDate, QueryInfo queryInfo,int month){
		String sql = "";
		if(month == 0){
			sql = "select * from m_weight where clientId = ? and" +
				" uploadTime >= ? and" +
				" uploadTime <= ? " +
				"order by uploadTime asc";
		}else{
			sql = "select * from m_weight where clientId = ? and" +
			" date_format(uploadTime,'%Y-%m') >=date_format(?,'%Y-%m') and" +
			" date_format(uploadTime,'%Y-%m')<=date_format(?,'%Y-%m') " +
			"order by uploadTime asc";
		}
		
		List args = new ArrayList();
		args.add(clientId);
		args.add(startDate);
		args.add(endDate);
		
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo, ClientWeight.class);
	}
	
}
