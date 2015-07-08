package com.bskcare.ch.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_suggestion")
public class Suggestion implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 1：用户发给医生或管理员 */
	public static final int TYPE_SEND_TO_DOC = 1;
	/** 0：医生或管理员发给用户 */
	public static final int TYPE_SEND_TO_CLIENT = 0;
	/** 消息状态：0 未读 1 已读 */
	public static final int STATUS_UNREAD = 0;
	/** 消息状态：0 未读 1 已读 */
	public static final int STATUS_READED = 1;
	/** 对应软件（0: 表示 云健康管家 或者动动更健康) */
	public static final int SOFT_YUN_PLANT = 0;
	/** 对应软件（1: 管血糖 ) */
	public static final int SOFT_GUAN_XUE_TANG = 1;
	/** 对应软件（2:管血压) */
	public static final int SOFT_GUAN_XUE_YA = 2;
	/** 对应软件（-1:所有软件) */
	public static final int SOFT_NO = -1;

	private Integer id;
	/** 意见和反馈 */
	private String suggestion;
	/** 发送者 */
	private String sender;
	/** 接收者 */
	private String receiver;
	/** 状态 **/
	private int status;
	/** 意见发出者类型 **/
	private int type;
	/** 对应软件 **/
	private int soft;
	private Date createTime;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSoft() {
		return soft;
	}

	public void setSoft(int soft) {
		this.soft = soft;
	}

	@Override
	public String toString() {
		return "Suggestion [createTime=" + createTime + ", id=" + id
				+ ", receiver=" + receiver + ", sender=" + sender + ", soft="
				+ soft + ", status=" + status + ", suggestion=" + suggestion
				+ ", type=" + type + "]";
	}

}
