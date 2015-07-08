package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bskcare.ch.poi.util.ExcelResources;

/**
 * 产品卡
 * 
 * @author houzhiqing
 * 
 */
@Entity
@Table(name="t_product_card")
public class ProductCard implements Serializable {

	private static final long serialVersionUID = -70571478472359104L;

	/**
	 * 产品卡状态 ： 未激活
	 */
	public static final int PC_STATUS_UNACTIVE = 0;
	
	/**
	 * 产品卡状态： 已激活
	 */
	public static final int PC_STATUS_ACTIVE = 1;
	
	/**
	 * 产品卡状态： 作废
	 */
	public static final int PC_STATUS_DISABLED = 2;
	
	/**
	 * 产品卡存在
	 */
	public static final int PC_EXIT = 0;
	
	/**
	 * 产品卡不存在
	 */
	public static final int PC_NOTEXIT = 5;
	
	/**
	 * 产品卡密码不正确
	 */
	public static final int PC_FALSE = 1;
	
	/**
	 * 产品卡密码正确
	 */
	public static final int PC_TRUE = 0;
	
	private Integer id;
	
	/**
	 * 产品卡号
	 */
	private String code;
	
	/**
	 * 产品卡密码
	 */
	private String codePwd;
	
	/**
	 * 主账号绑定的产品信息
	 */
	private Integer mainProductId;
	/**
	 * 亲情账号绑定的产品信息
	 */
	private Integer familyProductId;
	
	/**
	 * 产品卡状态： 是否已经激活 1：激活 ，0：未激活， 2:作废
	 */
	private int activeStatus; 
	
	private Date createTime;
	/**
	 * 激活时间：主卡激活时间
	 */
	private Date activeTime;
	
	/**
	 * 亲情账号允许最大激活次数
	 */
	private int allowActiveCount;
	
	/**
	 * 设备Id
	 */
	private Integer equipmentId;
	
	/**
	 * 区域表
	 */
	private Integer areaId;
	
	/**
	 * 分配Id 对应t_product_assign表
	 */
	private Integer assignId;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ExcelResources(title="产品卡号", order=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelResources(title="产品卡密码", order=2)
	public String getCodePwd() {
		return codePwd;
	}

	public void setCodePwd(String codePwd) {
		this.codePwd = codePwd;
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

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public int getAllowActiveCount() {
		return allowActiveCount;
	}

	public void setAllowActiveCount(int allowActiveCount) {
		this.allowActiveCount = allowActiveCount;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getAssignId() {
		return assignId;
	}

	public void setAssignId(Integer assignId) {
		this.assignId = assignId;
	}

	@Override
	public String toString() {
		return "ProductCard [id=" + id + ", code=" + code + ", codePwd="
				+ codePwd + ", mainProductId=" + mainProductId
				+ ", familyProductId=" + familyProductId + ", activeStatus="
				+ activeStatus + ", createTime=" + createTime + ", activeTime="
				+ activeTime + ", allowActiveCount=" + allowActiveCount
				+ ", equipmentId=" + equipmentId + ", areaId=" + areaId
				+ ", assignId=" + assignId + "]";
	}
	
	
}
