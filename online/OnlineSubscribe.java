package com.bskcare.ch.vo.online;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户预约
 */
@Entity
@Table(name = "t_online_subscribe")
public class OnlineSubscribe implements Serializable {

	private static final long serialVersionUID = -70571478472359104L;
	/** 预约信息状态 0：预定中 1：已完成 2：取消 */
	public static final int STATUS_SUBSCRIBING = 0;
	/** 预约信息状态 0：预定中 1：已完成 2：取消 */
	public static final int STATUS_FINSHED = 1;
	/** 预约信息状态 0：预定中 1：已完成 2：取消 */
	public static final int STATUS_CANCEL = 2;

	/** 是否已经评价： 0：未评价 1：已评价 */
	public static final int SATISFACTION_FALSE = 0;
	/** 是否已经评价： 0：未评价 1：已评价 */
	public static final int SATISFACTION_TRUE = 1;

	private Integer id;
	private Integer clientId;
	/** 专家团队成员 */
	private Integer expertId;
	private String mobile;
	/** 症状描述 */
	private String remark;
	/** 0:预约中 1：预约完成 2：取消预约 */
	private int status;
	/** 是否已经评价过 0：未评价 1：已评价 */
	private int hasSatisfaction;
	/** 预约时间 */
	private Date subTime;
	private Date createTime;
	/** 完成时间 */
	private Date finishTime;

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

	public Integer getExpertId() {
		return expertId;
	}

	public void setExpertId(Integer expertId) {
		this.expertId = expertId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSubTime() {
		return subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
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

	public int getHasSatisfaction() {
		return hasSatisfaction;
	}

	public void setHasSatisfaction(int hasSatisfaction) {
		this.hasSatisfaction = hasSatisfaction;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "OnlineSubscribe [clientId=" + clientId + ", createTime="
				+ createTime + ", remark=" + remark + ", hasSatisfaction="
				+ hasSatisfaction + ", id=" + id + ", mobile=" + mobile
				+ ", status=" + status + ", subTime=" + subTime + ", expertId="
				+ expertId + "]";
	}

}
