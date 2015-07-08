package com.bskcare.ch.service.impl;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.xml.sax.InputSource;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.OrderProductDao;
import com.bskcare.ch.dao.OrderServiceItemDao;
import com.bskcare.ch.dao.ServiceExpenseDao;
import com.bskcare.ch.service.BskExpertService;
import com.bskcare.ch.service.OrderServiceItemService;
import com.bskcare.ch.util.ArrayUtils;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.ListUtils;
import com.bskcare.ch.util.MD5Util;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.util.WebServiceUtils;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.order.OrderServiceItem;
import com.bskcare.ch.vo.order.ServiceExpense;

@Service
public class OrderServiceItemServiceImpl implements OrderServiceItemService {

	@Autowired
	private OrderServiceItemDao orderServiceItemDao;

	@Autowired
	private ServiceExpenseDao serviceExpenseDao;

	@Autowired
	private OrderProductDao orderProductDao;

	@Autowired
	private BskExpertService bskExpertService;

	protected transient final Logger log = Logger.getLogger(getClass());

	public int expenseService(String item, Integer clientId, int expenseCount,
			int recorded) {

		if (StringUtils.isEmpty(item) || null == clientId)
			return -1;

		// 判断该用户在有效期内是否有可用服务项目

		Integer itemId = Integer.parseInt(SystemConfig.getString(item));

		int result = Constant.NO_AVAILABLE_SERVICE;
		OrderServiceItem osi = orderServiceItemDao.queryServiceItem(clientId,
				itemId, expenseCount);

		if (null != osi) {
			if (0 != expenseCount) {
				osi.setSurplusAmount(osi.getSurplusAmount() - expenseCount);
				orderServiceItemDao.update(osi);
			}

			// 不需要记录消费明细
			if (ServiceExpense.NEED_RECORD == recorded) {
				ServiceExpense se = new ServiceExpense();
				se.setClientId(clientId);
				se.setExpenseTime(new Date());
				se.setExpenseCount(expenseCount);
				se.setOsId(osi.getOsId());
				se.setStatus(ServiceExpense.STATUS_SUC);

				serviceExpenseDao.add(se);
			}
			result = Constant.EXPENSE_SERVICE_SUC;
		}
		return result;
	}

	public int isServiceItemEnough(String item, Integer clientId) {
		// 判断该项目是否可用
		Integer itemId = Integer.parseInt(SystemConfig.getString(item));
		OrderServiceItem osi = orderServiceItemDao.queryServiceItem(clientId,
				itemId, 0);
		int result = Constant.NO_AVAILABLE_SERVICE;
		if (null != osi) {
			result = Constant.EXPENSE_SERVICE_SUC;
		} else {
			int amount = orderProductDao.queryServiceProductAmount(clientId);
			if (amount > 0) {
				result = Constant.SERVICE_EXPIRE;
			} else {
				result = Constant.NO_SELLING_SERVICE;
			}
		}
		return result;
	}

	public int queryOrderService(Integer clientId, Integer itemId) {
		int orderItem = 0;
		OrderServiceItem os = new OrderServiceItem();
		os.setClientId(clientId);
		os.setProItemId(itemId);
		List<OrderServiceItem> lstOs = orderServiceItemDao.queryOrderByItem(os);
		if(CollectionUtils.isEmpty(lstOs)){
			orderItem = -1;  //表示没有购买此服务
		}else{
			os.setExpiresTime(new Date());
			List<OrderServiceItem> lstOsi = orderServiceItemDao.queryOrderByItem(os);
			if(CollectionUtils.isEmpty(lstOsi)){
				orderItem = -2;  //表示服务已过期
			}else{
				//orderItem = orderServiceItemDao.queryOrderService(clientId, itemId);
				//表示有没有过期的电话服务，计算剩余的时间
				for (OrderServiceItem orderServiceItem : lstOsi) {
					orderItem += orderServiceItem.getSurplusAmount();
				}
			}
		}
		return orderItem;
	}

	@SuppressWarnings("unchecked")
	public boolean updateClientTalkService(ClientInfo info, String admin_uin) {
		Integer itemId = Integer.parseInt(SystemConfig.getString("item_telephone_intervention"));
		List<OrderServiceItem> orderServiceItems = orderServiceItemDao
				.quertOrderItems(info.getId(), itemId);// 查询用户所有的未失效电话干预服务项目集合
		if (!CollectionUtils.isEmpty(orderServiceItems)) {// 如果有服务项
			Date beginTime = null;
			// 存储服务项目id
			List osIdList = ListUtils.getFiledList(orderServiceItems, "osId");
			String items = ArrayUtils.join(osIdList.toArray());
			if (StringUtils.isEmpty(items)) {
				items = "0";
			}
			//查询所有客户的电话服务列表
			List<ServiceExpense> serviceExpenses = serviceExpenseDao
					.quertServiceExpense(info.getId(), items); // 获得纪录列表
			
			//如果有电话服务过，查询最后一次电话服务的时间。
			if (!CollectionUtils.isEmpty(serviceExpenses)) {
				beginTime = serviceExpenses.get(0).getExpenseTime();// 如果有记录根据最后一次记录，找之后的
			} else {
				beginTime = orderServiceItems.get(0).getCreateTime();// 如果没有纪录，根据服务信息查找,
			}// 获得查询开始时间，如果没有纪录，查询购买服务以后的记录，购买服务之前的不计算
		
			String xmlResout = gettTQresout(beginTime, admin_uin, info
					.getMobile());// 调用webservice查询接口数据
			
			getMinutesByWebserviceResout(info, xmlResout);// 根据返回的数据，获得用户所有有效的通话时间
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据返回的xml获得用户通话分钟数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void getMinutesByWebserviceResout(ClientInfo info, String resout) {
		int minutes = 0;
		if (StringUtils.isEmpty(resout)) {
			return;
		}
		StringReader read = new StringReader(resout);
		InputSource source = new InputSource(read);
		SAXBuilder sb = new SAXBuilder();
		try {
			Document doc = sb.build(source);
			Element root = doc.getRootElement();
			List jiedian = root.getChildren();
			Namespace ns = root.getNamespace();
			Element et = null;
			if (!CollectionUtils.isEmpty(jiedian)) {
				for (int i = 1; i < jiedian.size(); i++) {
					et = (Element) jiedian.get(i);// 循环依次得到子元素
					String beginTime = et.getChild("Start_time", ns).getText();
					String EndTime = et.getChild("End_time", ns).getText();
					if (!StringUtils.isEmpty(beginTime)
							&& !StringUtils.isEmpty(EndTime)
							&& StringUtils.isNumeric(beginTime)
							&& StringUtils.isNumeric(EndTime)// 有开始结束时间
							&& et.getChild("Is_called_phone", ns).getText()
									.equals("1")) {// 并且电话状态等于1
						minutes = Integer.parseInt(EndTime)
								- Integer.parseInt(beginTime);// 秒数
						// 有效的，未记录的通话记录时间
						int dd = minutes % 60;
						if (dd > 0) {
							minutes = minutes / 60 + 1;
						} else {
							minutes = minutes / 60;
						}
						Integer userId = null;
						if (!StringUtils.isEmpty(et.getChild("Called_id", ns)
								.getText())
								&& StringUtils.isNumeric(et.getChild(
										"Called_id", ns).getText())) {
							String calledId = et.getChild("Called_id", ns)
									.getText();
							// 根据坐席号查询userid
							if (bskExpertService
									.quertBskExpertByMobile(calledId) != null)
								userId = bskExpertService
										.quertBskExpertByMobile(calledId)
										.getUserId();
							else
								userId = null;

						}
						
						updateUserOrderService(minutes, info, new Date((Long
								.parseLong(et.getChild("Insert_time", ns)
										.getText())) * 1000), userId);// 根据用户通话时间和服务卡项目，更新服务项目数据并添加记录
					}
				}
			}
		} catch (Exception e) {
			log.error("请求tq接口数据失败，返回错误信息：" + resout);
			e.printStackTrace();
		}
	}

	/**
	 * 更新用户上次服务后的余量。并把上次服务后的所有有效通话记录保存
	 * 
	 * @param mins
	 *            用户通话分钟数
	 * @param info
	 *            用户信息
	 * @param expenseTime
	 *            用户通话开始时间
	 * @param calledId
	 *            用户电话服务人员坐席号
	 */
	private void updateUserOrderService(int mins, ClientInfo info,
			Date expenseTime, Integer userId) {
		// int mins = minute;
		List<OrderServiceItem> orderServiceItems = orderServiceItemDao
				.quertOrderItems(info.getId(),Integer.parseInt(SystemConfig.getString("item_telephone_intervention")));
		if (!CollectionUtils.isEmpty(orderServiceItems)) {
			for (OrderServiceItem orderServiceItem : orderServiceItems) {
				ServiceExpense expense = null;
				if (mins > 0) {
					if (orderServiceItem.getSurplusAmount() >= mins) {// 当前服务项目中的剩余量大于本次使用分钟数
						// 现在通话时间不等于余量，说明有新的有效通话产生，需要更新数据库
						if (expenseTime != null) {
							expense = new ServiceExpense();
							expense.setClientId(orderServiceItem.getClientId());
							expense.setExpenseCount(mins);
							expense.setOsId(orderServiceItem.getOsId());
							expense.setExpenseTime(expenseTime);
							// USER ID
							expense.setUserId(userId);
							serviceExpenseDao.add(expense);  //记录本次通话的开始时间
						}
						
						//修改剩余通话的分钟数
						orderServiceItemDao.updateOStalkSurplusAmount(
								orderServiceItem.getOsId(), mins);
						break;
					} else { //
						// 差值（不够的分钟数）
						if (orderServiceItem.getSurplusAmount() != 0) {
							int chazhi = mins
									- orderServiceItem.getSurplusAmount();
							if (expenseTime != null) {
								expense = new ServiceExpense();
								expense.setClientId(orderServiceItem
										.getClientId());
								expense.setExpenseCount(orderServiceItem
										.getSurplusAmount());
								expense.setOsId(orderServiceItem.getOsId());
								expense.setExpenseTime(expenseTime);
								expense.setUserId(userId);
								serviceExpenseDao.add(expense);
								log
										.info(">>>>>>>>>>>>>>>>>>>>>>>扣除用户语音干预服务余额：[服务id osId="
												+ orderServiceItem.getOsId()
												+ "]");
								orderServiceItemDao.updateOStalkSurplusAmount(
										orderServiceItem.getOsId(),
										orderServiceItem.getSurplusAmount());
							}
							mins = chazhi;//用户没有其他卡了，剩余没有扣除的时间。
						}
					}
				} else {
					break;
				}
			}
		}
	}

	public List<OrderServiceItem> quertOrderItems(Integer cid, Integer itemId) {
		return orderServiceItemDao.quertOrderItems(cid, itemId);
	}

	/**
	 * @param beginDate  打电话开始时间
	 * @param admin_uin  管理员id
	 * @param callerid   客户电话号码
	 * @return
	 */
	private String gettTQresout(Date beginDate, String admin_uin,
			String callerid) {
		String resout = null;
		if (beginDate != null) {
			// webservice wsdl
			String endpoint = "http://webservice.tq.cn/Servers/services/ServerNew?wsdl";
			// 要请求的方法名
			String method = "getPhoneRecordByClient";
			// 参数列表
			Object[] objs = new Object[] {
					"",
					admin_uin,
					"",
					MD5Util.digest32(SystemConfig
							.getString("tq_admin_password")), "", callerid, "",
					DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", beginDate),
					DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", new Date()) };
			try {
				resout = WebServiceUtils.requertInterface(endpoint, objs,
						method);
				log.info("请求webService成功  ，返回信息   >>");
			} catch (Exception e) {
				log.error(">>>>>>>>>>>>>>>>>>>>> 请求tq接口异常");
				e.printStackTrace();
			}
		}
		return resout;
	}

	public Map<String, String> quertUserKPI(String userId) {
		List list = orderServiceItemDao.quertUserKPI(userId);
		Map<String, String> map = null;
		if (!CollectionUtils.isEmpty(list)) {
			map = new HashMap<String, String>();
			map.put("size", list.size() + "");
			double mins = 0;
			for (Object obj : list) {
				Object[] objects = (Object[]) obj;
				String min=objects[4].toString();
				mins += Integer.parseInt(min);
			}
			map.put("count", mins+"");
			double ave = mins/list.size();
			map.put("ave", ave+"");
		}
		return map;
	}

}
