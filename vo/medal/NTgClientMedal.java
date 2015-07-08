package com.bskcare.ch.vo.medal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ntg_client_medal")
public class NTgClientMedal implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer clientId;
	private int hardLevel;
	private int hardScore;
	private int validLevel;
	private int validScore;
	private int heartLevel;
	private int heartNumber;
	private Date createTime;
	
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
	public int getHardLevel() {
		return hardLevel;
	}
	public void setHardLevel(int hardLevel) {
		this.hardLevel = hardLevel;
	}
	public int getHardScore() {
		return hardScore;
	}
	public void setHardScore(int hardScore) {
		this.hardScore = hardScore;
	}
	public int getValidLevel() {
		return validLevel;
	}
	public void setValidLevel(int validLevel) {
		this.validLevel = validLevel;
	}
	
	public int getValidScore() {
		return validScore;
	}
	public void setValidScore(int validScore) {
		this.validScore = validScore;
	}
	public int getHeartLevel() {
		return heartLevel;
	}
	public void setHeartLevel(int heartLevel) {
		this.heartLevel = heartLevel;
	}
	public int getHeartNumber() {
		return heartNumber;
	}
	public void setHeartNumber(int heartNumber) {
		this.heartNumber = heartNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
