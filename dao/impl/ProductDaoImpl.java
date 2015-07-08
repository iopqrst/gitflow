package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ProductExtend;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.Product;

/**
 * 综合产品
 * @author houzhiqing
 */
@Repository("productDao")
@SuppressWarnings("unchecked")
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {
	
	public List<Product> executeFind(Product t) {
		StringBuffer hql = new StringBuffer("from Product t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%"+ t.getName().trim() +"%");
			}
		}
		
		List<Product> list = executeFind(hql.toString(), args.toArray());
		return list;
	}
	
	public PageObject<Product> queryObjects(Product t, QueryInfo info) {
		StringBuffer hql = new StringBuffer("from Product t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(null != t) {
			if(!StringUtils.isEmpty(t.getName())) {
				hql.append(" and t.name like ?");
				args.add("%"+ t.getName().trim() +"%");
			}
		}
		return queryObjects(hql.toString(), args.toArray(), info);
	}

	public List<Product> queryProductByIds(String pIds) {
		StringBuffer hql = new StringBuffer("from Product t where t.status = ?");
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		
		if(!StringUtils.isEmpty(pIds)) {
			hql.append(" and t.id in (" + pIds + ")");
		}
		
		List<Product> list = executeFind(hql.toString(), args.toArray());
		return list;
	}
	
	public List<ProductExtend> queryProduct(Product t) {
		ArrayList args = new ArrayList();
		String sql = "select id,name from t_product where status = ?";
		args.add(Constant.STATUS_NORMAL);
		List<ProductExtend> list = executeNativeQuery(sql, args.toArray(), ProductExtend.class);
		return list;
	}
	
	public List<Product> queryProductList(){
		ArrayList args = new ArrayList();
		String sql = "select * from t_product where status = ? and isShow = ? order by currentPrice asc";
		args.add(Constant.STATUS_NORMAL);
		args.add(Product.ISSHOW_YES);
		List<Product> list = executeNativeQuery(sql, args.toArray(), Product.class);
		return list;
	}
}
