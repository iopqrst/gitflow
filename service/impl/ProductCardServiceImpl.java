package com.bskcare.ch.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bskcare.ch.bo.ProductCardExtend;
import com.bskcare.ch.bo.ProductExcel;
import com.bskcare.ch.constant.Constant;
import com.bskcare.ch.dao.ProductCardDao;
import com.bskcare.ch.poi.util.ExcelUtil;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.ProductCardService;
import com.bskcare.ch.util.Message;
import com.bskcare.ch.util.RandomUtils;
import com.bskcare.ch.vo.ProductCard;

/**
 * 产品卡
 * 
 * @author houzhiqing
 * 
 */
@Service("productCardService")
@SuppressWarnings("unchecked")
public class ProductCardServiceImpl implements ProductCardService {

	private ProductCardDao productCardDao;

	@Resource
	public void setProductCardDao(ProductCardDao ProductCardDao) {
		this.productCardDao = ProductCardDao;
	}

	public void add(ProductCard t) {
		productCardDao.add(t);
	}

	public void update(ProductCard t) {
		productCardDao.update(t);
	}

	public void delete(int id) {

	}

	public ProductCard load(int id) {
		return productCardDao.load(id);
	}

	public PageObject<ProductCard> queryObjects(ProductCard t, QueryInfo info) {
		return productCardDao.queryObjects(t, info);
	}

	/**
	 * 生成产品卡
	 * 
	 * @param createTime
	 *            传入时间标识
	 * @param quantity
	 *            要生成产品卡的数量
	 * @return 返回生成的数量，如果为-XX表示传入日期已经不能再生成产品卡,
	 */
	public String generateCode(String createTime, int quantity) {
		String msg = "";
		int result = -1;
		if (!StringUtils.isEmpty(createTime) && quantity > 0) {

			Object obj = (Object) productCardDao.queryPCodeByTime(createTime);
			// 当日是否已经生过产品卡，返回生成数量
			int existCodeCount = 0;
			if (null != obj) {
				existCodeCount = Integer.parseInt(obj.toString());
			}

			if (quantity <= (Constant.MAX_PRODUCT_QUANLITY - existCodeCount)) {
				result = 0;
				int start = 0;
				if (existCodeCount != 0) {
					Object objCode = productCardDao
							.queryMaxPCodeByTime(createTime);
					if (null != objCode) {
						String maxCode = objCode + "";
						maxCode = maxCode.substring(maxCode.length()-4);
						start = Integer.parseInt(maxCode);
					}
				}
				for (int i = 1; i <= quantity; i++) {
					ProductCard pc = new ProductCard();
					if (existCodeCount != 0) {
						start++;// 在当日已存在卡号递增
						pc.setCode(createTime
								+ RandomUtils.getSuffixCode(4, start));
					} else {
						pc
								.setCode(createTime
										+ RandomUtils.getSuffixCode(4, i));
					}
					pc.setCodePwd(RandomUtils.getRandomNumber(6));
					pc.setCreateTime(new Date());
					pc.setActiveStatus(ProductCard.PC_STATUS_UNACTIVE);
					add(pc);
					result++;
				}
				msg = "{'status':'success','code':'0001','info':'create card success','count':'"
						+ result + "'}";
			} else {
				result = Constant.MAX_PRODUCT_QUANLITY - existCodeCount;
				msg = "{'status':'fail','code':'0002','info':'remain card is not enough!!','count':'"
						+ result + "'}";
			}

		} else {
			msg = "{'status':'fail','code':'0003','info':'createTime or quantity is empty!!','count':0}";
		}
		return msg;
	}

	public int getUnAssignPCCount() {
		Object obj = productCardDao.getUnAssignPCCount();
		if (null != obj) {
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}

	public HSSFWorkbook getHSSFWorkbook(ProductCard t) {
		PageObject<ProductCard> po = this.queryObjects(t, null);
		if (null != po && null != po.getDatas()) {
			return ExcelUtil.getInstance()
					.handleObj2Excel(po.getDatas(), ProductCard.class,
							Message.getString("product_card"), 50000);
		}
		return null;
	}

	/**
	 * 判断产品卡是否存在
	 */
	public int isCodeExists(String code) {
		List<ProductCard> list = productCardDao.checkCodeExists(code);
		int codeStatus = ProductCard.PC_EXIT;
		if (null != list && list.size() > 0) {
			codeStatus = list.get(0).getActiveStatus();
		} else {
			codeStatus = ProductCard.PC_NOTEXIT;
		}
		return codeStatus;
	}

	public String codeStatus(String code, String type) {
		List<ProductCard> list = productCardDao.checkCodeExists(code);
		int codeStatus = ProductCard.PC_STATUS_ACTIVE;
		String msg = "";
		if (null != list && list.size() > 0) {
			ProductCard pc = list.get(0);
			codeStatus = pc.getActiveStatus();
			// 1: 会员
			if (type.equals("1")) {
				if (codeStatus == ProductCard.PC_STATUS_ACTIVE) {
					// 激活 不能再注册
					msg = "{\"codeStatus\":\"" + codeStatus + "\"}";
				} else {
					// 可以注册
					msg = "{\"codeStatus\":\"" + ProductCard.PC_STATUS_UNACTIVE
							+ "\"}";
				}
			}
			// 3：体验用户
			if (type.equals("3")) {
				if (codeStatus == ProductCard.PC_STATUS_UNACTIVE) {
					// 未激活，可以注册体验用户
					msg = "{\"codeStatus\":\"" + codeStatus + "\"}";
				} else {
					msg = "{\"codeStatus\":\"" + ProductCard.PC_STATUS_ACTIVE
							+ "\"}";
				}
			}
		} else {
			msg = "{\"codeStatus\":\"" + ProductCard.PC_NOTEXIT + "\"}";
		}
		return msg;
	}

	/**
	 * 判断产品卡号和密码是否匹配
	 */
	public int isCodePwdTrue(String code, String codePwd) {
		List<ProductCard> list = productCardDao.checkCodePwdExists(code,
				codePwd);
		int codePwdStatus = ProductCard.PC_FALSE;
		if (null != list && list.size() > 0) {
			codePwdStatus = ProductCard.PC_TRUE;
		} 
		return codePwdStatus;
	}
	
	public int findPcAreaIdByCode(String code) {
		Object obj = productCardDao.getPcAreaId(code);
		int id = 0;
		if (null != obj) {
			id = Integer.parseInt(obj.toString());
		}
		return id;
	}
	
	public ProductCard findValidCard(String code,String codePwd) {
		return productCardDao.findValidCard(code,codePwd);
	}
	
	public ProductCard queryProductCard(ProductCard card) {
		return productCardDao.queryProductCard(card);
	}

	public PageObject<ProductCardExtend> findUserActiveProductCard(String areaChain,
			Integer status, QueryInfo queryInfo,ProductCardExtend productCard) {
		return productCardDao.findUserActiveProductCard(areaChain, status, queryInfo,productCard);
	}

	public List findCustomerProductCardDate(Integer status) {
		return productCardDao.findCustomerProductCardDate(status);
	}
	
	public HSSFWorkbook getHSSFWorkbookAssign(ProductCard t) {
		List<ProductExcel> lstProduct = productCardDao.queryProductCardExcel(t);
		if (!CollectionUtils.isEmpty(lstProduct)) {
			return ExcelUtil.getInstance()
					.handleObj2Excel(lstProduct, ProductExcel.class,
							Message.getString("product_card"), 50000);
		}
		return null;
	}

	public ProductCard findPcInfoByCard(String code, String codePwd) {
		return productCardDao.findPcInfoByCard(code,codePwd);
	}

	public ProductCard queryFirstUnusedCard(Integer areaId, Integer productId) {
		List<ProductCard> list = productCardDao.queryFirstUnusedCard(areaId, productId);
		if(!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
