package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 心电图
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "m_electrocardiogram")
public class Electrocardiogram implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * state、dispose常量在 MonConstant.java中
	 */
	
	//id
	private Integer id;
	private Integer clientId;
	// 上传时间
	private Date uploadDateTime;
	// 测试时间
	private Date testDateTime;
	// 提出建议时间
	private Date suggestTime;
	// 建议
	private String suggestion;
	// 医生用户ID
	private Integer userId;
	// 检测结果
	private String result;
	//心电图附件 URL
	private String attachmentUrl;
	//平均心率HR
	private Integer averageHeartRate;
	//测量模式
	private String measurementPattern;
	//滤波模式
	private String filteringPattern;
	//结果状态
	private Integer resultStatus;
	//异常状态 0 正常 1异常 2严重异常
	private Integer state ;
	//是否处理  0 未处理  1已处理
	private Integer dispose ; 	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "clientId")
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "uploadDateTime")
	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	@Column(name = "testDateTime")
	public Date getTestDateTime() {
		return testDateTime;
	}

	public void setTestDateTime(Date testDateTime) {
		this.testDateTime = testDateTime;
	}

	@Column(name = "suggestTime")
	public Date getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(Date suggestTime) {
		this.suggestTime = suggestTime;
	}

	@Column(name = "suggestion")
	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "attachmentUrl")
	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public Integer getAverageHeartRate() {
		return averageHeartRate;
	}

	public void setAverageHeartRate(Integer averageHeartRate) {
		this.averageHeartRate = averageHeartRate;
	}

	public String getMeasurementPattern() {
		return measurementPattern;
	}

	public void setMeasurementPattern(String measurementPattern) {
		this.measurementPattern = measurementPattern;
	}

	public String getFilteringPattern() {
		return filteringPattern;
	}

	public void setFilteringPattern(String filteringPattern) {
		this.filteringPattern = filteringPattern;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
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

	public void setDispose(Integer dispose) {
		this.dispose = dispose;
	}
	
}
