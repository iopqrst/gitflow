package com.bskcare.ch.service;

import java.util.List;

import com.bskcare.ch.vo.ProductEquipment;

/**
 * 产品设备
 * 
 * @author houzhiqing
 */
public interface ProductEquipmentService {

	public ProductEquipment add(ProductEquipment pe);
	public ProductEquipment load(int id);
	public List<ProductEquipment> queryAllEquipments(ProductEquipment pe);
	
}
