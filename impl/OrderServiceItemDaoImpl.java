package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.OrderServiceItemDao;
import com.bskcare.ch.vo.order.OrderServiceItem;

@Repository
@SuppressWarnings("unchecked")
public class OrderServiceItemDaoImpl extends BaseDaoImpl<OrderServiceItem>
		implements OrderServiceItemDao {

	public OrderServiceItem queryServiceItem(Integer clientId, Integer itemId, int expenseCount) {
		String hql = "from OrderServiceItem osi where osi.expiresTime >= ? "
				+ " and osi.surplusAmount > ? ";
		
		ArrayList args = new ArrayList();
		args.add(new Date());
		args.add(expenseCount);
		
		if (null != clientId) {
			hql += " and osi.clientId = ?";
			args.add(clientId);
		}

		if (null != itemId) {
			hql += " and osi.proItemId = ?";
			args.add(itemId);
		}
		
		
		hql += " order by osi.createTime asc ";
		List<OrderServiceItem> list = executeFind(hql, args.toArray());
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Deprecated
	public int expenseService(Integer clientId, Integer itemId, int count) {
		String sql = "update t_order_service set surplusAmount = (surplusAmount - ?) " +
				" where clientId = ? and proItemId = ? and expiresTime > now() " +
				" and surplusAmount > 0 order by createTime asc";
		
		return updateBySql(sql, new Object[]{count, clientId, itemId});
	}
	
	public int queryOrderService(Integer clientId, Integer itemId) {
		String sql = "select sum(surplusAmount) from t_order_service osi where osi.expiresTime >= ?";
		ArrayList args = new ArrayList();
		args.add(new Date());
		if (null != clientId) {
			sql += " and osi.clientId = ?";
			args.add(clientId);
		}

		if (null != itemId) {
			sql += " and osi.proItemId = ?";
			args.add(itemId);
		}
		sql += " order by osi.createTime asc ";
		int sum = 0;
		Object obj = findUniqueResultByNativeQuery(sql, args.toArray());
		if(obj!=null&&!obj.equals("")){
			sum = Integer.parseInt(obj.toString());
		}
		return sum;
	}
	
	public List<OrderServiceItem> queryOrderByItem(OrderServiceItem os){
		if(os != null){
			List args = new ArrayList();
			String hql = "from OrderServiceItem where 1=1";
			if(os.getClientId() != null){
				hql += " and clientId = ?";
				args.add(os.getClientId());
			}
			if(os.getProItemId() != null){
				hql += " and proItemId = ?";
				args.add(os.getProItemId());
			}
			if(os.getExpiresTime() != null){
				hql += " and expiresTime >= ?";
				args.add(os.getExpiresTime());
			}
			return executeFind(hql,args.toArray());
		}
		return null;
	}

	public List<OrderServiceItem> quertOrderItems(Integer cid, Integer itemId) {
		String hql = "from OrderServiceItem where clientId = ? and proItemId = ? and status = ? and expiresTime > now() order by createTime asc ";
		if(cid!=null && itemId != null){
			return executeFind(hql,new Object[]{cid,itemId,OrderServiceItem.STATUS_SUC} );
		}
		return null;
	}

	public void updateOStalkSurplusAmount(Integer osId, Integer mins) {
		String sql="UPDATE t_order_service SET surplusAmount = surplusAmount-? where  osId = ?";
		ArrayList args = new ArrayList();
		args .add(mins);
		args.add(osId);
		updateBySql(sql,args.toArray());
	}

	public List quertUserKPI(String userId) {
		String sql="SELECT t1.* from t_service_expense as t1 LEFT JOIN t_order_service as t2 on t1.osId = t2.osId where t1.userId = ? and t2.proItemId = ?";
		ArrayList args = new ArrayList();
		args .add(userId);
		args.add(99);
		return executeNativeQuery(sql, args.toArray());
	}

}
