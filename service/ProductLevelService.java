package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductLevel;

/**
 * 产品等级
 * @author houzhiqing
 * 
 */
public interface ProductLevelService {

	public void add(ProductLevel pl);

	public void update(ProductLevel pl);

	public void delete(Integer id);

	public ProductLevel load(Integer id);

	public List<ProductLevel> executeFind(ProductLevel pl);
	
	public PageObject<ProductLevel> queryObjects(ProductLevel pl, QueryInfo queryInfo);
	
}
