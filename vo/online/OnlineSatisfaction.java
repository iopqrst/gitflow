package com.bskcare.ch.vo.online;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户满意度记录
 */
@Entity
@Table(name = "t_online_satisfaction")
public class OnlineSatisfaction implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	/** 对应预约记录Id */
	private Integer osId;
	/** 专家Id */
	private Integer expertId;
	/** 满意度分值 */
	private int score;
	/** 建议 */
	private String suggestion;
	private Date createTime;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExpertId() {
		return expertId;
	}

	public void setExpertId(Integer expertId) {
		this.expertId = expertId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOsId() {
		return osId;
	}

	public void setOsId(Integer osId) {
		this.osId = osId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	@Override
	public String toString() {
		return "OnlineSatisfaction [createTime=" + createTime + ", id=" + id
				+ ", osId=" + osId + ", score=" + score + ", suggestion="
				+ suggestion + ", expertId=" + expertId + "]";
	}

}
