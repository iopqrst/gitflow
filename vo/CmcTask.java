package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *中医体质建议
 */
@Entity
@Table(name="t_cmc_task")
public class CmcTask implements Serializable{

	/**
	 * 未处理
	 */
	public static final int CMC_TASK_NOT_DEAL = 0;
	/**
	 * 已处理
	 */
	public static final int CMC_TASK_DEAL = 1;
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer clientId;
	private Integer ceId;
	private Integer status;
	private Date createTime;
	private Integer userId;
	private String advice;
	private Date adviceTime;
	
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
	public Integer getCeId() {
		return ceId;
	}
	public void setCeId(Integer ceId) {
		this.ceId = ceId;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public Date getAdviceTime() {
		return adviceTime;
	}
	public void setAdviceTime(Date adviceTime) {
		this.adviceTime = adviceTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}	
