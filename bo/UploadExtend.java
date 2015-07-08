package com.bskcare.ch.bo;

import java.io.Serializable;
import java.util.Date;

/**
 *  
 */
public class UploadExtend implements Serializable {

	private static final long serialVersionUID = 1L;
	private String clientName;
	private String mobile;
	private Integer age;
	private Integer id;
	private Integer clientId;
	private Integer levelId;
	private Integer bazzaarGrade;
	private String val1;
	private String val2;
	private Integer type;
	private String areaName;
	private Integer areaId;
	// 上传时间
	private Date uploadDateTime;
	// 测试时间
	private Date testDateTime;
	// 提出建议时间
	private Date suggestTime;
	// 提出建议时间
	private String suggestion;
	// 医生用户ID
	private Integer userId;
	// 检测结果
	private String result;
	// 异常状态
	private Integer state;
	// 是否处理 0 未处理 1已处理
	private Integer dispose;
	// 
	private Integer isRead;
	// 血糖的类型
	private Integer stype;
	private Date regTime; //用户注册时间

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Integer getBazzaarGrade() {
		return bazzaarGrade;
	}

	public void setBazzaarGrade(Integer bazzaarGrade) {
		this.bazzaarGrade = bazzaarGrade;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Date getTestDateTime() {
		return testDateTime;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public void setTestDateTime(Date testDateTime) {
		this.testDateTime = testDateTime;
	}

	public Date getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(Date suggestTime) {
		this.suggestTime = suggestTime;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDispose() {
		return dispose;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public void setDispose(Integer dispose) {
		this.dispose = dispose;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public String getVal1() {
		return val1;
	}

	public void setVal1(String val1) {
		this.val1 = val1;
	}

	public String getVal2() {
		return val2;
	}

	public void setVal2(String val2) {
		this.val2 = val2;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Override
	public String toString() {
		return "UploadExtend [age=" + age + ", areaName=" + areaName
				+ ", clientId=" + clientId + ", clientName=" + clientName
				+ ", dispose=" + dispose + ", id=" + id + ", isRead=" + isRead
				+ ", mobile=" + mobile + ", result=" + result + ", state="
				+ state + ", suggestTime=" + suggestTime + ", suggestion="
				+ suggestion + ", testDateTime=" + testDateTime + ", type="
				+ type + ", uploadDateTime=" + uploadDateTime + ", userId="
				+ userId + ", val1=" + val1 + ", val2=" + val2 + "]";
	}

}
