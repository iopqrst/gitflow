package com.bskcare.ch.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bskcare.ch.bo.ProductCardExtend;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.ProductCard;

/**
 * 产品卡
 * 
 * @author houzhiqing
 * 
 */
@SuppressWarnings("unchecked")
public interface ProductCardService {

	public void add(ProductCard pc);

	public void update(ProductCard pc);

	public void delete(int id);

	public ProductCard load(int id);

	public PageObject<ProductCard> queryObjects(ProductCard pc,
			QueryInfo queryInfo);

	/**
	 * 生成产品卡
	 * 
	 * @param createTime
	 *            传入时间标识
	 * @param quantity
	 *            要生成产品卡的数量
	 * @return
	 */
	public String generateCode(String createTime, int quantity);

	/**
	 * 获取为分配的产品卡数量
	 */
	public int getUnAssignPCCount();

	/**
	 * 获取下载Excel相关信息
	 */
	public HSSFWorkbook getHSSFWorkbook(ProductCard pc);

	/**
	 * 验证产品卡是否存在，存在是否激活
	 * 
	 * @param productcard
	 * @return 0: unactive 1: active 2:not exist
	 */
	public int isCodeExists(String code);

	/**
	 * 判断用户账户和密码是否匹配
	 */
	public int isCodePwdTrue(String code, String codePwd);

	/**
	 * 根据产品卡判断是会员还是游客
	 * 
	 * @param code
	 *            产品卡号
	 * @param type
	 *            1：会员 3：游客
	 * @return String
	 */
	public String codeStatus(String code, String type);

	public int findPcAreaIdByCode(String code);
	/**查询服务激活码是否有效 **/
	public ProductCard findValidCard(String code, String codePwd);

	public ProductCard queryProductCard(ProductCard card);
	
	/**
	 * 根据代理商id,查询代理商下所有购产品卡的用户使用情况
	 */
	public PageObject<ProductCardExtend> findUserActiveProductCard(String areaChain,Integer status,QueryInfo queryInfo,ProductCardExtend productCard);
	
	/**
	 * 根据代理商id,
	 */
	public List findCustomerProductCardDate(Integer status);
	
	public HSSFWorkbook getHSSFWorkbookAssign(ProductCard t);
	
	public ProductCard findPcInfoByCard(String code, String codePwd);
	
	public ProductCard queryFirstUnusedCard(Integer areaId, Integer productId);
}
