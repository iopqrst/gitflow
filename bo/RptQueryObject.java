package com.bskcare.ch.bo;

import java.util.Date;

public class RptQueryObject {
	private Integer id;
	private Integer rptId;
	private String userName;
	private String mobile;
	private Integer areaId;
	private Date createTime;// 健康报告创建时间
	private String type;
	private String areaName;
	private Integer age;
	private Integer gender; // 性别
	private int docStatus; // 医生审核状态
	private int hmStatus;
	private int dietStatus; // 饮食审核状态
	private int sportStatus; // 运动审核状态
	private int status;
	private int srptType;
	private int reading;  //是否解读

	public int getHmStatus() {
		return hmStatus;
	}

	public void setHmStatus(int hmStatus) {
		this.hmStatus = hmStatus;
	}

	public int getReading() {
		return reading;
	}

	public void setReading(int reading) {
		this.reading = reading;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(int docStatus) {
		this.docStatus = docStatus;
	}

	public int getDietStatus() {
		return dietStatus;
	}

	public void setDietStatus(int dietStatus) {
		this.dietStatus = dietStatus;
	}

	public int getSportStatus() {
		return sportStatus;
	}

	public void setSportStatus(int sportStatus) {
		this.sportStatus = sportStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRptId() {
		return rptId;
	}

	public void setRptId(Integer rptId) {
		this.rptId = rptId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public int getSrptType() {
		return srptType;
	}

	public void setSrptType(int srptType) {
		this.srptType = srptType;
	}

}
