package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_client_sms")
public class ShortMessage implements java.io.Serializable {

	/** 已发送 */
	public static final Integer SEND_RESULT_SUC = 0;
	/**未发送**/
	public static final Integer SEND_RESULT_NOT = -1;
	/**发送失败**/
	public static final Integer SEND_RESULT_FAIL = 1;
	/**取消发送**/
	public static final Integer SEND_RESULT_CANCLE = 2;

	private static final long serialVersionUID = 1L;
	private Integer id; // id
	private String type; // 短信类型
	private String content; // 短信内容
	private Date sendTime; // 发送时间
	private Integer clientId; // 用户id
	private Integer result; // 状态 -1 未发送 0：已发送1：发送失败 2：取消发送
	private Date fixedTime; // 定时时间
	private BigInteger state; // 返回状态
	private String mobile; // 手机号
	
	private List<ShortMessage> lstMessageEntity;

	@Transient
	public List<ShortMessage> getLstMessageEntity() {
		return lstMessageEntity;
	}

	public void setLstMessageEntity(List<ShortMessage> lstMessageEntity) {
		this.lstMessageEntity = lstMessageEntity;
	}

	public ShortMessage() {}
	
	public ShortMessage(String type, String content, Integer clientId, String mobile) {
		this.type = type;
		this.content = content;
		this.clientId = clientId;
		this.mobile = mobile;
	}
	
	public ShortMessage(String type, String content, Integer clientId, String mobile, Date fixedTime) {
		this.type = type;
		this.content = content;
		this.clientId = clientId;
		this.mobile = mobile;
		this.fixedTime = fixedTime;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public BigInteger getState() {
		return state;
	}

	public void setState(BigInteger state) {
		this.state = state;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Date getFixedTime() {
		return fixedTime;
	}

	public void setFixedTime(Date fixedTime) {
		this.fixedTime = fixedTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
