package com.bskcare.ch.vo.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户体检报告
 * 
 * @author Administrator
 */

@Entity
@Table(name = "t_client_upload")
public class ClientUpload implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 上传文件状态 : 0-未处理*/
	public static final int STATUS_UNDEAL = 0;
	/** 上传文件状态 : 1-处理*/
	public static final int STATUS_DEALED = 1;
	
	private Integer id;
	private Integer clientId;
	private String title;
	/**
	 * 上传文件类型：1.体检数据 2.问诊数据 3.住院数据 4.其他数据
	 */
	private int type;
	/**
	 * 上传文件路径
	 */
	private String filePath;
	/**
	 * 状态：0.未处理 1.处理
	 */
	private int status;
	private Date uploadTime;
	private Date dealTime;
	private Integer dealUser;
	private String dealRemark;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Integer getDealUser() {
		return dealUser;
	}

	public void setDealUser(Integer dealUser) {
		this.dealUser = dealUser;
	}

	public String getDealRemark() {
		return dealRemark;
	}

	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}

	@Override
	public String toString() {
		return "ClientUpload [clientId=" + clientId + ", dealRemark="
				+ dealRemark + ", dealTime=" + dealTime + ", dealUser="
				+ dealUser + ", filePath=" + filePath + ", id=" + id
				+ ", status=" + status + ", title=" + title + ", type=" + type
				+ ", uploadTime=" + uploadTime + "]";
	}

}
