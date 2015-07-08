package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问题
 */
@Entity
@Table(name = "rpt_question")
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer qid;
	private String question;// 问题内容
	private Integer status;// 状态
	private Date createTime;// 创建时间

	@Id
	@GeneratedValue
	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
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
		return "Question [createTime=" + createTime + ", qid=" + qid
				+ ", question=" + question + ", status=" + status + "]";
	}

}
