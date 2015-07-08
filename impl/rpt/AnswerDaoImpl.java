package com.bskcare.ch.dao.impl.rpt;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.AnswerDao;
import com.bskcare.ch.vo.rpt.Answer;
@Repository
public class AnswerDaoImpl extends BaseDaoImpl<Answer> implements AnswerDao {

	public List<Answer> listAnswer() {
		String hql = "from Answer ";
		return this.executeFind(hql);
	}

	
}
