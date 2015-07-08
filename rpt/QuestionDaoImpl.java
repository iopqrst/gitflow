package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.QuestionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.rpt.Question;

@Repository
@SuppressWarnings("unchecked")
public class QuestionDaoImpl extends BaseDaoImpl<Question> implements
		QuestionDao {

	public List<Question> listQuestion() {
		String hql = "from Question ";
		return this.executeFind(hql);
	}

	public PageObject<Question> queryList(Question question,QueryInfo queryInfo) {
		ArrayList args = new ArrayList();
		String hql = " select q.* from rpt_question q where 1=1 ";
		if(null != question) {
			if(null != question.getQid()) {
				hql += " and q.qid=? ";
				args.add(question.getQid());
			}
			if(!StringUtils.isEmpty(question.getQuestion())) {
				hql += " and q.question=? ";
				args.add(question.getQuestion());
			}
			if(null != question.getStatus()) {
				hql += " and q.status=? ";
				args.add(question.getStatus());
			}
		}
		return this.queryObjectsBySql(hql,null,null,args.toArray(),queryInfo,Question.class);
	}

}
