package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.OrderProductRecordDao;
import com.bskcare.ch.vo.OrderProductRecord;

@Repository
@SuppressWarnings("unchecked")
public class OrderProductRecordDaoImpl extends BaseDaoImpl<OrderProductRecord> implements OrderProductRecordDao{
	
	public OrderProductRecord queryOrderRecord(Integer cid, Integer productId){
		String hql = " from OrderProductRecord where 1 = 1";
		List args = new ArrayList();
		if(cid != null){
			hql += " and clientId = ?";
			args.add(cid);
		}
		if(productId != null){
			hql += " and productId = ?";
			args.add(productId);
		}
		
		hql += " order by expiresTime desc";
		List<OrderProductRecord> lst = executeFind(hql, args.toArray());
		if(!CollectionUtils.isEmpty(lst)){
			return lst.get(0);
		}
		
		return null;
	}
}
