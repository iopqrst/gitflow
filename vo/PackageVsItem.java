package com.bskcare.ch.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品套餐项目中间表
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name = "t_product_package_item")
public class PackageVsItem {

	private Integer id;
	private Integer packageId;
	private Integer itemId;
	
	/**
	 * 套餐项目对应数量
	 */
	private int quantity;
	
	public PackageVsItem() {}
	
	public PackageVsItem(Integer packageId, Integer itemId, int quantity) {
		this.packageId = packageId;
		this.itemId = itemId;
		this.quantity = quantity;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "PackageVsItem [id=" + id + ", itemId=" + itemId
				+ ", packageId=" + packageId + ", quantity=" + quantity + "]";
	}
	
}
