package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 健康报告
 * @author Administrator
 */

@Entity
@Table(name="t_health_report")
public class HealthReport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态为正常
	 */
	public static final int HEALTHREPORTNORMAL = 0;
	
	/**
	 * 状态为异常 删除
	 */
	public static final int HEALTHREPORTNOTNORMAL = 1;
	
	private Integer id;
	private int type;
	private String title;
	private String filePath;
	private Date createTime;
	private int status;
	private Integer userId;
	private Integer clientId;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
