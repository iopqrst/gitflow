package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductPackage;

/**
 * 产品套餐
 * @author houzhiqing
 *
 */
public interface ProductPackageService {

	public ProductPackage add(ProductPackage pp, String itemsInfo);

	public void update(ProductPackage pp, String itemsInfo) throws Exception;

	public void delete(int id);

	public ProductPackage load(int id);

	public List<ProductPackage> executeFind(ProductPackage pp);
	
	public PageObject<ProductPackage> queryObjects(ProductPackage pp, QueryInfo queryInfo);
	
	public String queryStringObjects(ProductPackage pp, QueryInfo queryInfo);
	
}
