package com.bskcare.ch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.ProductVsPackageDao;
import com.bskcare.ch.vo.ProductVsPackage;

/**
 * 产品Vs套餐
 * @author houzhiqing
 */
@Repository
public class ProductVsPackageDaoImpl extends BaseDaoImpl<ProductVsPackage> implements ProductVsPackageDao {

	public List<ProductVsPackage> executeFind(Integer productId) {
		String hql = "from ProductVsPackage where productId = ? order by id asc";
		return executeFind(hql, productId);
	}

	public int deleteByProductId(Integer id) {
		String hql = "delete from ProductVsPackage where productId = ?";
		return updateByHql(hql, id);
	}
	
}
