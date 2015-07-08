package com.bskcare.ch.vo.rpt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问卷问题
 */
@Entity
@Table(name = "rpt_survey_question")
public class SurveyQuestion implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer sid;// 问卷主键编号
	private Integer qid;// 问题主键编号

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

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	@Override
	public String toString() {
		return "SurveryQuestion [id=" + id + ", qid=" + qid + ", sid=" + sid
				+ "]";
	}

}
