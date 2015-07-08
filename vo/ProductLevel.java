package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品等级
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name = "t_product_level")
public class ProductLevel implements Serializable {

	private static final long serialVersionUID = -70571478472359104L;

	private Integer id;
	private String name;
	private Date createTime;
	/**
	 * 产品状态： 0 正常， 1 删除
	 */
	private int status;

	/**
	 * 创建人
	 */
	private Integer creator;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/** 产品等级排序*/
	private Integer priority;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "ProductLevel [createTime=" + createTime + ", creator="
				+ creator + ", id=" + id + ", name=" + name + ", priority="
				+ priority + ", remark=" + remark + ", status=" + status + "]";
	}

}
