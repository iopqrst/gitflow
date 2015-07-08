package com.bskcare.ch.dao;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ProductAssignExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductAssign;

/**
 * 产品卡分配
 * @author houzhiqing
 */
public interface ProductAssignDao extends BaseDao<ProductAssign>{

	PageObject<Object> queryObjects(ProductAssign pa, QueryInfo queryInfo, QueryCondition qc);
	
	public PageObject<ProductAssignExtend> queryProductAssignObjects(ProductAssign pa, QueryInfo queryInfo, QueryCondition qc);
}
