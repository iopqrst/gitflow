package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问题答案 
 */
@Entity
@Table(name = "rpt_answer")
public class Answer implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer aid;
	private String answer;// 问题答案內容
	private Integer qid;// 问卷编号(对应问题)
	private Integer status;// 状态
	private Date createTime;// 创建时间 
	
	@Id
	@GeneratedValue
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Answer [aid=" + aid + ", answer=" + answer + ", createTime="
				+ createTime + ", qid=" + qid + ", status=" + status + "]";
	}
	
}
