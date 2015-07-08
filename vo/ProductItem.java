package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 产品项目
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name = "t_product_item")
public class ProductItem implements Serializable {

	private static final long serialVersionUID = -70571478472359104L;

	/**
	 * 项目类型 : 服务项目
	 */
	public static final int TYPE_OF_SERVICE = 1;
	/**
	 * 项目类型 : 设备项目
	 */
	public static final int TYPE_OF_EQUIPMENT = 2;
	/**
	 * 项目类型 : 干预项目
	 */
	public static final int TYPE_OF_INTERVENE = 3;

	private Integer id;
	private String name;

	/**
	 * 项目单价
	 */
	private Double price;

	/**
	 * 项目类型（1:服务项目 2： 设备项目 3：干预项目）
	 */
	private int type;

	/**
	 * 创建者
	 */
	private Integer creator;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后一次修改时间
	 */
	private Date modifyTime;

	/**
	 * 项目描述
	 */
	private String remark;

	/**
	 * 状态
	 */
	private int status = 0;

	private Date beginTime;
	private Date endTime;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Transient
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Transient
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String toString() {
		return "ProductItem [id=" + id + ", name=" + name + ", price=" + price
				+ ", type=" + type + ", creator=" + creator + ", createTime="
				+ createTime + ", endTime=" + endTime + ", remark=" + remark
				+ ", status=" + status + "]";
	}
}
