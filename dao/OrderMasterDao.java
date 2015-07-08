package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.order.OrderMaster;

/**
 * 用户订单
 * 
 */
@SuppressWarnings("unchecked")
public interface OrderMasterDao extends BaseDao<OrderMaster> {

	/**
	 * 修改用户订单状态（订单状态 1：未付款 2：处理中 3：完成订单 4：取消订单 ）
	 * 之所以要传递clientId的原因是避免串单的问题发生
	 */
	public int updateOrderStatus(Integer oId, Integer clientId, Integer status);

	/**
	 * 根据管理员用户Id查找该管理员管理区域用户的订单情况
	 */
	public PageObject queryObject(String areaChain,OrderMaster orderMaster, QueryCondition qc,
			QueryInfo qi);
	
	/**
	 * 根据用户查找订单（该情况只是用与一个订单只有一种产品）
	 */
	public PageObject queryOrderByClientId(Integer clientId, OrderMaster order, QueryInfo queryInfo);
}
