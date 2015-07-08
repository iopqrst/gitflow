package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.bo.ProductExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.Product;

/**
 * 综合产品
 * @author houzhiqing
 * 
 */
public interface ProductService {

	public Product add(Product product, Integer[] packageIds);

	public void update(Product product, Integer[] packageIds);

	public void delete(Integer id);

	public Product load(Integer id);

	public List<Product> executeFind(Product product);
	
	public PageObject<Product> queryObjects(Product product, QueryInfo queryInfo);
	
	public String queryStringObjects(Product product, QueryInfo queryInfo);

	public String queryPackagesByPId(Integer id);
	
	public List<Product> queryProductByIds(String pIds);
	
	/**
	 * 查询所有产品信息及产品一下包含的所有项目信息
	 */
	@Deprecated
	public String queryPrductVsItems(Product product);
	
	public String queryStringOfProduct(Product product);
	
	public List<ProductExtend> queryProduct(Product t);
	
	public String queryProductList();
}
