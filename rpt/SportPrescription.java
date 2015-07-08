package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rpt_sport_prescription")
public class SportPrescription implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 大处方 */
	public static final int TYPE_BIG_PRESCRIPTION = 0;
	/** 小处方 */
	public static final int TYPE_SMALL_PRESCRIPTION = 1;

	private Integer id;

	private String name;

	private String remark;

	private Integer type;

	private String image;
	/**
	 * 热卡/min
	 */
	private Double calories;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getTypeBigPrescription() {
		return TYPE_BIG_PRESCRIPTION;
	}

	public static int getTypeSmallPrescription() {
		return TYPE_SMALL_PRESCRIPTION;
	}

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "SportPrescription [id=" + id + ", image=" + image + ", name="
				+ name + ", remark=" + remark + ", type=" + type + "]";
	}

}
