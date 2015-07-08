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
 *血氧  张三添加（第二次）
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "m_blood_oxygen")
public class BloodOxygen implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * state、dispose 常量在 MonConstant.java中
	 */
	
	// id
	private Integer id;
	// 客户端用户id
	private Integer clientId;
	// 上传时间
	private Date uploadDateTime;
	// 测试时间
	private Date testDateTime;
	// 提出建议时间
	private Date suggestTime;
	// 提出建议时间
	private String suggestion;
	// 血氧
	private int bloodOxygen;
	// 脉率
	private int heartbeat;

	// 医生用户ID
	private Integer userId;
	// 检测结果
	private String result;
	//异常状态 0 正常 1异常 2严重异常
	private Integer state ;
	//是否处理  0 未处理  1已处理
	private Integer dispose ;
	//http://192.168.1.111:8080/ch/uploadMonitoringData_uploadBloodOxygen.do?msg=[{"bloodOxygen":99,"clientId":null,"dispose":null,"heartbeat":75,"id":null,"result":null,"state":null,"suggestTime":null,"suggestion":null,"testDateTime":null,"uploadDateTime":"2013-05-06 12:12:15","userId":null}]&clientInfo.mobile=13366241553&clientInfo.password=52c69e3a57331081823331c4e69d3f2e

	
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

	@Column(name = "bloodOxygen")
	public int getBloodOxygen() {
		return bloodOxygen;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setBloodOxygen(int bloodOxygen) {
		this.bloodOxygen = bloodOxygen;
	}

	@Column(name = "heartbeat")
	public int getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
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


	@Override
	public String toString() {
		return "BloodOxygen [bloodOxygen=" + bloodOxygen + ", clientId="
				+ clientId + ", dispose=" + dispose + ", heartbeat="
				+ heartbeat + ", id=" + id + ", result="
				+ result + ", state=" + state + ", suggestTime=" + suggestTime
				+ ", suggestion=" + suggestion + ", testDateTime="
				+ testDateTime + ", uploadDateTime=" + uploadDateTime
				+ ", userId=" + userId + "]";
	}
	
}
