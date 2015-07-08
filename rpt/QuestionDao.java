package com.bskcare.ch.dao.rpt;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.Question;

public interface QuestionDao extends BaseDao<Question>{
    public List<Question> listQuestion();
    public PageObject<Question> queryList(Question question,QueryInfo queryInfo);
}
