package com.bskcare.ch.service.impl.rpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.rpt.QuestionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.rpt.QuestionService;
import com.bskcare.ch.vo.rpt.Question;
@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	QuestionDao questionDao;
	
	public List<Question> findAll() {
		return questionDao.listQuestion();
	}

	public Question save(Question question) {
		return questionDao.add(question);
	}

	public PageObject<Question> queryList(Question question,QueryInfo queryInfo) {
		return questionDao.queryList(question,queryInfo);
	}

	public void delete(Integer id) {
		questionDao.delete(id);		
	}

	public Question findById(Integer id) {
		return questionDao.load(id);
	}

	public void updateQuestion(Question question) {
		questionDao.update(question);		
	}

}
