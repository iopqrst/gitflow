package com.bskcare.ch.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品套餐关系表
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name = "t_product_relations")
public class ProductVsPackage {

	private Integer id;
	private Integer productId;
	private Integer packageId;
	
	public ProductVsPackage() {
	}

	public ProductVsPackage(Integer productId, Integer packageId) {
		this.productId = productId;
		this.packageId = packageId;
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ProductVsPackage [id=" + id + ", packageId=" + packageId
				+ ", productId=" + productId + "]";
	}
	
}
