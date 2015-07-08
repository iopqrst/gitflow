package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "m_blood_sugar_target")
public class BloodSugarTarget implements Serializable {

	private Integer id;
	private Integer clientId;
	private double fbgMax; //空腹血糖最大
	private double fbgMin;	//  最小
	private double pbgMax;	//餐后血糖
	private double pbgMin;
	private Date createTime;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}


	public double getFbgMax() {
		return fbgMax;
	}

	public void setFbgMax(double fbgMax) {
		this.fbgMax = fbgMax;
	}

	public double getFbgMin() {
		return fbgMin;
	}

	public void setFbgMin(double fbgMin) {
		this.fbgMin = fbgMin;
	}

	public double getPbgMax() {
		return pbgMax;
	}

	public void setPbgMax(double pbgMax) {
		this.pbgMax = pbgMax;
	}

	public double getPbgMin() {
		return pbgMin;
	}

	public void setPbgMin(double pbgMin) {
		this.pbgMin = pbgMin;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
