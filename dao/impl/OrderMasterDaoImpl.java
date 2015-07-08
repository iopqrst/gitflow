package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.OrderMasterExtend;
import com.bskcare.ch.dao.OrderMasterDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ClientInfo;
import com.bskcare.ch.vo.order.OrderMaster;

@Repository
@SuppressWarnings("unchecked")
public class OrderMasterDaoImpl extends BaseDaoImpl<OrderMaster> implements
		OrderMasterDao {

	public int updateOrderStatus(Integer oId, Integer clientId, Integer status) {
		String hql = "update OrderMaster set status = ? where oId = ? and clientId = ? ";
		Object[] args = new Object[] { status, oId, clientId };
		return updateByHql(hql, args);
	}

	/**
	 * 根据管理员用户Id查找该管理员管理区域用户的订单情况 Please forgive me
	 */
	public PageObject queryObject(String areaChain, OrderMaster order,
			QueryCondition qc, QueryInfo queryInfo) {

		if (null == qc)
			return null;

		ArrayList args = new ArrayList();
		String sql = "select n. NAME AS clientName,n.clientCode AS clientCode, n.areaId AS areaId, n.mobile AS mobile, n.id AS cid,m.omId AS omId,	x.productId AS productId,x.createTime , m.`status` as `status` , m.salesPrice as salesPrice from t_order_master m INNER JOIN t_clientinfo n ON m.clientId = n.id LEFT JOIN t_order_product x on x.omId = m.omId WHERE n.status = ?";
		args.add(ClientInfo.STATUS_NORMAL);
		if (!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			sql+=" and ( ";
			if (areaChains.length > 1) {
				for (int i = 0; i < areaChains.length; i++) {
					sql+=" n.areaChain like ? ";
					args.add(areaChains[i] + "%");
					if (i != areaChains.length - 1) {
						sql+=" or";
					}
				}
			} else {
				sql+=" n.areaChain like ? ";
				args.add(areaChains[0] + "%");
			}
			sql+=")";
		}
		if (null != qc) {
			if (!StringUtils.isEmpty(qc.getClientName())) {
				sql += " and n.`name` like ?";
				args.add("%" + qc.getClientName().trim() + "%");
			}
			if (!StringUtils.isEmpty(qc.getMobile())) {
				sql += " and n.mobile like ?";
				args.add("%" + qc.getMobile().trim() + "%");
			}
		}
		if (null != order) {
			if (null != order.getAreaId()) {
				sql += " and n.areaId = ?";
				args.add(order.getAreaId());
			}
		}
		if (null != qc) {
			if (null != qc.getBeginTime()) {
				sql +=" and x.createTime >= ?";
				args.add(qc.getBeginTime());
			}
			if (null != qc.getEndTime()) {
				sql +=" and x.createTime <= ?";
				args.add(qc.getEndTime());
			}
		}
		System.out.println("sql --> " + sql);
		return queryObjectsBySql(sql, null, null, args.toArray(), queryInfo,
				OrderMasterExtend.class);
	}

	public PageObject queryOrderByClientId(Integer clientId, OrderMaster order,
			QueryInfo queryInfo) {
		String sql = "select m.omId as omId, m.salesPrice as price, m.createTime as createTime,m.expiresTime as expiresTime,"
				+ " m.status as status,group_concat(m.`name`) as pnames,GROUP_CONCAT(m.productId) as pids,"
				+ " GROUP_CONCAT(m.imageUrl) as pimages from "
				+ "("
				+ " select t2.*,t3.`name`,t3.id as productId, t3.imageUrl,t1.expiresTime from t_order_product t1 "
				+ "left join t_order_master t2 on t1.omId = t2.omId left join t_product t3 on"
				+ " t1.productId = t3.id where t2.clientId = ? order by t1.opId asc"
				+ " ) m group by omId order by m.createTime desc";

		Map scalars = new LinkedHashMap();
		scalars.put("omId", StandardBasicTypes.INTEGER);
		scalars.put("price", StandardBasicTypes.DOUBLE);
		scalars.put("createTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("expiresTime", StandardBasicTypes.TIMESTAMP);
		scalars.put("status", StandardBasicTypes.INTEGER);
		scalars.put("pids", StandardBasicTypes.STRING);
		scalars.put("pnames", StandardBasicTypes.STRING);
		scalars.put("pimages", StandardBasicTypes.STRING);

		return this.queryObjectsBySql(sql, null, scalars,
				new Object[] { clientId }, queryInfo, null);
	}

}
