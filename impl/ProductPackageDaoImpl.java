package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductPackageDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ProductPackage;

/**
 * 产品套餐
 * @author houzhiqing
 */
@Repository("productPackageDao")
@SuppressWarnings("unchecked")
public class ProductPackageDaoImpl extends BaseDaoImpl<ProductPackage> implements ProductPackageDao {
	
	public List<ProductPackage> executeFind(ProductPackage t) {
		StringBuffer hql = new StringBuffer("from ProductPackage t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%"+ t.getName().trim() +"%");
			}
			if(t.getPrice() >= 0) {
				
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
		
		hql.append(" order by t.id desc");
		List<ProductPackage> list = executeFind(hql.toString(), args.toArray());
		return list;
	}
	
	public PageObject<ProductPackage> queryObjects(ProductPackage t, QueryInfo info) {
		StringBuffer hql = new StringBuffer("from ProductPackage t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%"+ t.getName().trim() +"%");
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
}
