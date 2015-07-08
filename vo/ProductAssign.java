package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品卡分配
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name = "t_product_assign")
public class ProductAssign implements Serializable {

	private static final long serialVersionUID = -1882440674646947542L;

	private Integer id;
	/**
	 * 发货时间
	 */
	private Date deliveryTime;
	/**
	 * 发货区域
	 */
	private Integer areaId;
	/**
	 * 发货总数
	 */
	private int deliveryCount;
	/**
	 * 发货人
	 */
	private String deliveryName;
	
	private Integer equipmentId;
	
	/**
	 * 主账号绑定的产品信息
	 */
	private Integer mainProductId;
	/**
	 * 亲情账号绑定的产品信息
	 */
	private Integer familyProductId;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

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
	
	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getMainProductId() {
		return mainProductId;
	}

	public void setMainProductId(Integer mainProductId) {
		this.mainProductId = mainProductId;
	}

	public Integer getFamilyProductId() {
		return familyProductId;
	}

	public void setFamilyProductId(Integer familyProductId) {
		this.familyProductId = familyProductId;
	}

	@Override
	public String toString() {
		return "ProductAssign [areaId=" + areaId + ", deliveryCount="
				+ deliveryCount + ", deliveryName=" + deliveryName
				+ ", deliveryTime=" + deliveryTime + ", equipmentId="
				+ equipmentId + ", familyProductId=" + familyProductId
				+ ", id=" + id + ", mainProductId=" + mainProductId + "]";
	}
}