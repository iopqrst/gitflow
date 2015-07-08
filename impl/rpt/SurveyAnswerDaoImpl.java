package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SurveyAnswerDao;
import com.bskcare.ch.vo.rpt.SurveyAnswer;
@Repository
@SuppressWarnings("unchecked")
public class SurveyAnswerDaoImpl extends BaseDaoImpl<SurveyAnswer> implements SurveyAnswerDao{

	public List<SurveyAnswer> queryAid(Integer sid) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" SELECT * FROM rpt_survey_answer a ");
		sql.append(" WHERE a.sid=? ");
		args.add(sid);
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), SurveyAnswer.class);
	}

	public List<SurveyAnswer> findSurveyAnswerBySid(Integer sid, Integer clientId) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from rpt_survey_answer WHERE 1=1 ");
		if(null != sid && null != clientId) {
			sql.append(" AND clientId=? AND sid=? ");
			args.add(clientId);
			args.add(sid);
		}
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), SurveyAnswer.class);
	}

}
