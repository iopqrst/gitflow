package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductLevel;

/**
 * 产品等级
 * @author houzhiqing
 */
public interface ProductLevelDao extends BaseDao<ProductLevel>{
	public List<ProductLevel> queryProductLevel(ProductLevel productLevel);
	public PageObject<ProductLevel> queryObjects(ProductLevel productLevel, QueryInfo info);
}
