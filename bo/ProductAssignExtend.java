package com.bskcare.ch.bo;

import java.io.Serializable;

import com.bskcare.ch.vo.ProductAssign;

public class ProductAssignExtend extends ProductAssign implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String areaName;
	private String productName;
	private String primary_family;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPrimary_family() {
		return primary_family;
	}
	public void setPrimary_family(String primaryFamily) {
		primary_family = primaryFamily;
	}
}
