package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductItemDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ProductItem;

/**
 * 产品项目
 * @author houzhiqing
 */
@Repository("productItemDao")
@SuppressWarnings("unchecked")
public class ProductItemDaoImpl extends BaseDaoImpl<ProductItem> implements ProductItemDao {
	
	public List<ProductItem> executeFind(ProductItem t) {
		StringBuffer hql = new StringBuffer("from ProductItem t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%"+ t.getName().trim() +"%");
			}
			if(t.getType() > 0) {
				hql.append(" and t.type = ?");
				args.add(t.getType());
			}
			if(null != t.getBeginTime()){
				hql.append(" and t.createTime >= ?");
				args.add(t.getCreateTime());
			}
			if(null != t.getEndTime()){
				hql.append(" and t.createTime <= ?");
				args.add(t.getCreateTime());
			}
		}
		//hql.append(" order by t.createTime,t.id desc");
		
		List<ProductItem> list = executeFind(hql.toString(), args.toArray());
		return list;
	}
	
	public PageObject<ProductItem> queryObjects(ProductItem t, QueryInfo info) {
		StringBuffer hql = new StringBuffer("from ProductItem t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%"+ t.getName().trim() +"%");
			}
			if(t.getType() > 0) {
				hql.append(" and t.type = ?");
				args.add(t.getType());
			}
			if(null != t.getBeginTime()){
				hql.append(" and t.createTime >= ?");
				args.add(t.getCreateTime());
			}
			if(null != t.getEndTime()){
				hql.append(" and t.createTime <= ?");
				args.add(t.getCreateTime());
			}
		}
		
		return queryObjects(hql.toString(), args.toArray(), info);
	}

	public List<Object> queryItemsByPackageId(Integer packageId) {
		StringBuffer hql = new StringBuffer("select pi, pvi.quantity from ProductItem pi, PackageVsItem pvi where pi.id = pvi.itemId and pi.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(null != packageId) {
			hql.append(" and pvi.packageId = ?");
			args.add(packageId);
		}
		hql.append(" order by pvi.id asc");
		
		List list = executeFind(hql.toString(), args.toArray());
		return list;
	}
	
	public List<Object> queryServiceItems(Integer productId, int type) {
		String sql = "select {t1.*},t2.quantity q,t2.packageId p from t_product_item t1 ,("
						+ " select itemId,quantity,packageId from t_product_package_item where packageId " +
								" in (select packageId from t_product_relations where productId = ?)"
						+ " ) t2 where t1.id = t2.itemId and t1.`status` = ?";
		
		ArrayList args = new ArrayList();
		args.add(productId);
		args.add(Constant.STATUS_NORMAL);
		
		if(type != 0) {
			sql += " and t1.type = ?";
			args.add(type);
		}
		
		Map entities = new HashMap();
		entities.put("t1", ProductItem.class);
		
		Map scalars = new LinkedHashMap();
		scalars.put("q", StandardBasicTypes.INTEGER);
		scalars.put("p", StandardBasicTypes.INTEGER);
		
		List list =  executeNativeQuery(sql, entities, scalars, args.toArray(), null);
		return list;
	}
}
