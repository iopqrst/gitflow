package com.bskcare.ch.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bskcare.ch.base.exception.MessageException;
import com.bskcare.ch.bo.ProductAssignExtend;
import com.bskcare.ch.bo.ProductAssignObject;
import com.bskcare.ch.bo.ProductCardObject;
import com.bskcare.ch.dao.AreaInfoDao;
import com.bskcare.ch.dao.ProductAssignDao;
import com.bskcare.ch.dao.ProductCardDao;
import com.bskcare.ch.dao.ProductDao;
import com.bskcare.ch.dao.ProductEquipmentDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryCondition;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ProductAssignService;
import com.bskcare.ch.vo.AreaInfo;
import com.bskcare.ch.vo.Product;
import com.bskcare.ch.vo.ProductAssign;
import com.bskcare.ch.vo.ProductCard;
import com.bskcare.ch.vo.ProductEquipment;

/**
 * 产品卡分配
 * 
 * @author houzhiqing
 */
@Service("productAssignService")
public class ProductAssignServiceImpl implements ProductAssignService {

	private ProductAssignDao productAssignDao;
	private ProductCardDao productCardDao;
	private ProductEquipmentDao productEquipmentDao;
	private ProductDao productDao;
	private AreaInfoDao areaInfoDao;

	public void add(ProductAssign t) {
		productAssignDao.add(t);
	}

	public void update(ProductAssign t) {
		productAssignDao.update(t);
	}

	public ProductAssign load(int id) {
		return productAssignDao.load(id);
	}

	public PageObject<ProductAssignExtend> queryObjects(ProductAssign pa,
			QueryInfo queryInfo,QueryCondition qc) {
		//return productAssignDao.queryObjects(pa,queryInfo,qc);
		queryInfo.setSort(null);
		return productAssignDao.queryProductAssignObjects(pa, queryInfo, qc);
	}

	/**
	 * 分配产品卡
	 */
	public ProductAssignObject assignProductCard(ProductAssign pa, ProductCard pc) {
		ProductAssignObject pao = new ProductAssignObject();
		
		if (null == pa) {
			pao.setEffectCount(-1);
			return pao;
		}
		
		ProductAssign pAssign = productAssignDao.add(pa);
		if(null == pc) 
			pc = new ProductCard();
		
		pc.setAssignId(pa.getId());
		pc.setAreaId(pa.getAreaId());
		pc.setEquipmentId(pa.getEquipmentId());
		pc.setMainProductId(pa.getMainProductId());
		pc.setFamilyProductId(pa.getFamilyProductId());
		
		int effectCount = productCardDao.updateProductCard(pc, pAssign
				.getDeliveryCount());
		
		if (effectCount != pAssign.getDeliveryCount()) {
			throw new MessageException(
					"assign product card fail , try again later!!");
		}
		
		pao.setAssignId(pa.getId());
		pao.setEffectCount(effectCount);
		return pao;
	}

	/**
	 * 查询产品卡分配信息
	 */
	public ProductCardObject queryAssignInfo(ProductCard productCard) {
		if (null == productCard)
			return null;

		ProductCardObject pco = new ProductCardObject(); // 保存产品卡信息
		// 获取产品信息
		Product mp = productDao.load(productCard.getMainProductId());
		//Product fp = productDao.load(productCard.getFamilyProductId());
		AreaInfo ai = new AreaInfo();
		if(productCard.getAreaId() != null)
			ai = areaInfoDao.load(productCard.getAreaId());
		ProductAssign pa = productAssignDao.load(productCard.getAssignId()); // 获取产品卡分配信息
		ProductEquipment pe = productEquipmentDao.load(productCard
				.getEquipmentId()); // 获取设备信息

		pco.setEquipment(pe.getName());
		pco.setMainProductName(mp.getName());
		//pco.setFamilyProductName(fp.getName());
		pco.setDeliveryName(pa.getDeliveryName());
		pco.setDeliveryCount(pa.getDeliveryCount());
		pco.setDeliveryTime(pa.getDeliveryTime());

		pco.setAllowCreateCount(productCard.getAllowActiveCount());
		pco.setAreaName(ai.getName());
		pco.setAssignId(productCard.getAssignId());

		return pco;
	}

	@Resource
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Resource
	public void setProductAssignDao(ProductAssignDao ProductAssignDao) {
		this.productAssignDao = ProductAssignDao;
	}

	@Resource
	public void setProductCardDao(ProductCardDao productCardDao) {
		this.productCardDao = productCardDao;
	}

	@Resource
	public void setProductEquipmentDao(ProductEquipmentDao productEquipmentDao) {
		this.productEquipmentDao = productEquipmentDao;
	}

	@Resource
	public void setAreaInfoDao(AreaInfoDao areaInfoDao) {
		this.areaInfoDao = areaInfoDao;
	}

}
