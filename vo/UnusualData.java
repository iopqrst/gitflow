package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 报警异常数据
 */
@Entity
@Table(name = "m_unusual_data")
public class UnusualData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖*/
	public static final int BLOODPRESSURE = 1;
	/**1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖*/
	public static final int BLOODOXYGEN = 2;
	/**1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖*/
	public static final int ELECTROCARDIO = 3;
	/**1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖*/
	public static final int BLOODSUGAR = 4;
	/**1：血压 2：血氧 3：心电 4：空腹血糖 5：餐后2小时血糖*/
	public static final int BLOODSUGAR2H = 5;
	/**6：体温*/
	public static final int TEMPERATURE = 6;
	
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
	
	/**未处理*/
	public static final int UNHANDLE = 0;
	/**已处理*/
	public static final int HANDLED = 1;
	
	private Integer id;
	/**
	 * 客户编号
	 */
	private Integer clientId;
	/**
	 * 异常数据编号
	 */
	private Integer dataId;
	/**
	 * 异常数据类型:1为血压,2为血氧,3为心电,4为餐前血糖l,5为餐后血糖
	 */
	private Integer type;
	/**
	 * 异常数数据具体值(val1,1val2):type为血压，val1为收缩压(sbp),val2为舒张压（dbp）,
	 * type为血氧，val1为血氧（bloodOxygen）,val2为心率（heartbeat）, type为心电，
	 * val1为图片地址（attachmentUrl），val2为心率平均值（averageHeartRate）,
	 * type为餐前血糖，val1为血糖值（bloodSugarValue）,val2为null,
	 * type为餐后血糖，val1为血糖值（bloodSugarValue）,val2为null
	 */
	private String val1;
	private String val2;
	/**
	 * 测试时间
	 */
	private Date testTime;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	/**
	 * 状态,0为未处理,1为已处理
	 */
	private Integer status;

	@Id
	@GeneratedValue
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

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UnusualData() {
		super();
	}

	public UnusualData(Integer clientId, Integer dataId, Integer type,
			String val1, String val2, Date testTime, Date uploadTime,
			Integer status) {
		super();
		this.clientId = clientId;
		this.dataId = dataId;
		this.type = type;
		this.val1 = val1;
		this.val2 = val2;
		this.testTime = testTime;
		this.uploadTime = uploadTime;
		this.status = status;
	}

	@Override
	public String toString() {
		return "AbnormalData [clientId=" + clientId + ", dataId=" + dataId
				+ ", id=" + id + ", status=" + status + ", testTime="
				+ testTime + ", type=" + type + ", uploadTime=" + uploadTime
				+ ", val1=" + val1 + ", val2=" + val2 + "]";
	}

}
