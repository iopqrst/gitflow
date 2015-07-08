package com.bskcare.ch.dao;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.ProductEquipment;

/**
 * 产品设备
 * 
 * @author houzhiqing
 */
public interface ProductEquipmentDao extends BaseDao<ProductEquipment> {
	public List<ProductEquipment> queryAllEquipments(ProductEquipment pe);
}
