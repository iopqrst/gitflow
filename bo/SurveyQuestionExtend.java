package com.bskcare.ch.bo;

import java.util.Date;

import com.bskcare.ch.vo.rpt.Question;

/**
 * 问卷问题扩展类
 */
public class SurveyQuestionExtend extends Question {

	private static final long serialVersionUID = 1L;

	/**
	 * 问卷名称
	 */
	private String name;
	/**
	 * 创建时间
	 */
	private Date cTime;
	/**
	 * 扩展id
	 */
	private Integer sqid;
	/**
	 * 状态
	 */
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Integer getSqid() {
		return sqid;
	}

	public void setSqid(Integer sqid) {
		this.sqid = sqid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SurveyQuestionExtend [cTime=" + cTime + ", name=" + name
				+ ", sqid=" + sqid + ", status=" + status + "]";
	}

}
