package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ProductExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Product;

/**
 * 综合产品
 * @author houzhiqing
 */
public interface ProductDao extends BaseDao<Product>{
	public List<Product> executeFind(Product product);
	public PageObject<Product> queryObjects(Product product, QueryInfo info);
	public List<Product> queryProductByIds(String pIds);
	
	public List<ProductExtend> queryProduct(Product t);
	
	public List<Product> queryProductList();
}
