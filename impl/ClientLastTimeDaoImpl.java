//package com.bskcare.ch.dao.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.springframework.stereotype.Repository;
//import org.springframework.util.CollectionUtils;
//import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
//import com.bskcare.ch.dao.ClientLastTimeDao;
//import com.bskcare.ch.vo.ClientLastTime;
//
//@Repository
//@SuppressWarnings("unchecked")
//public class ClientLastTimeDaoImpl extends BaseDaoImpl<ClientLastTime> implements ClientLastTimeDao{
//	
//	public ClientLastTime queryLastTimeByClientId(Integer clientId){
//		String hql = "from ClientLastTime where clientId = ?";
//		List args = new ArrayList();
//		args.add(clientId);
//		List<ClientLastTime> lst = executeFind(hql, args.toArray());
//		if(!CollectionUtils.isEmpty(lst)){
//			return lst.get(0);
//		}
//		return null;
//	}
//	
//	public void updateLastTime(Integer clientId,String type){
//		String sql = "update t_client_last_time set "+type+" = ? where clientId = ?";
//		List args = new ArrayList();
//		args.add(new Date());
//		args.add(clientId);
//		updateBySql(sql, args.toArray());
//	}
//	
//	
//}
