package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.OrderProductExtend;
import com.bskcare.ch.dao.OrderProductDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.order.OrderProduct;

@Repository
@SuppressWarnings("unchecked")
public class OrderProductDaoImpl extends BaseDaoImpl<OrderProduct> implements
		OrderProductDao {

	public int createOplTmpTable(String tmpName) {
		StringBuffer sql = new StringBuffer();
		sql.append("create table " + tmpName + " as (");
		sql
				.append("select t7.clientId, t6.id levelId from t_product_level t6 right join");
		sql.append("(");
		sql.append("select min(t5.priority) priority ,t5.clientId from (");
		sql
				.append("select t3.clientId, t4.priority from t_order_product t3 left JOIN");
		sql.append("(");
		sql
				.append("select t1.id as productId, t1.name, t2.id levelId, t2.priority from t_product t1"
						+ " left join t_product_level t2 on t1.levelId = t2.id");
		sql
				.append(") t4 on t3.productId = t4.productId where t3.expiresTime > now() ");// 只查询未失效的订单产品
		sql.append(") t5 group by t5.clientId ");
		sql.append(") t7 on t6.priority = t7.priority");
		sql.append(")");

		System.out.println(sql.toString());

		return updateBySql(sql.toString());
	}

	public int dropTmpTable(String tmpName) {
		String sql = "drop table " + tmpName;
		return updateBySql(sql);
	}

	public int createServiceRemindRecord(int days, String tmpName) {
		String sql1 = "create table "
				+ tmpName
				+ " as select * from"
				+ "	("
				+ "select t2.clientId,t2.productId,t2.omId,t2.createTime,t2.expiresTime,t2.remaining from ("
				+ "	select t1.*,(TO_DAYS(t1.expiresTime) - TO_DAYS(now())) remaining "
				+ "			from t_order_product t1 "
				+ "			where t1.expiresTime > now() and (TO_DAYS(t1.expiresTime) - TO_DAYS(now())) <= ?"
				+ ") t2 left join t_clientinfo t3 on t3.id = t2.clientId where t3.status = 0 "
				+ ") t";
		String sql2 = "update t_client_service_remind set remainingDays = (remainingDays - 1), createTime = now() where remainingDays > 0";
		String sql3 = "insert into t_client_service_remind(clientId,productId,omId,purchaseTime,expiresTime,remainingDays,createTime)"
				+ " select t1.*,now() as createTime from "
				+ tmpName
				+ " t1 where not exists "
				+ "(select 1 from t_client_service_remind t2 where t1.clientId = t2.clientId "
				+ " and t1.productId = t2.productId and t1.omId = t2.omId)";
		String sql4 = "drop table " + tmpName;

		updateBySql(sql1, days);
		updateBySql(sql2);
		updateBySql(sql3);
		updateBySql(sql4);
		return 0;
	}

	public List<OrderProduct> queryOrderProductByOId(Integer orderId,
			Integer clientId) {
		String hql = "from OrderProduct where omId = ? and clientId = ? order by createTime desc";
		return executeFind(hql, new Object[] { orderId, clientId });
	}

	public OrderProductExtend queryMaxLevelAvaliableProduct(Integer clientId) {
		String sql = "select t1.*,t2.`name` as pname from t_order_product t1 left join t_product t2 "
				+ " on t1.productId = t2.id left join t_product_level t3 on t3.id = t2.levelId "
				+ " where t1.expiresTime > now() and t1.clientId = ? order by priority,t1.createTime desc LIMIT 1";

		List<OrderProductExtend> list = this.executeNativeQuery(sql,
				new Object[] { clientId }, OrderProductExtend.class);

		return CollectionUtils.isEmpty(list) == true ? null : list.get(0);
	}

	public int queryServiceProductAmount(Integer clientId) {
		String hql = "select count(t) from OrderProduct t where expiresTime is not null and clientId = ? and productPrice > 0";
		Object obj = findUniqueResult(hql, clientId);
		return Integer.parseInt(obj + "");
	}

	public List<OrderProduct> queryNotSubscribeOrderProduct() {
		String sql = "select {t1.*} from t_order_product t1 where"
				+ " not exists(select omId from rpt_subscribe t2 where t1.omId = t2.omId)"
				+ " and t1.expiresTime > now() order by t1.clientId asc";

		Map entities = new HashMap();
		entities.put("t1", OrderProduct.class);
		return executeNativeQuery(sql, entities, null);
	}

	public List<OrderProduct> queryPayAndExpire(int isExpire) {
		String sql = "SELECT * FROM t_order_product ";
		sql += " where productPrice > 0 and expiresTime is not null and LENGTH(expiresTime) > 0 ";
		if (isExpire == 0) {
			sql += " and expiresTime > NOW()";
		} else if (isExpire == 1) {
			sql += " and DATE_FORMAT(expiresTime,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')";
		} else if(isExpire == 3){
			sql += " and expiresTime <= NOW()";
		}
		return executeNativeQuery(sql, null, OrderProduct.class);
	}

	public List<OrderProduct> queryOrderProductByPids(Integer clientId,
			String pids) {
		String hql = "from OrderProduct where clientid = ? and productId in("
				+ pids + ") and expiresTime > now() order by createTime";
		return executeFind(hql, new Object[] { clientId });
	}

	public OrderProduct queryOrderProductMaxExpiresTime(Integer cid,
			Integer productId) {
		if (cid == null || productId == null)
			return null;
		String hql = "from OrderProduct where productId = ? and clientId = ? AND expiresTime>NOW() ORDER BY expiresTime DESC";
		List args = new ArrayList();
		args.add(productId);
		args.add(cid);
		List<OrderProduct> list = executeFind(hql, args.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public PageObject quertOrderProduct(QueryInfo info) {
		String sql = "SELECT t.* FROM (" +
				" SELECT t.cid,t.createTime,t.expiresTime,t.productCount,t.productPrice,t.`NAME`,om.productCardCode FROM (" +
				" SELECT o.clientId AS cid, o.omId AS omid, o.createTime AS createTime, o.expiresTime AS expiresTime, o.productCount AS productCount, o.productPrice AS productPrice, p.`name` AS NAME" +
				" FROM t_order_product o, t_product p WHERE o.productId = p.id AND o.productId != 10005 ) " +//不是新用户注册卡
				"AS t LEFT JOIN t_order_master AS om ON t.omid = om.omId) as t";
		return queryObjectsBySql(sql, info);
		
	}

}
