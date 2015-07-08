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
 * 血糖
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "m_blood_sugar")
public class BloodSugar implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 血糖类型 1：空腹血糖 2：餐后2小时血糖
	 */
	public static final int SUGAR_TYPE = 1;
	/**
	 * 血糖类型 1：空腹血糖 2：餐后2小时血糖
	 */
	public static final int SUGAR_TYPE_2H = 2;
	
	/**
	 * 血糖类型：早餐前血糖
	 */
	public static final int SUGAR_ZAOCAN_BEFORE = 10;
	/**
	 * 血糖类型：早餐后血糖
	 */
	public static final int SUGAR_ZAOCAN_AFTER = 11;
	/**
	 * 血糖类型：午餐前血糖
	 */
	public static final int SUGAR_WUCAN_BEFORRE = 12;
	/**
	 * 血糖类型：午餐后血糖
	 */
	public static final int SUGAR_WUCAN_AFTER = 13;
	/**
	 * 血糖类型：晚餐前血糖
	 */
	public static final int SUGAR_WANCAN_BEFORE = 14;
	/**
	 * 血糖类型：晚餐后血糖
	 */
	public static final int SUGAR_WANCAN_AFTER = 15;
	/**
	 * 血糖类型：睡前血糖
	 */
	public static final int SUGAR_SLEEP_BEFORE = 16;
	/**
	 * 血糖类型：凌晨血糖
	 */
	public static final int SUGAR_LINGCHEN = 17;
	
	public static final String SUGAR_BEFORE = "1,10";
	public static final String SUGAR_AFTER = "2,11,13,15";
	public static final String SUGAR_OTHER = "12,14,16,17";
	/**
	 * 血糖理想状态
	 */
	public static final int IDEAL_STAUTS = 1;
	/**
	 * 血糖良好状态
	 */
	public static final int WELL_STAUTS = 2;
	/**
	 * 血糖不良好状态
	 */
	public static final int BADNESS_STAUTS = 3;
	
	/**
	 * 是否上传过血糖数据 1：上传过   2：未上传
	 */
	public static final int UPLOAD_BLOOD_SUGAR = 1;
	public static final int UNUPLOAD_BLOOD_SUGAR = 2;

	// id
	private Integer id;
	private Integer clientId;
	// 上传时间
	private Date uploadDateTime;
	// 测试时间
	private Date testDateTime;
	// 提出建议时间
	private Date suggestTime;
	// 提出建议时间
	private String suggestion;
	// 血糖值
	private double bloodSugarValue;
	// 血糖类型 1：空腹血糖 2：餐后2小时血糖
	private Integer bloodSugarType;
	// 医生用户ID
	private Integer userId;
	// 检测结果
	private String result;
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
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Date getUploadDateTime() {
		return uploadDateTime;
	}
	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}
	public Date getTestDateTime() {
		return testDateTime;
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
	public double getBloodSugarValue() {
		return bloodSugarValue;
	}
	public void setBloodSugarValue(double bloodSugarValue) {
		this.bloodSugarValue = bloodSugarValue;
	}
	public Integer getBloodSugarType() {
		return bloodSugarType;
	}
	public void setBloodSugarType(Integer bloodSugarType) {
		this.bloodSugarType = bloodSugarType;
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
	public void setDispose(Integer dispose) {
		this.dispose = dispose;
	}
	
}
