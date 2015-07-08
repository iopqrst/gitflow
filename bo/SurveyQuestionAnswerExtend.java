package com.bskcare.ch.bo;

import com.bskcare.ch.vo.rpt.Answer;

/**
 * 问卷问题扩展累
 */
public class SurveyQuestionAnswerExtend extends Answer {

	private static final long serialVersionUID = 1L;

	private String question;
	private Integer sid;

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	@Override
	public String toString() {
		return "SurveyQuestionExtend [question=" + question + ", sid=" + sid
				+ "]";
	}
}
