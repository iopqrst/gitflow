package com.bskcare.ch.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 检测数据里面的 专家建议
 * @author Administrator
 *
 */
@Entity
@Table(name = "m_expertadvise")
public class ExpertAdvise implements java.io.Serializable {

	private static final long serialVersionUID = -8918366947772467290L;
	
	private Integer id; //id
	private Integer clientId; //客户ID
	private Integer userId;//医生ID
	private Date dateTime; //时间
	private String result; //医生建议
	private Integer typeId; //类型  血压：0  血氧：1 血糖 ：2 餐后2小时血糖：3  心电图：4
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
	
	
}
