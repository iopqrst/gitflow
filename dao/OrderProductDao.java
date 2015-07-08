package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.OrderProductExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.order.OrderProduct;

/**
 * 用户订单产品
 * 
 */
public interface OrderProductDao extends BaseDao<OrderProduct> {
	
	/**
	 * 创建订单包含产品等级的临时表
	 */
	public int createOplTmpTable(String tmpName);
	
	/***
	 * 删除创建临时表
	 */
	public int dropTmpTable(String tmpName);
	
	/***
	 * 计算服务即将到产品，并存信息到数据库中
	 * @param days 差多少天到期
	 * @param tmpName 临时表
	 * @return
	 */
	public int createServiceRemindRecord(int days, String tmpName);
	
	/**
	 * 根据用户Id和订单号查询订单产品
	 */
	public List<OrderProduct> queryOrderProductByOId(Integer orderId, Integer clientId);
	
	/**
	 * 查询用户可用产品最大等级的订单产品
	 */
	public OrderProductExtend queryMaxLevelAvaliableProduct(Integer clientId);
	
	/**
	 * 查询用户是否购买过服务产品 
	 */
	public int queryServiceProductAmount(Integer clientId);
	/**
	 * 查询用户是否购买过某个产品，返回列表
	 */
	public List<OrderProduct> queryOrderProductByPids(Integer clientId,String pids);
	
	/**
	 * 获取为预约订单产品列表
	 */
	public List<OrderProduct> queryNotSubscribeOrderProduct();
	
	
	/**
	 * 查询订单产品是否过期 
	 * isExpire 是否过期参数，0为未过期，1为今日过期（根据系统时间查的）,3包括之前过期的（主要是定时检查，以免漏掉）
	 */
	public List<OrderProduct> queryPayAndExpire(int isExpire);
	/**
	 * 查询用户某个产品最后到期的订单，如果用户没有该产品或全部过期，返回null
	 * @param cid  用户id
	 * @param productId   产品id
	 * @return
	 */
	public OrderProduct queryOrderProductMaxExpiresTime(Integer cid,Integer productId);
	/**
	 * 查询所有的产品订单，分页，与crm同步时使用
	 */
	public PageObject quertOrderProduct(QueryInfo info);
	
}
