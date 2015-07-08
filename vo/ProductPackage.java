package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 套餐
 * @author houzhiqing
 *
 */
@Entity
@Table(name = "t_product_package")
public class ProductPackage implements Serializable {
	
	private static final long serialVersionUID = 5238591159321480480L;
	
	private Integer id;
	private String name;
	
	/**
	 * 套餐单价
	 */
	private Double price;
	
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
	 * 套餐描述
	 */
	private String remark;
	
	/**
	 * 状态
	 */
	private int status = 0;
	
	/**
	 * 开始时间（用户查询,非数据库字段）
	 */
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

	@Override
	public String toString() {
		return "ProductPackage [id=" + id + ", name=" + name + ", price="
				+ price + ", creator=" + creator + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + ", remark=" + remark
				+ ", status=" + status + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + "]";
	}
	
	
}
