package com.bskcare.ch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.ProductEquipmentDao;
import com.bskcare.ch.service.ProductEquipmentService;
import com.bskcare.ch.vo.ProductEquipment;

/**
 * 产品设备
 * @author houzhiqing
 */
@Service("productEquipmentService")
public class ProductEquipmentServiceImpl implements ProductEquipmentService {

	private ProductEquipmentDao productEquipmentDao;

	@Resource
	public void setProductEquipmentDao(ProductEquipmentDao productEquipmentDao) {
		this.productEquipmentDao = productEquipmentDao;
	}
	
	public ProductEquipment add(ProductEquipment pe) {
		return productEquipmentDao.add(pe);
	}
	
	public ProductEquipment load(int id) {
		return productEquipmentDao.load(id);
	}

	public List<ProductEquipment> queryAllEquipments(ProductEquipment pe) {
		return productEquipmentDao.queryAllEquipments(pe);
	}
	
	

	
}
