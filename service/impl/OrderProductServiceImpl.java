package com.bskcare.ch.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.ParseConversionEvent;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.OrderProductExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.OrderProductDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ClientInfoService;
import com.bskcare.ch.service.OrderMasterService;
import com.bskcare.ch.service.OrderProductService;
import com.bskcare.ch.service.ProductCardService;
import com.bskcare.ch.util.CrmResponse;
import com.bskcare.ch.util.CrmURLConfig;
import com.bskcare.ch.util.DateJsonValueProcessor;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.HttpClientUtils;
import com.bskcare.ch.util.JsonUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ProductCard;
import com.bskcare.ch.vo.order.OrderMaster;
import com.bskcare.ch.vo.order.OrderProduct;

@Service
public class OrderProductServiceImpl implements OrderProductService {
	
	private Logger log = Logger.getLogger(OrderProductService.class);

	@Autowired
	private OrderProductDao orderProductDao;
	@Autowired
	private ClientInfoDao clientInfoDao;
	
	@Autowired
	private ClientInfoService clientInfoService;
	
	@Autowired
	private ProductCardService cardService;

	@Autowired
	private OrderMasterService orderService;
	

	public List<OrderProduct> queryOrderProductByOId(Integer orderId,
			Integer clientId) {
		return orderProductDao.queryOrderProductByOId(orderId, clientId);
	}

	/**
	 * 查询用户可用产品最大等级的订单产品
	 */
	public OrderProductExtend queryMaxLevelAvaliableProduct(Integer clientId) {
		return orderProductDao.queryMaxLevelAvaliableProduct(clientId);
	}
	
	/**
	 * 查询用户可用产品最大等级的订单产品
	 */
	public String queryMaxLevelProductForString(Integer clientId) {
		OrderProductExtend ope = orderProductDao.queryMaxLevelAvaliableProduct(clientId);
		if(null != ope) {
			JSONObject jo = JsonUtils.getJsonObject4JavaPOJO(ope, DateJsonValueProcessor.LONG_DATE_PATTERN);
			if(null != ope.getExpiresTime()) {
				jo.put("expireDays", DateUtils.getQuot(ope.getExpiresTime(), new Date()));
			}
			return jo.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * 获取为预约订单产品列表
	 */
	public List<OrderProduct> queryNotSubscribeOrderProduct() {
		return orderProductDao.queryNotSubscribeOrderProduct();
	}

	public List<OrderProduct> queryPayAndExpire(int isExpire) {
		return orderProductDao.queryPayAndExpire(isExpire);
	}

	public void settingAvalibaleProduct() {
		log.info(LogFormat.b("开始为用户设置可用产品"));
		List<OrderProduct> list = orderProductDao.queryPayAndExpire(0);
		if (!CollectionUtils.isEmpty(list)) {
			
			log.info(LogFormat.f("avaliable product total : " + list.size()));
			
			for (OrderProduct orderProduct : list) {
				ClientInfo client = clientInfoDao.load(orderProduct.getClientId());
				
				if(null == client) { 
					continue;
				}
				
				if(ClientInfo.STATUS_NORMAL != client.getStatus()) {
					continue; //如果是非正常用户
				}
				
				String availableProduct = client.getAvailableProduct();
				if (!StringUtils.isEmpty(availableProduct)) {
					//如果avaiableProduct 不为空 需要判断一下是否包含该product id ， 包含不添加，不包含添加
					if (!availableProduct.contains(orderProduct.getProductId() + ",")) {
						availableProduct += orderProduct.getProductId() + ",";
						client.setType(ClientInfo.TYPE_VIP);
						client.setAvailableProduct(availableProduct);
						clientInfoDao.updateInfoByProductExpire(client);
						
						log.info("client :" + client.getId() + ", set product :" + orderProduct.getProductId() );
					}
				} else {
					client.setType(ClientInfo.TYPE_VIP);
					client.setAvailableProduct(orderProduct.getProductId() + ",");
					clientInfoDao.updateInfoByProductExpire(client);
					
					log.info("client :" + client.getId() + ", set vip and product :" + orderProduct.getProductId() );
				}
			}
			
		} else {
			log.info(LogFormat.f(" avaliable product is empty !!"));
		}
		log.info(LogFormat.b("为用户设置可用产品结束"));
	}

	@SuppressWarnings("unchecked")
	public String quertOrderProductPager(Integer pager) {
		if(pager==null ||pager == 0) return "";
		QueryInfo info = new QueryInfo();
		info.setPageSize(100);//每次取100条
		info.setPageOffset(100*(pager-1));
		PageObject pageobj = orderProductDao.quertOrderProduct(info);
		if(pageobj==null|| pageobj.getTotalRecord()==0) return "";
		List list = pageobj.getDatas();
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		for (Object obj : list) {
			Object[] objs = (Object[])obj;
			jo = new JSONObject();
			jo.put("clientId", objs[0]);
			jo.put("buytime", DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN, objs[1]));
			jo.put("lastDate", DateUtils.formatDate(DateUtils.LONG_DATE_PATTERN, objs[2]));
			jo.put("amount", objs[3]);
			jo.put("unitPrice", objs[4]);
			jo.put("cardTypeName", objs[5]);
			jo.put("cardid", objs[6]);
			ja.add(jo);
		}
		System.out.println(ja.size()+"条");
		return ja.toString();
	}
	
	public void removeExpiresProduct(int type) {
		// 1、查询所有今日过期的产品 
		// 2、找到用户，修改用户avaliabeProduct(type)
		log.info(LogFormat.b("查询所有今日失效产品，修改client avaliableProduct column"));
		List<OrderProduct> list = this.queryPayAndExpire(type); 
		if (!CollectionUtils.isEmpty(list)) {
			log.info(LogFormat.f("查询失效产品总数为：" + list.size()));
			
			int i = 0;
			for (OrderProduct orderProduct : list) {
				ClientInfo client = clientInfoDao.load(orderProduct.getClientId());
				
				if(null == client) { 
					continue;
				}
				
				if(ClientInfo.STATUS_NORMAL != client.getStatus()) {
					continue; //如果是非正常用户
				}
				
				//avaliableProduct not empty
				if (!StringUtils.isEmpty(client.getAvailableProduct())) {
					String availableProduct = client.getAvailableProduct();
					if (availableProduct.contains(orderProduct.getProductId() + ",")) {
						availableProduct = availableProduct.replace(orderProduct.getProductId()+",","");
						client.setAvailableProduct(availableProduct);
						
						log.info("client :" + client.getId() + ", remove product :" + orderProduct.getProductId() );
						
						//如果移除后的product为空则将用户的类型设置为体验用户
						if(StringUtils.isEmpty(availableProduct)) {
							client.setType(ClientInfo.TYPE_EXPERIENCE);
							
							log.info("client :" + client.getId() + ", change client's type to experience");
						}
					}
					
				} else {
					if(ClientInfo.TYPE_VIP == client.getType()) {
						client.setType(ClientInfo.TYPE_EXPERIENCE);
						
						log.info("client :" + client.getId() + ", change client's type to experience");
					}
				}
				
				clientInfoDao.updateInfoByProductExpire(client);
				i++;
			}
			
			log.info(LogFormat.f("更新用户信息总数为：" + i));
		} else {
			log.info(LogFormat.f("查询失效产品总数为：0"));
		}
		log.info(LogFormat.b("修改今日失效产品结束"));
	}
	
	
	public String queryOrderProductMaxExpiresTime(Integer cid, Integer productId){
		JSONObject jo = new JSONObject();
		OrderProduct order = orderProductDao.queryOrderProductMaxExpiresTime(cid, productId);
		if(order != null){
			jo.put("code", 1);
			jo.put("msg", "查询成功");
			JSONObject json = new JSONObject();
			if(order.getExpiresTime() != null){
				json.put("expiresTime", DateUtils.longDate(order.getExpiresTime()));
			}else{
				json.put("expiresTime", "");
			}
			if(order.getCreateTime() != null){
				json.put("createTime", DateUtils.longDate(order.getCreateTime()));
			}else{
				json.put("createTime", "");
			}
			jo.put("data", json.toString());
		}else{
			jo.put("code", 1);
			jo.put("msg", "您当前没有购买此服务");
			jo.put("data", "");
		}
		return jo.toString();
	}
	
	
	public String activeService(String mobile, String pid, String orderSource){
		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pid)){
			return JsonUtils.encapsulationJSON(0, "参数有误", "").toString();
		}else{
			ClientInfo client = new ClientInfo();
			client.setMobile(mobile);
			ClientInfo clientInfo = clientInfoService.queryClientInfo(client);
			Integer cid = null;
			String result = "";
			if(clientInfo == null){
				if(!StringUtils.isEmpty(orderSource) && orderSource.equals("leyu")){
					client.setPassword("123456");
					client.setCompSource(1);  //1：表示乐语客户   
					ClientInfo c = clientInfoService.createExperienceClient(client, "php_sugar", "");
					cid = c.getId();
					//0：表示乐语购买服务升级， 但是没有注册
					result = activeProductInfo(pid, cid, 0, mobile);
				}
			}else{
				cid = clientInfo.getId();
				//1：表示乐语购买服务升级， 已经注册
				result = activeProductInfo(pid, cid, 1, mobile);
			}
			return result;
		}
	}


	public String activeProductInfo(String pid, Integer cid, Integer isReg, String mobile){
		Integer productId = null;
		Integer npid = null;
		if(pid.equals("0001")){
			productId = 10010;
			npid = 10007;
		}else if(pid.equals("0002")){
			productId = 10009;
			npid = 10008;
		}else if(pid.equals("0003")) {
			productId = 10011;
			npid = 10001;
		}else{
			productId = null;
		}
		
		if(productId == null){
			return JsonUtils.encapsulationJSON(0, "没有该服务", "").toString();
		}else{
			ProductCard pcard = cardService.queryFirstUnusedCard(
					Integer.parseInt(SystemConfig.getString("bloodsugar_areaid")), productId);
			
			JSONObject jo = new JSONObject();
			if(null != pcard) {
				OrderMaster order = new OrderMaster();
				OrderMaster newOrder = orderService.activateCode(cid, pcard, order, isReg);
				if(null != newOrder) {
					//同步订单到crm
					String url = CrmURLConfig.getString("audit_docInfo_url")
					+ CrmURLConfig.getString("sync_order_service");
			
					HashMap<String, String> parmas = new HashMap<String, String>();
					parmas.put("orderMaster.clientId", cid+"");
					parmas.put("orderMaster.productId", npid+"");
					parmas.put("orderMaster.mobile", mobile);
			
					//根据productId查询产品信息
					
					
					// 获得返回（跟根据url 和 参数）
					CrmResponse crs = HttpClientUtils.getCrmResponse(url, parmas);
					jo.accumulate("code", Constant.INTERFACE_SUCC);
					jo.accumulate("msg", "vip升级成功");
					jo.put("data", "");
					return jo.toString();
				} else {
					jo.accumulate("code", Constant.INTERFACE_FAIL);
					jo.accumulate("msg", "vip升级失败");
					jo.put("data", "");
					return jo.toString();
				}
			} else {
				jo.accumulate("code", Constant.INTERFACE_FAIL);
				jo.accumulate("msg", "vip升级失败，没有卡了！！");
				jo.put("data", "");
				return jo.toString();
			}
		}
	}
}
