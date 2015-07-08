package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductLevelDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ProductLevel;

/**
 * 产品等级
 * 
 * @author houzhiqing
 */
@Repository
@SuppressWarnings("unchecked")
public class ProductLevelDaoImpl extends BaseDaoImpl<ProductLevel> implements
		ProductLevelDao {

	public List<ProductLevel> queryProductLevel(ProductLevel t) {
		StringBuffer hql = new StringBuffer(
				"from ProductLevel t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);

		if (null != t) {
			if (!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%" + t.getName().trim() + "%");
			}
		}

		List<ProductLevel> list = executeFind(hql.toString(), args.toArray());
		return list;
	}

	public PageObject<ProductLevel> queryObjects(ProductLevel t, QueryInfo info) {
		StringBuffer hql = new StringBuffer("from ProductLevel t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		
		if (null != t) {
			if (!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%" + t.getName().trim() + "%");
			}
		}
		return queryObjects(hql.toString(), args.toArray(), info);
	}
}
