package com.bskcare.ch.bo;

import java.util.Date;

/**
 * 客户列表
 * 
 */
public class ClientQueryObject {

	/**
	 * t1.id,t1.account,t1.`name` as
	 * userName,t1.mobile,t1.areaId,t1.email,t1.levelId
	 * ,t1.healthIndex,t1.createTime," +
	 * " t2.hasGxy,t2.hasTnbI,t2.hasTnbII,t2.hasGxz" + "
	 **/
	private Integer id;
	private String account;
	private String userName;
	private String mobile;
	private Integer areaId;
	private String email;
	private Date createTime;
	private String type;
	private String areaName;
	private Integer age;
	private Integer levelId; // 级别
	private Integer gender; // 性别
	// private Integer healthIndex; //健康指数
	private Integer bazzaarGrade; // 市场评分
	private double finishPercent; // 档案完成率
	// private String hasGxy; //是否患有高血压
	// private String hasTnbI;
	// private String hasTnbII;
	// private String hasGxz;
	private Integer principalId;

	/** 最后一次上传数据时间 **/
	private Date lastFollowTime;
	/** 最后一次上次监测数据的时间 **/
	private Date lastUploadDateTime;
	/**
	 * 服务id
	 */
	private String availableProduct;
	/**
	 * 注册来源
	 */
	private String source;
	// 用户标记
	private Integer flag;

	public String getAvailableProduct() {
		return availableProduct;
	}

	public void setAvailableProduct(String availableProduct) {
		this.availableProduct = availableProduct;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	// public Integer getHealthIndex() {
	// return healthIndex;
	// }
	//
	// public void setHealthIndex(Integer healthIndex) {
	// this.healthIndex = healthIndex;
	// }

	public double getFinishPercent() {
		return finishPercent;
	}

	public void setFinishPercent(double finishPercent) {
		this.finishPercent = finishPercent;
	}

	// public String getHasGxy() {
	// return hasGxy;
	// }
	//
	// public void setHasGxy(String hasGxy) {
	// this.hasGxy = hasGxy;
	// }
	//
	// public String getHasTnbI() {
	// return hasTnbI;
	// }
	//
	// public void setHasTnbI(String hasTnbI) {
	// this.hasTnbI = hasTnbI;
	// }
	//
	// public String getHasTnbII() {
	// return hasTnbII;
	// }
	//
	// public void setHasTnbII(String hasTnbII) {
	// this.hasTnbII = hasTnbII;
	// }
	//
	// public String getHasGxz() {
	// return hasGxz;
	// }
	//
	// public void setHasGxz(String hasGxz) {
	// this.hasGxz = hasGxz;
	// }

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

	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}

	public Date getLastFollowTime() {
		return lastFollowTime;
	}

	public void setLastFollowTime(Date lastFollowTime) {
		this.lastFollowTime = lastFollowTime;
	}

	public Date getLastUploadDateTime() {
		return lastUploadDateTime;
	}

	public void setLastUploadDateTime(Date lastUploadDateTime) {
		this.lastUploadDateTime = lastUploadDateTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getBazzaarGrade() {
		return bazzaarGrade;
	}

	public void setBazzaarGrade(Integer bazzaarGrade) {
		this.bazzaarGrade = bazzaarGrade;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "ClientQueryObject [account=" + account + ", age=" + age
				+ ", areaId=" + areaId + ", areaName=" + areaName
				+ ", createTime=" + createTime + ", email=" + email
				+ ", gender=" + gender + ", id=" + id + ", levelId=" + levelId
				+ ", mobile=" + mobile + ", type=" + type + ", userName="
				+ userName + ", source=" + source + "]";
	}

}
