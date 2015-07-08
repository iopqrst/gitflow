package com.bskcare.ch.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.ProductCardExtend;
import com.bskcare.ch.bo.ProductExcel;
import com.bskcare.ch.dao.ProductCardDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.ProductCard;

/**
 * 产品卡
 * 
 * @author houzhiqing
 */
@Repository("productCardDao")
@SuppressWarnings("unchecked")
public class ProductCardDaoImpl extends BaseDaoImpl<ProductCard> implements
		ProductCardDao {

	public PageObject<ProductCard> queryObjects(ProductCard t, QueryInfo info) {
		StringBuffer hql = new StringBuffer("from ProductCard t where 1 = 1");
		ArrayList args = new ArrayList();
		if (null != t) {
			if (!StringUtils.isEmpty(t.getCode())) {
				hql.append(" and t.code like ?");
				args.add("%" + t.getCode().trim() + "%");
			}
			if (null != t.getAssignId()) {
				hql.append(" and t.assignId = ?");
				args.add(t.getAssignId());
			}
		}
		return queryObjects(hql.toString(), args.toArray(), info);
	}

	public Object queryPCodeByTime(String createTime) {
		String hql = "select count(t.code) from ProductCard t where 1 =1 ";
		ArrayList args = new ArrayList();
		if (!StringUtils.isEmpty(createTime)) {
			hql += " and t.code like ?";
			args.add("%" + createTime + "%");
		}
		return findUniqueResult(hql, args.toArray());
	}

	public Object queryMaxPCodeByTime(String createTime) {
		String hql = "select max(t.code) from ProductCard t where 1 =1 ";
		ArrayList args = new ArrayList();
		if (!StringUtils.isEmpty(createTime)) {
			hql += " and t.code like ?";
			args.add("%" + createTime + "%");
		}
		return findUniqueResult(hql, args.toArray());
	}

	/**
	 * 分配产品卡
	 * 
	 * @param pc
	 *            修改产品卡信息
	 * @param assignCount
	 *            分配数量
	 * @return
	 */
	public int updateProductCard(ProductCard pc, int assignCount) {
		if (null == pc)
			return 0;
		String sql = "update t_product_card set allowActiveCount = ? ,areaId = ?,"
				+ " assignId = ?, equipmentId = ?,familyProductId = ?,mainProductId =?"
				+ " where assignId is NULL and activeStatus = ? order by id asc LIMIT ?";

		ArrayList args = new ArrayList();
		args.add(pc.getAllowActiveCount());
		args.add(pc.getAreaId());
		args.add(pc.getAssignId());
		args.add(pc.getEquipmentId());
		args.add(pc.getFamilyProductId());
		args.add(pc.getMainProductId());
		args.add(ProductCard.PC_STATUS_UNACTIVE);
		args.add(assignCount);

		return updateBySql(sql, args.toArray());
	}

	public Object getUnAssignPCCount() {
		String hql = "select count(*) from ProductCard where assignId is null and activeStatus = ?";
		return findUniqueResult(hql, ProductCard.PC_STATUS_UNACTIVE);
	}

	public List<ProductCard> checkCodeExists(String code) {
		String hql = "from ProductCard where code= ?";
		List<ProductCard> list = executeFind(hql, code);
		return list;

	}

	public List<ProductCard> checkCodeActive(String activeStatus) {
		String hql = "from ProductCard where activeStatus= ?";
		List<ProductCard> list = executeFind(hql, activeStatus);
		return list;

	}

	public List<ProductCard> checkCodePwdExists(String code, String codePwd) {
		String hql = "from ProductCard  where code=? and codePwd=?";
		Object[] obj = { code, codePwd };
		List<ProductCard> list = executeFind(hql, obj);
		return list;
	}

	public int updatePcStatus(String code) {
		// TODO 该方法名
		String hql = "update t_product_card set activeStatus = ? , activeTime=? where code = ? ";
		ArrayList args = new ArrayList();
		args.add(ProductCard.PC_STATUS_ACTIVE);
		args.add(new Date());
		args.add(code);
		return updateBySql(hql, args.toArray());
	}

	public Object getPcAreaId(String code) {
		String hql = "select areaId from ProductCard where code= ?";
		Object obj = findUniqueResult(hql, code);
		return obj;
	}

	public ProductCard findValidCard(String code, String codePwd) {
		return (ProductCard) findUniqueResult(
				"from ProductCard where code=? and codePwd=? and activeStatus=?", new Object[] {code, codePwd, ProductCard.PC_STATUS_UNACTIVE});
	}

	public ProductCard queryProductCard(ProductCard card) {
		String hql = "from ProductCard where 1 = 1";
		ArrayList args = new ArrayList();
		if (null != card) {
			if (!StringUtils.isEmpty(card.getCode())) {
				hql += " and code = ?";
				args.add(card.getCode());
			}
			if (!StringUtils.isEmpty(card.getCodePwd())) {
				hql += " and codePwd = ?";
				args.add(card.getCodePwd());
			}
		}
		List<ProductCard> pcList = new ArrayList<ProductCard>();
		pcList = this.executeFind(hql, args.toArray());
		if (!pcList.isEmpty()) {
			return pcList.get(0);
		}
		return null;
	}

	public PageObject<ProductCardExtend> findUserActiveProductCard(String areaChain,
			Integer status,QueryInfo queryInfo,ProductCardExtend productCard) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append("SELECT t3.clientId , t3.productCode,t3.customerName , t3.mobile, t4.codePwd, t4.activeTime, t4.assignId, t4.mainProductId, t5.deliverytime, t4.activeStatus ");
		sql.append(" FROM ( SELECT t2.id AS clientId, t1.productCardCode AS productCode, t2.`name` AS customerName, t2.mobile,t2.areaChain ");
		sql.append(" FROM t_order_master t1 LEFT JOIN t_clientinfo t2 ON t1.clientId = t2.id	WHERE t1.productCardCode != '' AND t1.productCardCode IS NOT NULL and t2.`status` = ? ");
		sql.append(" ) t3 LEFT JOIN t_product_card t4 ON t3.productCode = t4.`code` LEFT JOIN t_product_assign t5 ON t5.id = t4.assignId ");
		args.add(status);
		sql.append(" where 1 = 1 ");
		if(null != productCard) {
			if(null != productCard.getCustomerName() && !"".equals(productCard.getCustomerName())) {
				sql.append(" AND t3.clientName like ? ");
				args.add("%" + productCard.getCustomerName().trim() + "%");
			}
			if(null != productCard.getMobile() && !"".equals(productCard.getMobile())) {
				sql.append(" AND t3.mobile like ? ");
				args.add("%" + productCard.getMobile().trim() + "%");
			}
			if(-1 != productCard.getActiveStatus()) {
				sql.append(" AND t4.activeStatus = ? ");
				args.add(productCard.getActiveStatus());
			}
			if(null != productCard.getDeliverytime()) {
				sql.append(" AND t5.deliverytime like ? ");
				args.add("%" + DateUtils.format(productCard.getDeliverytime()) + "%");
			}
		} 
		if(!StringUtils.isEmpty(areaChain)) {
			String[] areaChains = areaChain.split("#");
			sql.append(" and ( ");
			
			if(areaChains.length > 1){
				for (int i = 0; i < areaChains.length; i++) {
					sql.append(" t3.areaChain like ? ");
					args.add(areaChains[i]+"%");
					if(i != areaChains.length - 1) {
						sql.append(" or");
					}
				}
			} else {
				sql.append(" t3.areaChain like ? ");
				args.add(areaChains[0]+"%");
			}
			sql.append(")");
		}
		sql.append(" ORDER BY t5.deliverytime DESC, t3.productCode asc ");
		PageObject list = this.queryObjectsBySql(sql.toString(), null, null, args.toArray(),queryInfo, ProductCardExtend.class);
		return list;
	}

	public List findCustomerProductCardDate(Integer status) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append("SELECT DISTINCT ( DATE_FORMAT(f2.deliverytime, '%Y-%m-%d')) ");
		sql.append(" AS deliverytime FROM ( SELECT m1.* FROM t_product_card m1, ( ");
		sql.append(" SELECT t2.id areaid, t2. NAME FROM ");
		sql.append("  t_areainfo t2 ");
		sql.append(" WHERE 1 = 1 ");
		sql.append("  ");
		sql.append(" AND t2.`status` = ? ");
		sql.append(" ) m2 WHERE m1.areaid = m2.areaid ) p1 ");
		sql.append(" LEFT JOIN t_product_assign f2 ON p1.areaid = f2.areaId ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND f2.deliverytime BETWEEN DATE_SUB(NOW(), INTERVAL 6 MONTH) AND NOW() ");
		sql.append(" ORDER BY f2.deliverytime DESC ");
		args.add(status);
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), null);
	}
	
	public List<ProductExcel> queryProductCardExcel(ProductCard pc){
		List args = new ArrayList();
		String asql = "select * from t_product_card where mainProductId != '' and activeStatus != ?";
		args.add(ProductCard.PC_STATUS_DISABLED);
		if(pc != null){
			if(pc.getAssignId() != null){
				asql += " and assignId = ?";
				args.add(pc.getAssignId());
			}
		}
		String csql = "select * from t_product where status = 0";
		
		String sql = "select m.code,m.codePwd,n.name from ("+asql+") m left join ("+csql+") n on m.mainProductId = n.id";
		
		return executeNativeQuery(sql, args.toArray(), ProductExcel.class);
	}

	public ProductCard findPcInfoByCard(String code, String codePwd) {
		return (ProductCard) findUniqueResult(
				"from ProductCard where code=? and codePwd=? ", new Object[] {code, codePwd});
	}

	public List<ProductCard> queryFirstUnusedCard(Integer areaId, Integer productId) {
		String sql = "select * from t_product_card where assignId is not null" +
				" and areaId = ? and activeStatus = ? and mainProductId = ? order by id limit 1 ";
		return this.executeNativeQuery(sql, new Object[]{areaId, ProductCard.PC_STATUS_UNACTIVE, productId}, ProductCard.class);
	}
} 
