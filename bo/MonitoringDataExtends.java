package com.bskcare.ch.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据监测扩展类 
 */
public class MonitoringDataExtends implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String clientName;
	private String areaName;
	private Integer type;
	private Integer gender;
	private Integer age;
	private String mobile;
	private Date testDateTime;
	private Date uploadDateTime;
	private Integer dispose;
	private Integer state;
	private String areaChain;
	private Integer sbp;// 收缩压
	private Integer dbp;// 舒张压
	private Integer bloodOxygen;// 血氧值
	private Integer heartbeat;// 心率
	private double bloodSugarValue;// 血糖值
	private Integer averageHeartRate;// 心电平均心率值
	private Integer dataId;// 心电图编号

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Integer getAverageHeartRate() {
		return averageHeartRate;
	}

	public void setAverageHeartRate(Integer averageHeartRate) {
		this.averageHeartRate = averageHeartRate;
	}

	public double getBloodSugarValue() {
		return bloodSugarValue;
	}

	public void setBloodSugarValue(double bloodSugarValue) {
		this.bloodSugarValue = bloodSugarValue;
	}

	public Integer getBloodOxygen() {
		return bloodOxygen;
	}

	public void setBloodOxygen(Integer bloodOxygen) {
		this.bloodOxygen = bloodOxygen;
	}

	public Integer getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(Integer heartbeat) {
		this.heartbeat = heartbeat;
	}

	public Integer getSbp() {
		return sbp;
	}

	public void setSbp(Integer sbp) {
		this.sbp = sbp;
	}

	public Integer getDbp() {
		return dbp;
	}

	public void setDbp(Integer dbp) {
		this.dbp = dbp;
	}

	public String getAreaChain() {
		return areaChain;
	}

	public void setAreaChain(String areaChain) {
		this.areaChain = areaChain;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public Date getTestDateTime() {
		return testDateTime;
	}

	public void setTestDateTime(Date testDateTime) {
		this.testDateTime = testDateTime;
	}

	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	public Integer getDispose() {
		return dispose;
	}

	public void setDispose(Integer dispose) {
		this.dispose = dispose;
	}

	@Override
	public String toString() {
		return "MonitoringDataExtends [age=" + age + ", areaChain=" + areaChain
				+ ", areaName=" + areaName + ", averageHeartRate="
				+ averageHeartRate + ", bloodOxygen=" + bloodOxygen
				+ ", bloodSugarValue=" + bloodSugarValue + ", clientName="
				+ clientName + ", dataId=" + dataId + ", dbp=" + dbp
				+ ", dispose=" + dispose + ", gender=" + gender
				+ ", heartbeat=" + heartbeat + ", id=" + id + ", mobile="
				+ mobile + ", sbp=" + sbp + ", state=" + state
				+ ", testDateTime=" + testDateTime + ", type=" + type
				+ ", uploadDateTime=" + uploadDateTime + "]";
	}

}
