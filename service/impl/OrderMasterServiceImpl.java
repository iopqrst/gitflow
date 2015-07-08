package com.bskcare.ch.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.OrderProductExtend;
import com.bskcare.ch.bo.ProductVsCount;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ClientInfoDao;
import com.bskcare.ch.dao.OrderMasterDao;
import com.bskcare.ch.dao.OrderProductDao;
import com.bskcare.ch.dao.OrderProductRecordDao;
import com.bskcare.ch.dao.OrderServiceItemDao;
import com.bskcare.ch.dao.ProductCardDao;
import com.bskcare.ch.dao.ProductDao;
import com.bskcare.ch.dao.ProductItemDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.AreaInfoService;
import com.bskcare.ch.service.ClientPcardService;
import com.bskcare.ch.service.CrmClientInfoService;
import com.bskcare.ch.service.OrderMasterService;
import com.bskcare.ch.service.ProductCardService;
import com.bskcare.ch.service.ShortMessagService;
import com.bskcare.ch.service.TaskListService;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.LogFormat;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.AreaInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.ClientPcard;
import com.bskcare.ch.vo.OrderProductRecord;
import com.bskcare.ch.vo.Product;
import com.bskcare.ch.vo.ProductCard;
import com.bskcare.ch.vo.ProductItem;
import com.bskcare.ch.vo.ShortMessage;
import com.bskcare.ch.vo.Systemconfig;
import com.bskcare.ch.vo.order.OrderMaster;
import com.bskcare.ch.vo.order.OrderProduct;
import com.bskcare.ch.vo.order.OrderServiceItem;

@Service
@SuppressWarnings("unchecked")
public class OrderMasterServiceImpl implements OrderMasterService {
	
	private static Logger log = Logger.getLogger(OrderMasterServiceImpl.class);

	@Autowired
	private OrderMasterDao orderDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ClientInfoDao clientInfoDao;
	@Autowired
	private OrderProductDao orderProductDao;
	@Autowired
	private ProductItemDao productItemDao;
	@Autowired
	private ProductCardDao productCardDao;
	@Autowired
	private OrderServiceItemDao orderServiceItemDao;
	@Autowired
	private ClientPcardService cpCardService;
	@Autowired
	private TaskListService taskListService  ;
	@Autowired
	private CrmClientInfoService crmClientService;
	@Autowired
	private AreaInfoService areaInfoService;
	@Autowired
	private ShortMessagService messageService;
	@Autowired
	private OrderProductRecordDao orderRecordDao;
	@Autowired
	private ProductCardService cardService;
	
	
	public int cancleOrder(Integer oId, Integer clientId) {
		return orderDao.updateOrderStatus(oId, clientId, OrderMaster.STATUS_CANCLE);
	}
	
	public int deleteOrder(Integer oId, Integer clientId) {
		return orderDao.updateOrderStatus(oId, clientId, OrderMaster.STATUS_DELETE);
	}
	
	public void createRegisterOrder(String type, ClientInfo reginfo) {
		log.debug(LogFormat.f("["+reginfo.getMobile()+"]初始化订单......"));
		OrderMaster order = new OrderMaster();
		order.setActive(OrderMaster.ACTIVED);
		order.setAreaId(reginfo.getAreaId());
		order.setClientId(reginfo.getId());
		order.setOrderPath(OrderMaster.ORDER_PATH_SALES);
		if (!StringUtils.isEmpty(reginfo.getVipCard())) {
			order.setProductCardCode(reginfo.getVipCard());
		}
		order.setStatus(OrderMaster.STATUS_FINISHED);
		order.setType(OrderMaster.TYPE_PRESONAL);
		order.setCreateTime(new Date());
		Integer productId = null;
		
		// VIP用户对应的产品Id
		if (ClientInfo.TYPE_VIP.equals(type)) {
			ProductCard pc = new ProductCard();
			pc.setCode(reginfo.getVipCard());
			pc = productCardDao.queryProductCard(pc);
			if (null != pc) {
				productId = pc.getMainProductId();
			}
		}
		// 体验用户默认对应的产品Id
		if (ClientInfo.TYPE_EXPERIENCE.equals(type)) {
			String vproduct = SystemConfig.getString("vistied_default_product");
			if (StringUtils.isEmpty(vproduct))
				return;
			productId = Integer.parseInt(vproduct);
		}
		
		log.info("init order[type="+ClientInfo.TYPE_EXPERIENCE+"] , current prudct id is " + productId);
		
		List<ProductVsCount> pcList = new ArrayList<ProductVsCount>();
		ProductVsCount pvc = new ProductVsCount();
		pvc.setCount(1);
		pvc.setpId(productId);
		pcList.add(pvc);
		
		order = createOrder(order, pcList, null);
		
		log.info(">>>>>>>>>> 注册初始化订单订单信息 : " + order.toString() + ", 订单产品信息 : product Id = " + productId);
	}
	
	public OrderMaster activateCode(Integer clientId, ProductCard pcard,OrderMaster om, Integer isReg) {
		// 未激活
		ClientInfo clientInfo = clientInfoDao.load(clientId);
		
		OrderMaster order = new OrderMaster();
		order.setActive(OrderMaster.ACTIVED);
		if(pcard.getAreaId() != null)
			order.setAreaId(pcard.getAreaId()); 
		else
			order.setAreaId(clientInfo.getAreaId());
		order.setClientId(clientId);
		order.setOrderPath(OrderMaster.ORDER_PATH_SALES);
		order.setProductCardCode(pcard.getCode());

		order.setStatus(OrderMaster.STATUS_FINISHED);
		order.setType(OrderMaster.TYPE_PRESONAL);
		order.setCreateTime(new Date());
//		order.setRemark(om.getRemark());

		List<ProductVsCount> pcList = new ArrayList<ProductVsCount>();
		ProductVsCount pvc = new ProductVsCount();
		pvc.setCount(1);
		pvc.setpId(pcard.getMainProductId());
		pcList.add(pvc);
		log.info(">>>>>>>>>>>>>>>>>>升级之<前>的用户区域链：" + clientInfo.getAreaChain());
		
		if(StringUtils.isEmpty(clientInfo.getVipCard())) { //如果用户是第一次使用产品卡，则保存为Vipcard
			clientInfo.setVipCard(pcard.getCode());
//			if(!clientInfo.getAreaChain().contains(SystemConfig.getString("bloodsugar_areaid")) &&  // 这一改不知未来何去何从呀!!
//			if(!clientInfo.getAreaChain().contains(SystemConfig.getString("tg_1000"))){//如果用户不是学糖高管区的 并且也不是 千人推广活动
//				clientInfo.setAreaId(pcard.getAreaId());
//				clientInfo.setAreaChain(areaInfoService.getAreaChainByAreaId(pcard.getAreaId()));
//			}
			
			if(pcard.getAreaId() != null){
				if(!clientInfo.getAreaChain().contains(SystemConfig.getString("bloodsugar_areaid")) && 
						!clientInfo.getAreaChain().contains(SystemConfig.getString("tg_1000"))){ //如果用户不是学糖高管区的 并且也不是 千人推广活动
					clientInfo.setAreaId(pcard.getAreaId());
					clientInfo.setAreaChain(areaInfoService.getAreaChainByAreaId(pcard.getAreaId()));
				}
			}
		}

		log.info(">>>>>>>>>>>>>>>>>>升级之<后>的用户区域链：" + clientInfo.getAreaChain());
		
		clientInfo.setAvailableProduct(getAvaiProduct(
				clientInfo.getAvailableProduct(), pcard.getMainProductId()));
		//clientInfo.setType(ClientInfo.TYPE_VIP);
		clientInfoDao.update(clientInfo);
		
		//同步修改crm的用户信息
		ClientInfo client = clientInfoDao.load(clientInfo.getId());
		
		crmClientService.updateCrmClientInfo(client,"web");
		
		updateProductCard(clientInfo, pcard.getCode());
		activeProductCard(pcard);
		
		OrderMaster newOrder = createOrder(order,
				pcList, clientInfo.getAreaId());
		//为助理医生创建提醒任务
		//taskListService.levelUp(clientInfo.getAreaId(), clientId);
		
		this.sendUpgradeMsg(clientId, clientInfo.getMobile(), pcard.getMainProductId(), isReg) ;
		return newOrder;
	}

	
	/**
	 * 发送升级提醒短信
	 */
	private void sendUpgradeMsg(Integer clientId, String mobile, Integer productId, Integer isReg) {
		String content = Message.getString("upgrade_vip_success");
		content = content.replace("{0}", Product.getProductName(productId));
		
		//乐语升级是否注册标识
		if(isReg != null){
			//标识没有注册血糖高管客户
			if(isReg == 0){
				String msgContent = Message.getString("active_vip_no_reg");
				content = msgContent.replace("{0}", Product.getProductName(productId));
			}else{
				String msgContent = Message.getString("active_vip_reg");
				content = MessageFormat.format(msgContent, new Object[]{Product.getProductName(productId), mobile, "123456"});;
			}
		}
		
		if(!StringUtils.isEmpty(content)) {
			ShortMessage shortMessage = new ShortMessage("升级vip", content, clientId, mobile);
			messageService.sendMessage(shortMessage, "more");
		}
	}

	/**
	 * 传入一个地区，从crm获得地区下所有地区链，取出传入地区下的第一级 
	 * @param area 地区id
	 * @return 获得的地区id
	 * 本地好像有，下面又写了一个本地查询的
	 */
//	public Integer getAreaIdByAreaInfo(Integer area){
//		//从crm拿到地区下所有的地区链
//		JSONArray ja  = userInfoService.getAreaInfoByAreaId(area);
//		System.out.println("区域id:"+area+"下所有的地区:"+ ja.toString());
//		int ss = 0;
//		Set<String> areaSet = new HashSet<String>();
//		for (Object obj : ja) {
//			JSONObject jo =(JSONObject)obj; 
//			String[] strs = jo.getString("flagId").split(",");
//			if(ss==0){
//				for (int i = 0; i < strs.length; i++) {
//					System.out.println(strs[i]);
//					if(strs[i].equals(area+"")){
//						ss=i;
//						break;
//					}
//				}
//			}
//			if(ss!=0&& strs[ss].equals(area+"") && strs.length>=(ss+1))
//				areaSet.add(strs[ss+1]);
//		}
//		if(areaSet.size()<1) return null;
//		List<Integer> list = new ArrayList<Integer>();
//		for (String string : areaSet) {
//			list.add(Integer.parseInt(string));
//		}
//		System.out.println("返回区域的下一级的区域id"+ list.toString());
//		return list.get(RandomUtils.getRandomIndex(list.size()));
//	}
	
	/**
	 * 传入一个地区，从本地获得地区下所有地区链，取出传入地区下的第一级 
	 * @param area 地区id
	 * @return 获得的地区id
	 */
	public Integer getAreaIdByAreaInfo(Integer area){
		AreaInfo areaInfo = new AreaInfo();
		areaInfo.setParentId(area);
		List<AreaInfo> list = areaInfoService.queryList(areaInfo);
		if(list == null || list.size() < 1) return null;
		
		return list.get(RandomUtils.getRandomIndex(list.size())).getId();
	}
	
	/**
	 * 获取可用产品信息
	 * @param availableProduct
	 * @param mainProductId
	 * @return
	 */
	private String getAvaiProduct(String availableProduct, Integer mainProductId) {
		if(!StringUtils.isEmpty(availableProduct)) {
			// 判断一下现在可用的产品是否包含要添加的，如果包含不添加，否则添加
			if(null != mainProductId && availableProduct.indexOf(mainProductId + Constant.RPT_TAG_SPLIT) < 0){
				return availableProduct + mainProductId + Constant.RPT_TAG_SPLIT;
			}
			
		} else {
			return mainProductId + Constant.RPT_TAG_SPLIT;
		}
		return availableProduct;
	}

	/**
	 * 更新用户vipcard
	 */
	private void updateProductCard(ClientInfo clientInfo, String code) {
		ClientPcard cp = new ClientPcard();
		cp.setClientId(clientInfo.getId());
		cp.setCreateTime(new Date());
		cp.setType(ClientInfo.TYPE_VIP);
		cp.setPcCode(code);
		cp.setStatus(Constant.STATUS_NORMAL);
		cpCardService.add(cp);
	}
	
	/**
	 * 激活产品卡
	 */
	private void activeProductCard(ProductCard card) {
		card.setActiveStatus(ProductCard.PC_STATUS_ACTIVE);
		card.setActiveTime(new Date());
		productCardDao.update(card);
	}

	public OrderMaster createOrder(OrderMaster om, List<ProductVsCount> pcList, Integer areaId) {
		
		log.info(">>>>>>>>>>>>>>>>>>> om = " + om.toString());
		log.info(">>>>>>>>>>>>>>>>>>> pcList = " 
				+ (!CollectionUtils.isEmpty(pcList) ? pcList.get(0).toString() : 
					" pcList is empty , please attention !!!!"));
		
		if(null != om && !CollectionUtils.isEmpty(pcList)) { 
			List<OrderProductExtend> opList = getOrderProduct(pcList,om.getClientId()); 
			if(CollectionUtils.isEmpty(opList)) {
				log.warn("the product not found ,please check it !!!");
				return null;
			}
			om.setCreateTime(new Date());
			om.setSalesPrice(getOrderSales(opList));
			
			if(StringUtils.isEmpty(om.getProductCardCode())) {
				ClientInfo ci =  clientInfoDao.load(om.getClientId());
				om.setProductCardCode(ci.getVipCard());
			}
			//添加订单信息
			OrderMaster myOrder = orderDao.add(om);
			//创建产品订单及产品包含的服务项目
			createOrderProduct(myOrder, opList, areaId);
			
			//更新用户类型（会员、亲情等等）
			updateClientType(opList,om.getClientId());
			
			//更新用户级别
			updateClientLevel(om.getClientId());
			return myOrder;
		} else {
			log.info(" order info is empty or product list is empty!!");
		}
		return null;
	}
	
	/**
	 * 更改用户类型
	 */
	private void updateClientType(List<OrderProductExtend> opList,
			Integer clientId) {
		ClientInfo clientInfo = clientInfoDao.load(clientId);
		
		boolean hasHealthProduct = false;
		for(OrderProductExtend ope : opList) { //如果产品类型是健康管理类的产品，并且不是体验套餐（0元）
			if(Product.CATEGORY_HEALTH_PRODUCT == ope.getCategory() && ope.getProductPrice() > 0) {
				hasHealthProduct = true;
				break;
			}
		}
		
		if(hasHealthProduct && !ClientInfo.TYPE_VIP.equals(clientInfo.getType())) {
			clientInfo.setType(ClientInfo.TYPE_VIP);
			clientInfoDao.update(clientInfo);
		}
	}

	/**
	 * 更改用户等级
	 */
	private void updateClientLevel(Integer clientId) {
		clientInfoDao.updateLevelByClientId(clientId);
	}

	/**
	 * 生产订单产品信息
	 * @param oId 订单id
	 * @param opList 订单产品对象
	 */
	private void createOrderProduct(OrderMaster order, List<OrderProductExtend> opList, Integer areaId) {
		log.info(LogFormat.f("初始化订单产品..."));
		if(CollectionUtils.isEmpty(opList)) return ;
		for (OrderProductExtend opo : opList) {
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setCreateTime(new Date());
			orderProduct.setOmId(order.getOmId());
			orderProduct.setProductId(opo.getProductId());
			orderProduct.setProductCount(opo.getProductCount());
			orderProduct.setProductPrice(opo.getProductCount() * opo.getProductPrice());
			orderProduct.setExpiresTime(opo.getExpiresTime());
			orderProduct.setClientId(order.getClientId());
			//添加产品卡
			OrderProduct oProduct = orderProductDao.add(orderProduct);
			log.debug(LogFormat.f("order product info : " + orderProduct.toString()));
//			//crm同步信息
//			int crmret = crmOrderService.syncOrder(oProduct,order.getProductCardCode());
//			if(crmret == 0)
//				System.out.println("crm同步订单信息失败");
//			else
//				System.out.println("crm同步订单信息成功");
			if(null != oProduct && null != oProduct.getProductId()) {
				// 创建订单服务产品items
				opo.setOpId(oProduct.getOpId());
				opo.setOmId(order.getOmId());
				opo.setClientId(order.getClientId());
				opo.setProductId(oProduct.getProductId());
				opo.setExpiresTime(oProduct.getExpiresTime());
				createOrderServiceItems(opo);
			}
			
			//新订单创建时间
			Date createTime = oProduct.getCreateTime();
			Integer cid = oProduct.getClientId();
			Integer productId = oProduct.getProductId();
			OrderProductRecord orderRecord = orderRecordDao.queryOrderRecord(cid, productId);

			if(orderRecord != null){
				//该产品最大过期时间
				Date expTime = orderRecord.getExpiresTime();
				//如果新订单的创建时间  >= 当前订单最大的过期时间
				if(createTime.compareTo(expTime) >= 0){
					orderRecord.setCreateTime(new Date());
					if(areaId != null){
						taskListService.levelUp(areaId, cid);
					}
				}
				orderRecord.setExpiresTime(oProduct.getExpiresTime());
				orderRecordDao.update(orderRecord);
				
			}else{
				OrderProductRecord orecord = new OrderProductRecord();
				orecord.setClientId(cid);
				orecord.setProductId(productId);
				orecord.setOpId(oProduct.getOpId());
				orecord.setCreateTime(createTime);
				orecord.setExpiresTime(oProduct.getExpiresTime());
				
				orderRecordDao.add(orecord);
				if(areaId != null){
					taskListService.levelUp(areaId, cid);
				}
			}

		}
	}
	
	/**
	 * 创建订单服务产品items
	 */
	private void createOrderServiceItems(OrderProductExtend opo) {
		log.info(LogFormat.f("初始化订单服务..."));
		// 查询订单产品服务项目
		List<Object> itmsList = productItemDao.queryServiceItems(opo.getProductId(), ProductItem.TYPE_OF_SERVICE);
		
		Date createTime = new Date();
		
		for (Object obj : itmsList) {
			
			ProductItem item = (ProductItem)((Object[])obj)[0];
			int quantity = (Integer)(((Object[])obj)[1]);
			
			OrderServiceItem osi = new OrderServiceItem();
			osi.setClientId(opo.getClientId());
			osi.setOpId(opo.getOpId());
			osi.setOmId(opo.getOmId());
			osi.setProductId(opo.getProductId());
			osi.setProItemId(item.getId());
			osi.setStatus(Constant.STATUS_NORMAL);
			
			int total = quantity * opo.getProductCount();
			osi.setSurplusAmount(total);
			osi.setAmount(total);
			osi.setItemName(item.getName());
			osi.setExpiresTime(opo.getExpiresTime()); 
			osi.setCreateTime(createTime);
			
			osi = orderServiceItemDao.add(osi);
			log.debug(LogFormat.f("order service info : " + osi.toString()));
		}
		
	}
	
	/**
	 * 计算订单产品价格
	 * @param opList 订单产品对象
	 * @return
	 */
	private Double getOrderSales(List<OrderProductExtend> opList) {
		Double salesPrice = 0.0;
		if(!CollectionUtils.isEmpty(opList)) {
			for (OrderProductExtend op : opList) {
				salesPrice += op.getProductPrice() * op.getProductCount();
			}
		}
		return salesPrice;
	}
	
	/**
	 * 获取订单中的产品信息
	 * @param orderProductParams 订单产品对象字符串
	 */
	private List<OrderProductExtend> getOrderProduct(List<ProductVsCount> pcList,Integer cid) {
		List<OrderProductExtend> ooList = new ArrayList<OrderProductExtend>();
		if(!CollectionUtils.isEmpty(pcList)) {
			String pIds = "";
			Map<Integer,Integer> pcMap = new HashMap<Integer, Integer>();
			
			ProductVsCount pc = null;
			
			for (int i = 0; i < pcList.size(); i++) {
				pc = pcList.get(i);
				pcMap.put(pc.getpId(), pc.getCount());
				pIds += pc.getpId();
				if(i != pcList.size() - 1) {
					 pIds += ",";
				}
			}
		
			List<Product> productList = productDao.queryProductByIds(pIds);
			
			OrderProductExtend orderProduct = null;
			for (Product p : productList) {
				//查询用户有同样类型的产品，并且有效，将时间叠加上。
				int days =0;
				OrderProduct order = orderProductDao.queryOrderProductMaxExpiresTime(cid, p.getId());
				if(order!=null){
					//上一张产品卡的剩余有效天数
					days = new Long(DateUtils.getQuot(order.getExpiresTime(), new Date())).intValue();
					System.out.println(days);
					System.out.println("用户的上一张产品编号为"+p.getId()+"的产品卡还剩余有效天数"+days);
					days += p.getCycle();
					System.out.println("加上本张卡的有效天数，总有效天数为"+days);
				}
				if(days==0) days = p.getCycle();
				Date expiresTime = DateUtils.getExpiresTime(days);
				orderProduct = new OrderProductExtend();
				orderProduct.setProductId(p.getId());
				orderProduct.setProductPrice(p.getCurrentPrice());
				orderProduct.setProductCount(pcMap.get(p.getId()));
				orderProduct.setCycle(p.getCycle());
				orderProduct.setCategory(p.getCategory());
				orderProduct.setExpiresTime(expiresTime);
				ooList.add(orderProduct);
			}
		}
		//
		return ooList;
	}

	public PageObject queryObject(String areaChain,OrderMaster orderMaster, QueryCondition qc,
			QueryInfo qi) {
		return orderDao.queryObject(areaChain,orderMaster, qc, qi);
	}
	
	/**
	 * 根据用户查找订单（该情况只是用与一个订单只有一种产品）
	 */
	public String queryOrderByClientId(Integer clientId, OrderMaster order, QueryInfo queryInfo){
		PageObject po = orderDao.queryOrderByClientId(clientId, order,queryInfo);
		List<Object> list = po.getDatas();
		
		JSONArray jOrder = new JSONArray();
		
		for(Object obj : list){
			Object[] aObject = (Object[])obj;
			
			JSONObject jo = new JSONObject();
			
			jo.put("omId", aObject[0]);
			jo.put("price",aObject[1]);
			jo.put("createTime",DateUtils.longDate(aObject[2]));
			jo.put("expiresTime",DateUtils.longDate(aObject[3]));
			jo.put("status",aObject[4]);
			
			String[] pids = (aObject[5]+"").split(",");
			if(null != pids && pids.length > 0) {
				String[] pnames = (aObject[6]+"").split(",");
//				String[] pimages = (aObject[7]+"").split(",");
				
				JSONArray pArray = new JSONArray();
				for(int i = 0 ; i < pids.length; i++) {
					JSONObject jp = new JSONObject();
					jp.put("pid", pids[i]);
					jp.put("pname", pnames[i]);
//					jp.put("pimage", pimages[i]);
					pArray.add(jp);
				}
				jo.put("product", pArray);
			}
			
			
			jOrder.add(jo);
		}
		return jOrder.toString();
	}

	public PageObject findOrderByClientId(Integer clientId, OrderMaster order, QueryInfo queryInfo){
		return orderDao.queryOrderByClientId(clientId, order, queryInfo);
	}
	
	public OrderMaster upgradeVIPOnline(Integer clientId, Integer areaId, Integer productId) {
		
		if(null == areaId) {
			areaId = Integer.parseInt(SystemConfig.getString("online_area_id"));
		}
		
		ProductCard pcard = cardService.queryFirstUnusedCard(areaId, productId);
		
		if(null != pcard) {
			return this.activateCode(clientId, pcard, null, null);
		} else {
			log.warn(LogFormat.f(" "));
			log.warn(LogFormat.f("没有足够的VIP卡了，该用户升级失败，clientId = " + clientId + ",productId = " + productId));
			log.warn(LogFormat.f(" "));
		}
		
		return null;
	}
}
