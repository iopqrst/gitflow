package com.bskcare.ch.vo.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单表
 * 
 */
@Entity
@Table(name = "t_order_master")
public class OrderMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 订单状态： 1：未付款 */
	public static final Integer STATUS_UNPAYING = 1;
	/** 订单状态： 2：处理中 */
	public static final Integer STATUS_PROCESSING = 2;
	/** 订单状态： 3：完成订单 */
	public static final Integer STATUS_FINISHED = 3;
	/** 订单状态： 4：取消订单 */
	public static final Integer STATUS_CANCLE = 4;
	/** 订单状态： 5：删除订单 */
	public static final Integer STATUS_DELETE = 5;

	/** 订单类型： 1：个体 */
	public static final Integer TYPE_PRESONAL = 1;
	/** 订单类型： 2：团体 */
	public static final Integer TYPE_GROUP = 2;
	/** 订单类型： 3：团体个人 */
	public static final Integer TYPE_GROUP_PRESON = 3;
	/** 订单激活状态： 已激活*/
	public static final int ACTIVED = 0;
	/** 订单激活状态： 未激活*/
	public static final int UN_ACTIVE = 1;
	/** 网络 1*/
	public static final int ORDER_PATH_NET = 1;
	/** 销售渠道  2*/
	public static final int ORDER_PATH_SALES = 2;
	
	/**
	 * 订单id
	 */
	private Integer omId;
	private Integer clientId;
	/** 订货渠道: 1 网络 2 销售渠道 */
	private Integer orderPath;

	/** 订单状态 1：未付款 2：处理中 3：完成订单 4：取消订单 5：删除订单 */
	private Integer status;
	/** 订单备注 */
	private String remark;
	/** 订单总额 */
	private Double salesPrice;
	/** 订单类型： 1：个体 2：团体 3：团体个人 */
	private Integer type;
	/** 对应区域产生的订单 */
	private Integer areaId;
	/** 产品卡 */
	private String productCardCode;
	/** 订单状态 0： 已经激活 1： 未激活 */
	private int active;

	private Date createTime;

	@Id
	@GeneratedValue
	public Integer getOmId() {
		return omId;
	}

	public void setOmId(Integer omId) {
		this.omId = omId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getOrderPath() {
		return orderPath;
	}

	public void setOrderPath(Integer orderPath) {
		this.orderPath = orderPath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getProductCardCode() {
		return productCardCode;
	}

	public void setProductCardCode(String productCardCode) {
		this.productCardCode = productCardCode;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "OrderMaster [active=" + active + ", areaId=" + areaId
				+ ", clientId=" + clientId + ", createTime=" + createTime
				+ ", omId=" + omId + ", orderPath=" + orderPath
				+ ", productCardCode=" + productCardCode + ", remark=" + remark
				+ ", salesPrice=" + salesPrice + ", status=" + status
				+ ", type=" + type + "]";
	}

}
