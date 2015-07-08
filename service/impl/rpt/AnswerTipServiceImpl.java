package com.bskcare.ch.service.impl.rpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.AnswerTipDao;
import com.bskcare.ch.service.rpt.AnswerTipService;
import com.bskcare.ch.vo.rpt.AnswerTip;
@Service
public class AnswerTipServiceImpl implements AnswerTipService{
	
	@Autowired
	AnswerTipDao answerTipDao;

	public AnswerTip save(AnswerTip answerTip) {
		return answerTipDao.add(answerTip);
	}

}
