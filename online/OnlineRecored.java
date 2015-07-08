package com.bskcare.ch.vo.online;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户医生聊天记录
 */
@Entity
@Table(name = "t_online_records")
public class OnlineRecored implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 0: 医生发个用户 1：用户发给医生 2：管理员给管理员发送信息 3:用户给用户发送信息 */
	public static final int TYPE_SEND_TO_CLIENT = 0;
	/** 0: 医生发个用户 1：用户发给医生 2：管理员给管理员发送信息 3:用户给用户发送信息 */
	public static final int TYPE_SEND_TO_DOC = 1;
	/** 0: 医生发个用户 1：用户发给医生 2：管理员给管理员发送信息 3:用户给用户发送信息 */
	public static final int TYPE_DOC_TO_DOC = 2;
	/** 0: 医生发个用户 1：用户发给医生 2：管理员给管理员发送信息 3:用户给用户发送信息 */
	public static final int TYPE_CLIENT_TO_CLIENT = 3;

	/** 消息状态：0 未读 1 已读 */
	public static final int STATUS_UNREAD = 0;
	/** 消息状态：0 未读 1 已读 */
	public static final int STATUS_READED = 1;

	private Integer id;
	/** 消息发送者Id */
	private String sender;
	/** 消息接受者Id */
	private String receiver;
	/** 0：未读 1：已读 */
	private int status;
	/** 0: 医生发个用户 1：用户发给医生 3：用户给用户 */
	private int type;
	/** 消息 */
	private String message;
	private Date createTime;
	/**
	 * 对应软件（0: 表示 云健康管家, 1: 管血糖 2: 管血压 3: 流氓软件 4:160医院 )
	 * 
	 * 相应常量请参考：Constant.SOFT_YUN_PLANT ....
	 */
	private int soft;

	private String consultationId; // 咨询id(专门针对160接口)
	private String attachments; // 附件内容

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSoft() {
		return soft;
	}

	public void setSoft(int soft) {
		this.soft = soft;
	}

	public String getConsultationId() {
		return consultationId;
	}

	public void setConsultationId(String consultationId) {
		this.consultationId = consultationId;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String toString() {
		return "OnlineRecored [id=" + id + ", sender=" + sender + ", receiver="
				+ receiver + ", status=" + status + ", type=" + type
				+ ", message=" + message + ", createTime=" + createTime
				+ ", soft=" + soft + ", consultationId=" + consultationId
				+ ", attachments=" + attachments + "]";
	}

}
