package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 站内信息记录
 */
@Entity
@Table(name = "t_message_record")
public class MessageRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 消息状态 0: 未读 
	 */
	public static final int MESSAGE_NO_READ = 0;
	/**
	 * 消息状态 1: 已读
	 */
	public static final int MESSAGE_READ = 1;
	/**
	 * 消息状态 2: 删除
	 */
	public static final int MESSAGE_DELETE = 2;
	
	private Integer mrId;
	private Integer smId;// 站内信息
	private Integer clientId;// 用户id
	private Date createTime;// 创建时间
	private Integer status;// 消息状态 0： 未读 1： 已读 2： 删除

	@Id
	@GeneratedValue
	public Integer getMrId() {
		return mrId;
	}
	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}
	public Integer getSmId() {
		return smId;
	}
	public void setSmId(Integer smId) {
		this.smId = smId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MessageRecord [clientId=" + clientId + ", createTime="
				+ createTime + ", mrId=" + mrId + ", smId=" + smId
				+ ", status=" + status + "]";
	}
}
