package com.bskcare.ch.bo;

import java.util.Date;

public class ProductCardObject {

	/**
	 * 设备编号
	 */
	private String code;
	
	/**
	 * 分配(记录)id
	 */
	private Integer assignId;
	/**
	 * 设备名称
	 */
	private String equipment;
	/**
	 * 主产品名称
	 */
	private String mainProductName;
	/**
	 * 亲情产品名称
	 */
	private String familyProductName;
	/**
	 * 最大允许创建亲情账号数量
	 */
	private int allowCreateCount;
	/**
	 * 发货数量
	 */
	private int deliveryCount;
	/**
	 * 发货人
	 */
	private String deliveryName;
	/**
	 * 发货地区
	 */
	private String areaName;
	/**
	 * 发货时间
	 */
	private Date deliveryTime;

	public int getDeliveryCount() {
		return deliveryCount;
	}

	public void setDeliveryCount(int deliveryCount) {
		this.deliveryCount = deliveryCount;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getMainProductName() {
		return mainProductName;
	}

	public void setMainProductName(String mainProductName) {
		this.mainProductName = mainProductName;
	}

	public String getFamilyProductName() {
		return familyProductName;
	}

	public void setFamilyProductName(String familyProductName) {
		this.familyProductName = familyProductName;
	}

	public int getAllowCreateCount() {
		return allowCreateCount;
	}

	public void setAllowCreateCount(int allowCreateCount) {
		this.allowCreateCount = allowCreateCount;
	}

	public Integer getAssignId() {
		return assignId;
	}

	public void setAssignId(Integer assignId) {
		this.assignId = assignId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
