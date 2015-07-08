package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.vo.rpt.Answer;

public interface AnswerDao extends BaseDao<Answer>{
	public List<Answer> listAnswer();
}
