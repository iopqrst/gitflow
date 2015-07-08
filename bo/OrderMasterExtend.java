package com.bskcare.ch.bo;

import com.bskcare.ch.vo.order.OrderMaster;

/**
 * 订单列表扩展类
 */
public class OrderMasterExtend extends OrderMaster {

	private static final long serialVersionUID = 1L;

	/** 客户卡编号 */
	private String clientCode;
	/** 客户姓名 */
	private String clientName;
	private String mobile;
//	/** 区域名 */
//	private String areaName;
	/**区域id*/
	private Integer areaId;
	private Integer productId; // 产品Id
//	private String productName; // 产品名称
	private Integer cid; // 客户id
	private Integer omId;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	public String getAreaName() {
//		return areaName;
//	}
//
//	public void setAreaName(String areaName) {
//		this.areaName = areaName;
//	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

//	public String getProductName() {
//		return productName;
//	}
//
//	public void setProductName(String productName) {
//		this.productName = productName;
//	}

	public Integer getOmId() {
		return omId;
	}

	public void setOmId(Integer omId) {
		this.omId = omId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "OrderMasterExtend [cid=" + cid
				+ ", clientCode=" + clientCode + ", clientName=" + clientName
				+ ", mobile=" + mobile + ", productId=" + productId
				+ "]," + super.toString();
	}


}
