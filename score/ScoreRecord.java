package com.bskcare.ch.vo.score;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 积分记录
 */
@Entity
@Table(name = "s_score_record")
public class ScoreRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int CATEGORY_SCORE = 2;
	
	public static final int MODULE_START_APP = 1;
	public static final int MODULE_UPLOAD_BLOOD_SUGAR = 2;
	public static final int MODULE_ALIPAY = 3;
	public static final int MODULE_DIET = 4;
	public static final int MODULE_SPORT = 5;
	public static final int MODULE_SLEEP = 6;
	

	private Integer id;
	private Integer clientId;
	private Integer moduleId;
	/**
	 * 种类：1、金币 2、积分
	 */
	private int category;
	/**
	 * type(1：收入 2：支出)
	 */
	private int type;
	/** 具体分值 */
	private int score;
	private Date createTime;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ScoreRecord [category=" + category + ", clientId=" + clientId
				+ ", createTime=" + createTime + ", id=" + id + ", moduleId="
				+ moduleId + ", score=" + score + ", type=" + type + "]";
	}

}
