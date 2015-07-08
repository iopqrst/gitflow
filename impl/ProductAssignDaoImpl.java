package com.bskcare.ch.dao.impl;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ProductAssignExtend;
import com.bskcare.ch.dao.ProductAssignDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductAssign;

/**
 * 产品卡分配
 * 
 * @author houzhiqing
 */
@Repository("productAssignDao")
@SuppressWarnings("unchecked")
public class ProductAssignDaoImpl extends BaseDaoImpl<ProductAssign> implements
		ProductAssignDao {

	public PageObject queryObjects(ProductAssign pa, QueryInfo queryInfo, QueryCondition qc) {
		StringBuffer hql = new StringBuffer("select pa, p1.name , 'primary_family' ,ai.name, pe.name " +
				"from ProductAssign pa,Product p1, AreaInfo ai, ProductEquipment pe ");
		hql.append(" where pa.mainProductId = p1.id and pa.areaId = ai.id and pa.equipmentId = pe.id");
		ArrayList args = new ArrayList();
		if( pa != null){
			if(null != qc) {
				if(null != qc.getBeginTime()) {
					hql.append(" and pa.deliveryTime >= ?");
					args.add(qc.getBeginTime());
				}
				
				if(null != qc.getEndTime()) {
					hql.append(" and pa.deliveryTime <= ?");
					args.add(qc.getEndTime());
				}
			}
			
			if(null != pa.getEquipmentId()) {
				hql.append(" and pa.equipmentId = ?");
				args.add(pa.getEquipmentId());
			}
			
			if(null != pa.getAreaId()) {
				hql.append(" and pa.areaId = ?");
				args.add(pa.getAreaId());
			}
			
			if(null != pa.getMainProductId()) {
				hql.append(" and pa.mainProductId = ?");
				args.add(pa.getMainProductId());
			}
			
			if(null != pa.getFamilyProductId()) {
				hql.append(" and pa.familyProductId = ?");
				args.add(pa.getFamilyProductId());
			}
			
			if(!StringUtils.isEmpty(pa.getDeliveryName())){
				hql.append(" and pa.deliveryName like ?");
				args.add("%" + pa.getDeliveryName().trim() + "%");
			}
		}

		return queryPagerObjects(hql.toString(), args.toArray(), queryInfo);
	}

	public PageObject<ProductAssignExtend> queryProductAssignObjects(ProductAssign pa, QueryInfo queryInfo, QueryCondition qc) {
		StringBuffer sql = new StringBuffer("select m.*, n.name areaName from (select pa.*, p1.name productName, 'primary_family', pe.name " +
				" from t_product_assign pa, t_product p1, t_product_equipment pe");
		sql.append(" where pa.mainProductId = p1.id and pa.equipmentId = pe.id) m LEFT JOIN t_areainfo n on m.areaId = n.id where 1 = 1");
		ArrayList args = new ArrayList();
		if( pa != null){
			if(null != qc) {
				if(null != qc.getBeginTime()) {
					sql.append(" and m.deliveryTime >= ?");
					args.add(qc.getBeginTime());
				}
				
				if(null != qc.getEndTime()) {
					sql.append(" and m.deliveryTime <= ?");
					args.add(qc.getEndTime());
				}
			}
			
			if(null != pa.getEquipmentId()) {
				sql.append(" and m.equipmentId = ?");
				args.add(pa.getEquipmentId());
			}
			
			if(null != pa.getAreaId()) {
				sql.append(" and m.areaId = ?");
				args.add(pa.getAreaId());
			}
			
			if(null != pa.getMainProductId()) {
				sql.append(" and m.mainProductId = ?");
				args.add(pa.getMainProductId());
			}
			
			if(null != pa.getFamilyProductId()) {
				sql.append(" and m.familyProductId = ?");
				args.add(pa.getFamilyProductId());
			}
			
			if(!StringUtils.isEmpty(pa.getDeliveryName())){
				sql.append(" and m.deliveryName like ?");
				args.add("%" + pa.getDeliveryName().trim() + "%");
			}
		}

		sql.append(" order by m.id desc");
		return queryObjectsBySql(sql.toString(), null, null, args.toArray(), queryInfo, ProductAssignExtend.class);
	}
}
