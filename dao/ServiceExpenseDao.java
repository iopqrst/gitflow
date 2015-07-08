package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.order.ServiceExpense;

/**
 * 订单服务消费明细
 */
public interface ServiceExpenseDao extends BaseDao<ServiceExpense> {
	List<ServiceExpense> quertServiceExpense(Integer cid,String items);
}
