package com.bskcare.ch.bo;

import java.util.Date;

import org.hibernate.type.StandardBasicTypes;

public class TaskListPage {
	private Integer tlid;
	private String username;
	private Integer gender;
	private Integer age;
	private String mobile;
	private String nickName;
	private Integer bazzaarGrade;
	private Integer healthIndex;
	private String type;
	private Integer status;
	private Date creationTime;
	private Date accomplishTime;
	private Integer areaId;
	private String areaname;
	private Integer principalId;
	private String finishPercent;
	private Integer cid;
	private String tparticulars;
	private String availableProduct;


	public Integer getTlid() {
		return tlid;
	}

	public void setTlid(Integer tlid) {
		this.tlid = tlid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getBazzaarGrade() {
		return bazzaarGrade;
	}

	public void setBazzaarGrade(Integer bazzaarGrade) {
		this.bazzaarGrade = bazzaarGrade;
	}

	public Integer getHealthIndex() {
		return healthIndex;
	}

	public void setHealthIndex(Integer healthIndex) {
		this.healthIndex = healthIndex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getAccomplishTime() {
		return accomplishTime;
	}

	public void setAccomplishTime(Date accomplishTime) {
		this.accomplishTime = accomplishTime;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}

	public String getFinishPercent() {
		return finishPercent;
	}

	public void setFinishPercent(String finishPercent) {
		this.finishPercent = finishPercent;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getTparticulars() {
		return tparticulars;
	}

	public void setTparticulars(String tparticulars) {
		this.tparticulars = tparticulars;
	}

	public String getAvailableProduct() {
		return availableProduct;
	}

	public void setAvailableProduct(String availableProduct) {
		this.availableProduct = availableProduct;
	}

}
