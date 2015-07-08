package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductEquipmentDao;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ProductEquipment;

/**
 * 产品设备
 */
@Repository("productEquipmentDao")
@SuppressWarnings("unchecked")
public class ProductEquipmentDaoImpl extends BaseDaoImpl<ProductEquipment>
		implements ProductEquipmentDao {

	/**
	 * 获取所有设备
	 */
	public List<ProductEquipment> queryAllEquipments(ProductEquipment pe) {
		String hql = "from ProductEquipment p where p.status = ?";
		ArrayList args = new ArrayList();
		args.add(Constant.STATUS_NORMAL);
		if(null != pe) {
			if(!StringUtils.isEmpty(pe.getName())){
				hql += " and p.name like ?";
				args.add("%"+ pe.getName().toString() +"%");
			}
		}
		return this.executeFind(hql,args.toArray());
	}
}
