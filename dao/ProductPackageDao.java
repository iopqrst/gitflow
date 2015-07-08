package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductPackage;

/**
 * 产品套餐
 * @author houzhiqing
 */
public interface ProductPackageDao extends BaseDao<ProductPackage>{
	public List<ProductPackage> executeFind(ProductPackage pp);
	public PageObject<ProductPackage> queryObjects(ProductPackage pp, QueryInfo info);
}
