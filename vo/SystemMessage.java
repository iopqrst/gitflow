package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 发送站内信息
 */
@Entity
@Table(name = "t_system_message")
public class SystemMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 信息类型 0: 站内信息 
	 */
	public static final int MESSAGE_TYPE_IN = 0;
	/**
	 * 信息类型 1: 公告
	 */
	public static final int MESSAGE_TYPE_CONSULT = 1;

	/**
	 * 信息状态 0: 未发送
	 */
	public static final int MESSAGE_STATUS_NO_SEND = 0;
	/**
	 * 信息状态 1: 发送中
	 */
	public static final int MESSAGE_STATUS_SENDING = 1;
	/**
	 * 信息状态 2:发送完成 
	 */
	public static final int MESSAGE_STATUS_FINISH = 2;
	/**
	 * 信息状态 3: 预约发送
	 */
	public static final int MESSAGE_MAKE_APPOINTMENT = 3;
	/**
	 * 信息状态 4：删除状态
	 */
	public static final int MESSAGE_DELETE = 4;

	private Integer smId;
	private String title;// 信息标题
	private String content;// 信息内容
	private Date createTime;// 创建时间
	private Date planTime;// 计划发送时间
	private Date finishTime;// 发送完成时间
	private Integer type;// 信息类型 0: 站内信息 1：咨询信息 等等…
	private Integer status;// 信息状态 0: 未发送 1：发送中 2：发送完成 3: 预约发送 4：删除状态
	private Integer userId;// 创建人Id

	@Id
	@GeneratedValue
	public Integer getSmId() {
		return smId;
	}

	public void setSmId(Integer smId) {
		this.smId = smId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
		return "SystemMessage [content=" + content + ", createTime="
				+ createTime + ", finishTime=" + finishTime + ", smId=" + smId
				+ ", planTime=" + planTime + ", status=" + status + ", title="
				+ title + ", type=" + type + ", userId=" + userId + "]";
	}

}
