package com.bskcare.ch.vo;

import java.util.Date;

/**
 * 异常数据扩展类
 */
public class UnusualDataExtends extends UnusualData {

	private static final long serialVersionUID = 1L;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private Integer gender;
	
	private String mobile;
	
	/**
	 * 客户编号
	 */
	private Integer clientId;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 服务级别
	 */
	private String levelId;
	/**
	 * 所属区域名称
	 */
	private String areaName;

	private Integer areaId;
	
	private Date regTime; //注册时间
	
	private Integer bazzaarGrade;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}


	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Integer getBazzaarGrade() {
		return bazzaarGrade;
	}

	public void setBazzaarGrade(Integer bazzaarGrade) {
		this.bazzaarGrade = bazzaarGrade;
	}

	public String toString() {
		return "UnusualDataExtends [age=" + age + ", areaName=" + areaName
				+ ", clientId=" + clientId + ", gender=" + gender
				+ ", levelId=" + levelId + ", name=" + name + "]";
	}
}
