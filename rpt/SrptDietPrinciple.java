package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "srpt_diet_principle")
public class SrptDietPrinciple implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	/** 疾病名称 **/
	private String disease;
	/** 饮食原则最开始部分 **/
	private String principle;
	/** 应该多吃的食物 **/
	private String eatingMore;
	/** 少吃的食物 **/
	private String eatingLess;
	/** 心理原则-应保持的 **/
	private String keeping;
	/** 心理原则-应避免的 **/
	private String avoid;
	/** 生活原则简介 **/
	private String lifePrinciple;
	/** 饮食小处方 **/
	private String smallPrescription;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	public String getEatingMore() {
		return eatingMore;
	}

	public void setEatingMore(String eatingMore) {
		this.eatingMore = eatingMore;
	}

	public String getEatingLess() {
		return eatingLess;
	}

	public void setEatingLess(String eatingLess) {
		this.eatingLess = eatingLess;
	}

	public String getKeeping() {
		return keeping;
	}

	public void setKeeping(String keeping) {
		this.keeping = keeping;
	}

	public String getAvoid() {
		return avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	public String getLifePrinciple() {
		return lifePrinciple;
	}

	public void setLifePrinciple(String lifePrinciple) {
		this.lifePrinciple = lifePrinciple;
	}

	public String getSmallPrescription() {
		return smallPrescription;
	}

	public void setSmallPrescription(String smallPrescription) {
		this.smallPrescription = smallPrescription;
	}

	@Override
	public String toString() {
		return "SrptDietPrinciple [avoid=" + avoid + ", disease=" + disease
				+ ", eatingLess=" + eatingLess + ", eatingMore=" + eatingMore
				+ ", id=" + id + ", keeping=" + keeping + ", lifePrinciple="
				+ lifePrinciple + ", principle=" + principle
				+ ", smallPrescription=" + smallPrescription + "]";
	}

}
