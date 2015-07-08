package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.OrderProductRecord;

public interface OrderProductRecordDao extends BaseDao<OrderProductRecord>{
	
	public OrderProductRecord queryOrderRecord(Integer cid, Integer productId);
}
