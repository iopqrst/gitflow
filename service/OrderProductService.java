package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.OrderProductExtend;
import com.bskcare.ch.vo.order.OrderProduct;

/**
 * 用户订单产品信息
 *
 */
public interface OrderProductService {
	
	/**
	 * 根据用户Id和订单号查询订单产品
	 */
	public List<OrderProduct> queryOrderProductByOId(Integer orderId, Integer clientId);
	
	/**
	 * 查询用户可用产品最大等级的订单产品
	 */
	public OrderProductExtend queryMaxLevelAvaliableProduct(Integer clientId) ;
	
	/**
	 * 查询用户可用产品最大等级的订单产品
	 */
	public String queryMaxLevelProductForString(Integer clientId) ;
	
	/**
	 * 获取为预约订单产品列表
	 */
	public List<OrderProduct> queryNotSubscribeOrderProduct();
	
//	/**
//	 * 查询订单产品是否过期 
//	 * isExpire 是否过期参数，0为未过期，1为今日过期（根据系统时间查的）,3包括之前过期的（主要是定时检查，以免漏掉）
//	 */
//	public List<OrderProduct> queryPayAndExpire(int isExpire);
	
	/**
	 * 将用户购买未过期（截止到当前时间）的产品id存放到用户信息中
	 */
	public void settingAvalibaleProduct();
	
	/**
	 * 删除到期用户的product 修改用户type
	 * @param type 1:查询当前天的失效产品 3：查询之前所有失效的产品
	 */
	public void removeExpiresProduct(int type);
	/**
	 * 分页查询所有订单，与crm同步使用
	 */
	public String quertOrderProductPager(Integer pager);
	
	public String queryOrderProductMaxExpiresTime(Integer cid, Integer productId);
	
	public String activeService(String mobile, String pid, String orderSource);
}
