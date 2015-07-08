package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 体质调养方案
 * @author Administrator
 */

@Entity
@Table(name="t_cmc_aftercare")
public class CmcAftercare implements Serializable{
	
	private int id;
	private String name;
	private Date creatTime;
	private int creator;
	
	/**
	 * 引言
	 */
	private String introduction;
	/**
	 * 原则
	 */
	private String principle;
	/**
	 * 环境起居
	 */
	
	private String livingEnvironment;
	/**
	 * 形体运动
	 */
	private String physicalExercise;
	/**
	 *精神调适
	 */
	private String mentalAdjustment;
	/**
	 * 膳食调理
	 */
	private String dietaryConditioning;
	/**
	 * 药膳调养
	 */
	private String dietAftercare;
	/**
	 * 药物调养
	 */
	private String drugAftercare;
	/**
	 * 其他调养
	 */
	private String otherAftercare;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 状态0启用   1禁用
	 */
	private int status;
	
	/**
	 *状态为启用
	 */
	public static final int AFTERCARE_NORMAL=0;
	/**
	 *状态为启用
	 */
	public static final int AFTERCARE_NOTNORMAL=1;
	
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getPrinciple() {
		return principle;
	}
	public void setPrinciple(String principle) {
		this.principle = principle;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getLivingEnvironment() {
		return livingEnvironment;
	}
	public void setLivingEnvironment(String livingEnvironment) {
		this.livingEnvironment = livingEnvironment;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getPhysicalExercise() {
		return physicalExercise;
	}
	public void setPhysicalExercise(String physicalExercise) {
		this.physicalExercise = physicalExercise;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getMentalAdjustment() {
		return mentalAdjustment;
	}
	public void setMentalAdjustment(String mentalAdjustment) {
		this.mentalAdjustment = mentalAdjustment;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getDietaryConditioning() {
		return dietaryConditioning;
	}
	public void setDietaryConditioning(String dietaryConditioning) {
		this.dietaryConditioning = dietaryConditioning;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getDietAftercare() {
		return dietAftercare;
	}
	public void setDietAftercare(String dietAftercare) {
		this.dietAftercare = dietAftercare;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getDrugAftercare() {
		return drugAftercare;
	}
	public void setDrugAftercare(String drugAftercare) {
		this.drugAftercare = drugAftercare;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
	public String getOtherAftercare() {
		return otherAftercare;
	}
	public void setOtherAftercare(String otherAftercare) {
		this.otherAftercare = otherAftercare;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY) 
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
}
