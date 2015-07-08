package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductItem;

public interface ProductItemService {

	public ProductItem add(ProductItem pi);

	public void update(ProductItem pi);

	public void delete(int id);

	public ProductItem load(int id);

	public List<ProductItem> executeFind(ProductItem pi);
	
	public PageObject<ProductItem> queryObjects(ProductItem pi, QueryInfo queryInfo);
	
	public String queryStringObjects(ProductItem pi, QueryInfo queryInfo);
	
	public List<Object> queryItemsByPackageId(Integer packageId);
	
	/**
	 * 根据产品Id查询服务项目
	 * @param productId 产品Id
	 * @param type 服务项目类型，如果type==0时查询所有，否则查询相应状态的项目
	 */
	public List<Object> queryServiceItems(Integer productId, int type);
	/**
	 * 和上面方法一样，只不过返回至类型不同
	 */
	public String queryStringItemsByPId(Integer productId, int type);
}
