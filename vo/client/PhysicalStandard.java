package com.bskcare.ch.vo.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_physical_standard")
public class PhysicalStandard implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer pdId;
	private String name;
	private int gender;
	private double up;
	private double down;
	private String bgcolor;
	private String desc;
	private String remark;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public double getUp() {
		return up;
	}

	public void setUp(double up) {
		this.up = up;
	}

	public double getDown() {
		return down;
	}

	public void setDown(double down) {
		this.down = down;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "PhysicalStandard [bgcolor=" + bgcolor + ", desc=" + desc
				+ ", down=" + down + ", gender=" + gender + ", id=" + id
				+ ", name=" + name + ", pdId=" + pdId + ", remark=" + remark
				+ ", up=" + up + "]";
	}

}
