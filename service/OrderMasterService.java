package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.ProductVsCount;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ProductCard;
import com.bskcare.ch.vo.order.OrderMaster;

/**
 * 用户订单信息
 */
@SuppressWarnings("unchecked")
public interface OrderMasterService {

	/**
	 * 注册用户时初始化订单信息
	 * @param type 用户类型（ClientInfo.TYPE_EXPERIENCE： 体验用户 ，ClientInfo.TYPE_VIP：vip用户）
	 * @param reginfo 注册用户信息
	 */
	public void createRegisterOrder(String type, ClientInfo reginfo);
	
	/**
	 * 激活产品卡号
	 * @param clientId 用户Id
	 * @param pcard 产品卡对象
	 * @param om 订单对象
	 */
	public OrderMaster activateCode(Integer clientId, ProductCard pcard,OrderMaster om, Integer isReg);
	
	/***
	 * 生成订单
	 */
	public OrderMaster createOrder(OrderMaster orderMaster, List<ProductVsCount> pcList, Integer areaId);

	/**
	 * 取消订单
	 */
	public int cancleOrder(Integer oId, Integer clientId);
	
	public int deleteOrder(Integer oId, Integer clientId);

	/**
	 * 根据管理员用户Id查找该管理员管理区域用户的订单情况
	 * 
	 * @param OrderMaster orderMaster 订单信息
	 * @param QueryCondition qc 查询条件
	 */
	public PageObject queryObject(String areaChain,OrderMaster orderMaster, QueryCondition qc,
			QueryInfo qi);
	
	/**
	 * 根据用户查找订单（该情况只是用与一个订单只有一种产品）
	 */
	public String queryOrderByClientId(Integer clientId, OrderMaster order, QueryInfo queryInfo);
	
	public PageObject findOrderByClientId(Integer clientId, OrderMaster order, QueryInfo queryInfo);
	/**
	 * 根据一个区域id，返回该区域下一级的所有区域随机一个。
	 * @param area
	 * @return
	 */
	public Integer getAreaIdByAreaInfo(Integer area);
	
	/**
	 * 在线升级VIP
	 * @param clientId
	 * @param areaId 产品卡分配区域, 不传使用默认
	 * @param productId 对应产品
	 * @return
	 */
	public OrderMaster upgradeVIPOnline(Integer clientId, Integer areaId, Integer productId) ;
}
