package com.bskcare.ch.bo;

import java.util.Date;

import com.bskcare.ch.vo.ProductCard;

/**
 * 产品卡扩展类
 */
public class ProductCardExtend extends ProductCard {

	private static final long serialVersionUID = 1L;

	private String productCode;
	private Date deliverytime;
	private String customerName;
	private String mobile;
	private Integer clientId;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public Date getDeliverytime() {
		return deliverytime;
	}

	public void setDeliverytime(Date deliverytime) {
		this.deliverytime = deliverytime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "ProductCardExtend [clientId=" + clientId + ", customerName="
				+ customerName + ", deliverytime=" + deliverytime + ", mobile="
				+ mobile + ",  productCode=" + productCode
				+ "]";
	}
	
	

}
