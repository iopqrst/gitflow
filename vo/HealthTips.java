package com.bskcare.ch.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="m_health_tips")
public class HealthTips implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	//类型： 1 血压 2血氧 3空腹血糖 4 餐后2小时血糖  5心电图 6（血糖管家一类的空腹,午餐前，晚餐前、睡前、凌晨血糖） 7 血糖管家一类 早、午、晚餐后
	private int type;
	//患病程度  血压分为  0低血压 1理想血压 2正常数据 3 正常值偏高 4高血压  ...... 
	private int degree;
	//季节 春1夏2秋3冬4 
	private int season;
	//信息类型：1营养2 运动3 生活方式 4 其他
	private int messageType;
	//内容
	private String message;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
