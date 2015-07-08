package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.AnswerDao;
import com.bskcare.ch.service.rpt.AnswerService;
import com.bskcare.ch.vo.rpt.Answer;

@Service
public class AnswerServiceImpl implements AnswerService{

	@Autowired
	AnswerDao answerDao;
	
	public List<Answer> findAll() {
		return answerDao.listAnswer();
	}

	public Answer save(Answer answer) {
		return answerDao.add(answer);
	}
}
