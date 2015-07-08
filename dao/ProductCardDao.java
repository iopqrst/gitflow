package com.bskcare.ch.dao;

import java.util.List;
import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.bo.ProductCardExtend;
import com.bskcare.ch.bo.ProductExcel;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductCard;

/**
 * 产品卡
 * 
 * @author houzhiqing
 */
@SuppressWarnings("unchecked")
public interface ProductCardDao extends BaseDao<ProductCard> {
	public PageObject<ProductCard> queryObjects(ProductCard pc, QueryInfo info);

	/**
	 * 查询某日产品卡数量
	 */
	public Object queryPCodeByTime(String createTime);

	/**
	 * 获取某日最大产品卡数
	 */
	public Object queryMaxPCodeByTime(String createTime);

	/**
	 * 分配产品卡
	 * 
	 * @param pc
	 *            修改产品卡信息
	 * @param assignCount
	 *            分配数量
	 * @return
	 */
	public int updateProductCard(ProductCard pc, int assignCount);

	/**
	 * 获取为分配的产品卡数量
	 */
	public Object getUnAssignPCCount();

	/**
	 * 检验产品卡是否存在
	 * 
	 * @param code
	 * @return
	 */
	public List<ProductCard> checkCodeExists(String code);

	/**
	 * 检验产品卡和密码是否匹配
	 * 
	 * @param code
	 * @param codePwd
	 * @return
	 */
	public List<ProductCard> checkCodePwdExists(String code, String codePwd);

	/**
	 * 检验产品卡状态
	 * 
	 * @param activeStatus
	 * @return
	 */
	public List<ProductCard> checkCodeActive(String activeStatus);

	/**
	 * 设置产品卡的状态
	 * 
	 * @param code
	 *            产品卡号
	 * @return
	 */
	public int updatePcStatus(String code);
	
	public Object getPcAreaId(String code);
	/**查询服务激活码是否有效 **/
	public ProductCard findValidCard(String code, String codePwd);
	
	public ProductCard queryProductCard(ProductCard card);

	/**
	 * 根据代理商id,查询代理商下所有购产品卡的用户使用情况
	 */
	public PageObject<ProductCardExtend> findUserActiveProductCard(String areaChain,Integer status,QueryInfo queryInfo,ProductCardExtend productCard);
	public List findCustomerProductCardDate(Integer status);
	
	public List<ProductExcel> queryProductCardExcel(ProductCard pc);
	
	public ProductCard findPcInfoByCard(String code, String codePwd);

	/**
	 * 查询某个区域下第一个没有被使用的产品卡
	 * @param areaId
	 * @param productId
	 */
	public List<ProductCard> queryFirstUnusedCard(Integer areaId, Integer productId);
}
