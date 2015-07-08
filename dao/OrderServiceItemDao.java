package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.order.OrderServiceItem;

/**
 * 用户订单服务项目
 * 
 */
public interface OrderServiceItemDao extends BaseDao<OrderServiceItem> {

	/**
	 * 查询用户某个服务最早可用的订单服务项
	 */
	public OrderServiceItem queryServiceItem(Integer clientId, Integer itemId, int expenseCount);
	
	public int queryOrderService(Integer clientId, Integer itemId) ;
	/**
	 * 返回某个用户所有的某个项目，未过期的，服务项目集合，按创建时间升序排列
	 * @param cid  客户id
	 * @param itemId  服务id
	 * @return
	 */
	public List<OrderServiceItem> quertOrderItems(Integer cid,Integer itemId);
	/**
	 * 根据订单编号更新用户语音干预服务余量 
	 * @param osId
	 * @param mins
	 */
	public void updateOStalkSurplusAmount(Integer osId,Integer mins);
	/**
	 * 根据用户id获得 用户服务通话时长，通话次数，平均时长
	 */
	public List quertUserKPI(String userId);
	public List<OrderServiceItem> queryOrderByItem(OrderServiceItem os);
	
}
