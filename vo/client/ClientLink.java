package com.bskcare.ch.vo.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户联系人
 * @author Administrator
 */
@Entity
@Table(name="t_client_link")
public class ClientLink implements Serializable{
	
	private Integer id;
	private Integer clientId;
	/**
	 * 关系：1.子女 2.夫妻  3.父母  4.兄弟 5.朋友    6.亲戚   7.同事  8.其他  9.亲情号码 
	 */
	private int relation;
	private String name;
	private String telphone;
	private String mobile;
	private String email;
	private String address;
	private String postCode;
	private String remark;
	private int status;
	private int isSendMessage;
	
	/**
	 * 启用
	 */
	public static final int STATUS_NORMAL=0;
	/**
	 * 不启用
	 */
	public static final int STATUS_NOTMORMAL=1;
	
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
	public int getRelation() {
		return relation;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsSendMessage() {
		return isSendMessage;
	}
	public void setIsSendMessage(int isSendMessage) {
		this.isSendMessage = isSendMessage;
	}
	
	
}
