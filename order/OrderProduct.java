package com.bskcare.ch.vo.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 订单对应的产品
 * 
 */
@Entity
@Table(name = "t_order_product")
public class OrderProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer opId;
	/**订单Id*/
	private Integer omId; 
	/**产品id*/
	private Integer productId;
	/**用户id*/
	private Integer clientId;
	/** 购买产品数量 */
	private int productCount;	
	/** 购买产品单价 */
	private Double productPrice;
	
	private Date createTime;
	/** 失效时间 */
	private Date expiresTime;

	@Id
	@GeneratedValue
	public Integer getOpId() {
		return opId;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public Integer getOmId() {
		return omId;
	}

	public void setOmId(Integer omId) {
		this.omId = omId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	@Override
	public String toString() {
		return "OrderProduct [clientId=" + clientId + ", createTime="
				+ createTime + ", expiresTime=" + expiresTime + ", omId="
				+ omId + ", opId=" + opId + ", productCount=" + productCount
				+ ", productId=" + productId + ", productPrice=" + productPrice
				+ "]";
	}

}
