package com.bskcare.ch.vo.rpt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问题答案提示
 */
@Entity
@Table(name = "rpt_answer_tip")
public class AnswerTip implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer tid;
	private String content;// 答案提示内容
	private Integer aid;// 答案主键编号
	private Integer status;// 状态
	private Date createTime;// 创建时间

	@Id
	@GeneratedValue
	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
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
		return "AnswerTip [aid=" + aid + ", content=" + content
				+ ", createTime=" + createTime + ", status=" + status
				+ ", tid=" + tid + "]";
	}

}
