package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.ProductVsPackage;

/**
 * 产品套餐表
 * @author houzhiqing
 */
public interface ProductVsPackageDao extends BaseDao<ProductVsPackage>{
	
	public List<ProductVsPackage> executeFind(Integer productId);

	public int deleteByProductId(Integer id);
}
