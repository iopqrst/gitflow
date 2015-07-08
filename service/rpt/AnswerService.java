package com.bskcare.ch.service.rpt;

import java.util.List;

import com.bskcare.ch.vo.rpt.Answer;

public interface AnswerService {

	public List<Answer> findAll();
	
	public Answer save(Answer answer);
}
