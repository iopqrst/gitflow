package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户体检信息
 * 
 * @author Administrator
 */
@Entity
@Table(name = "t_client_physical")
public class ClientPhysical implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer clientId;
	private String medicalCenter;
	private String medicalNo;
	private Date physicalTime;
	private String summary;
	private String suggest;
	private String filePath;
	private String remark;
	private Date createTime;
	private int status;

	/**
	 * 状态为正常
	 */
	public static final int STATUS_NORMAL = 0;
	/**
	 * 状态为异常
	 */
	public static final int STATUS_NOTNORMAL = 1;

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

	public String getMedicalCenter() {
		return medicalCenter;
	}

	public void setMedicalCenter(String medicalCenter) {
		this.medicalCenter = medicalCenter;
	}

	public Date getPhysicalTime() {
		return physicalTime;
	}

	public void setPhysicalTime(Date physicalTime) {
		this.physicalTime = physicalTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMedicalNo() {
		return medicalNo;
	}

	public void setMedicalNo(String medicalNo) {
		this.medicalNo = medicalNo;
	}

	@Override
	public String toString() {
		return "ClientPhysical [clientId=" + clientId + ", createTime="
				+ createTime + ", filePath=" + filePath + ", id=" + id
				+ ", medicalCenter=" + medicalCenter + ", medicalNo="
				+ medicalNo + ", physicalTime=" + physicalTime + ", remark="
				+ remark + ", status=" + status + ", suggest=" + suggest
				+ ", summary=" + summary + "]";
	}

}
