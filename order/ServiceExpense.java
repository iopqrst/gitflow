package com.bskcare.ch.vo.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 订单服务项目消费明细
 */
@Entity
@Table(name = "t_service_expense")
public class ServiceExpense implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int STATUS_SUC = 0;
	public static final int STATUS_FAIL = 1;
	
	/**
	 * 不记录消费明细
	 */
	public static final int NO_RECORD = 0;
	/**
	 * 记录消费明细
	 */
	public static final int NEED_RECORD = 1;
	
	/** 主键Id */
	private Integer seId;
	private Integer clientId;
	/** OrderServiceItem 订单服务项目Id */
	private Integer osId;
	private int expenseCount;
	private Date expenseTime;
	private int status;
	private Integer userId;

	@Id
	@GeneratedValue
	public Integer getSeId() {
		return seId;
	}

	public void setSeId(Integer seId) {
		this.seId = seId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getOsId() {
		return osId;
	}

	public void setOsId(Integer osId) {
		this.osId = osId;
	}

	public int getExpenseCount() {
		return expenseCount;
	}

	public void setExpenseCount(int expenseCount) {
		this.expenseCount = expenseCount;
	}

	public Date getExpenseTime() {
		return expenseTime;
	}

	public void setExpenseTime(Date expenseTime) {
		this.expenseTime = expenseTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ServiceExpense [clientId=" + clientId + ",userId"+userId+", expenseCount="
				+ expenseCount + ", expenseTime=" + expenseTime + ", osId="
				+ osId + ", seId=" + seId + ", status=" + status + "]";
	}
	
}
