package com.bskcare.ch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductLevelDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ProductLevelService;
import com.bskcare.ch.vo.ProductLevel;

/**
 * 产品等级
 * @author houzhiqing
 *
 */
@Service
public class ProductLevelServiceImpl implements ProductLevelService {
	
	@Autowired
	private ProductLevelDao productLevelDao;

	public void add(ProductLevel t) {
		productLevelDao.add(t);
	}

	public void update(ProductLevel t) {
		productLevelDao.update(t);
	}

	public void delete(Integer id) {
		ProductLevel p = productLevelDao.load(id);
		p.setStatus(Constant.STATUS_UNNORMAL);
		productLevelDao.update(p);
	}

	public ProductLevel load(Integer id) {
		return productLevelDao.load(id);
	}

	public List<ProductLevel> executeFind(ProductLevel t) {
		return productLevelDao.queryProductLevel(t);
	}
	
	public PageObject<ProductLevel> queryObjects(ProductLevel t, QueryInfo info) {
		return productLevelDao.queryObjects(t,info);
	}

}
