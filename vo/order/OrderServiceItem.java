package com.bskcare.ch.vo.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 订单产品包含的服务项目
 */
@Entity
@Table(name = "t_order_service")
public class OrderServiceItem implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int STATUS_SUC = 0;
	public static final int STATUS_FAIL = 1;

	private Integer osId;
	/** 订单对应产品明细Id */
	private Integer opId;
	/** 订单Id*/
	private Integer omId;
	private Integer clientId;
	private Integer productId;
	private Integer proItemId;
	/**项目名称: 该字段主要是为了方便查看项目 */
	private String itemName; 
	/** 服务项目数量 */
	private int amount;
	/** 产品服务项目剩余数量 */
	private int surplusAmount;
	/*** 服务到期时间 */
	private Date expiresTime;
	private Date createTime;
	/** 订单项目状态 0： 开放 1：关闭 */
	private int status;

	@Id
	@GeneratedValue
	public Integer getOsId() {
		return osId;
	}

	public void setOsId(Integer osId) {
		this.osId = osId;
	}
	
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getOmId() {
		return omId;
	}

	public void setOmId(Integer omId) {
		this.omId = omId;
	}

	public Integer getOpId() {
		return opId;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProItemId() {
		return proItemId;
	}

	public void setProItemId(Integer proItemId) {
		this.proItemId = proItemId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(int surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OrderServiceItem [amount=" + amount + ", clientId=" + clientId
				+ ", createTime=" + createTime + ", expiresTime=" + expiresTime
				+ ", itemName=" + itemName + ", omId=" + omId + ", opId="
				+ opId + ", osId=" + osId + ", proItemId=" + proItemId
				+ ", productId=" + productId + ", status=" + status
				+ ", surplusAmount=" + surplusAmount + "]";
	}

}
