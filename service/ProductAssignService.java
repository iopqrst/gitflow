package com.bskcare.ch.service;

import com.bskcare.ch.bo.ProductAssignExtend;
import com.bskcare.ch.bo.ProductAssignObject;
import com.bskcare.ch.bo.ProductCardObject;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductAssign;
import com.bskcare.ch.vo.ProductCard;

/**
 * 产品卡分配
 * @author houzhiqing
 * 
 */
public interface ProductAssignService {

	public void add(ProductAssign pa);

	public void update(ProductAssign pa);

	public ProductAssign load(int id);

	public PageObject<ProductAssignExtend> queryObjects(ProductAssign pa, QueryInfo queryInfo, QueryCondition qc);
	
	/**
	 * 产品卡分配
	 */
	public ProductAssignObject assignProductCard(ProductAssign pa, ProductCard pc);
	
	/**
	 * 查询产品卡分配信息
	 */
	public ProductCardObject queryAssignInfo(ProductCard productCard);

}
