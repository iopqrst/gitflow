package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductItem;

/**
 * 产品项目
 * 
 * @author houzhiqing
 */
@SuppressWarnings("unchecked")
public interface ProductItemDao extends BaseDao<ProductItem> {
	public List<ProductItem> executeFind(ProductItem pi);

	public PageObject<ProductItem> queryObjects(ProductItem t, QueryInfo info);

	public List<Object> queryItemsByPackageId(Integer packageId);

	/**
	 * 根据产品Id查询服务项目
	 * @param productId 产品Id
	 * @param type 服务项目类型，如果type==0时查询所有，否则查询相应状态的项目
	 */
	public List<Object> queryServiceItems(Integer productId, int type);
}
