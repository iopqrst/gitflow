package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问卷答案
 */
@Entity
@Table(name = "rpt_survey_answer")
public class SurveyAnswer implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer sid;// 问卷主键编号
	private Integer aid;// 问题答案主键编号
	private Integer qid;// 问题主键编号
	private Integer clientId;// 客户主键编号
	private Date submitTime;// 问卷提交时间
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	@Override
	public String toString() {
		return "SurveryAnswer [aid=" + aid + ", clientId=" + clientId + ", id="
				+ id + ", qid=" + qid + ", sid=" + sid + ", submitTime="
				+ submitTime + "]";
	}
	
}
