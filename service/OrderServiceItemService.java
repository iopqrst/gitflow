package com.bskcare.ch.service;

import java.util.List;
import java.util.Map;



import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.order.OrderServiceItem;

/**
 * 用户订单服务信息
 * 
 */
public interface OrderServiceItemService {

	/**
	 * 检查可用服务，消费服务项目，记录消费明细
	 * 
	 * @param item
	 *            消费服务条目
	 * @param clientId
	 *            用户Id
	 * @param expenseCount
	 *            消费次数
	 * @param recorded
	 *            是否要记录消费明细
	 *            <p>
	 *            ServiceExpense.NO_RECORD = 0; 不记录消费明细
	 *            ServiceExpense.NEED_RECORD = 1; 记录消费明细
	 *            </p>
	 * 
	 * @reurn Constant.NO_AVAILABLE_SERVICE = 0 //没有可用服务项目;
	 *        Constant.EXPENSE_SERVICE_SUC = 1 //可用服务项目扣除消费成功
	 */
	public int expenseService(String item, Integer clientId, int expenseCount,
			int recorded);

	/**
	 * 某个用户是否有足够的
	 * 
	 * @param item
	 *            消费服务条目
	 * @param clientId
	 *            用户
	 * @return Constant.EXPENSE_SERVICE_SUC = 1 //存在可用服务</br>
	 *         Constant.SERVICE_EXPIRE = 3 //服务过期</br>
	 *         Constant.NO_SELLING_SERVICE=4 //没有购买过任何产品服务</br>
	 */
	public int isServiceItemEnough(String item, Integer clientId);

	
	/**
	 * 查询某个客户，某个服务项的信息
	 * 
	 * @param item
	 *            服务条目
	 * @param clientId
	 *            客户id
	 * @return -1 //表示没有购买此服务</br>
	 *         -2 //表示服务已过期</br>
	 */
	public int queryOrderService(Integer clientId, Integer itemId);
	
	
	/**
	 * 返回某个用户所有的某个项目，未过期的，服务项目集合，按创建时间升序排列
	 * @param cid  客户id
	 * @param itemId  服务id
	 * @return
	 */
	public List<OrderServiceItem> quertOrderItems(Integer cid,Integer itemId);

	/**
	 * 更新用户电话干预服务信息
	 * 
	 * @param admin_uin
	 *            tq管理员账号号
	 * @param Info
	 *            用户信息
	 */
	public boolean updateClientTalkService(ClientInfo Info ,String admin_uin);
	/**
	 * 根据用户id获得 用户服务通话时长，通话次数，平均时长
	 * @param userId 用户id
	 * @return	返回map集合
	 * 			key		value
	 * 			size	该用户通话服务次数
	 * 		·	count	该用户通话服务总分钟数
	 * 			ave		该用户平均每次通话时长
	 */
	public Map<String, String> quertUserKPI(String userId);
}
