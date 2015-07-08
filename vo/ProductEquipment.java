package com.bskcare.ch.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品设备
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name = "t_product_equipment")
public class ProductEquipment implements Serializable {

	private static final long serialVersionUID = -7113823398917681990L;

	private Integer id;
	private String name;
	private String remark;
	private int status; // 0 正常 1删除

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

	@Override
	public String toString() {
		return "ProductEquipment [id=" + id + ", name=" + name + ", remark="
				+ remark + ", status=" + status + "]";
	}

}
