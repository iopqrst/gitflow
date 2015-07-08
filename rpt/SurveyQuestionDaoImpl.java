package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.bo.SurveyQuestionAnswerExtend;
import com.bskcare.ch.bo.SurveyQuestionExtend;
import com.bskcare.ch.dao.rpt.SurveyQuestionDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.rpt.SurveyQuestion;
@Repository
@SuppressWarnings("unchecked")
public class SurveyQuestionDaoImpl extends BaseDaoImpl<SurveyQuestion> implements SurveyQuestionDao{

	public List<SurveyQuestionAnswerExtend> listBySid(Integer sid) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" select s1.question, s1.sid, a.* from rpt_answer a ");
		sql.append(" right join ");
		sql.append(" ( select q.question, q.qid, s.sid from `rpt_survey_question` s ");
		sql.append(" left join ");
		sql.append(" rpt_question q on s.qid = q.qid ");
		sql.append(" where s.sid = ? ");
		args.add(sid);
		sql.append(" ) s1 on a.qid = s1.qid ");
		sql.append(" where s1.qid is not NULL ");
		sql.append(" ORDER BY a.qid,a.aid ASC ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), SurveyQuestionAnswerExtend.class);
	}

	public List<SurveyQuestion> queryQid(Integer sid) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append(" select * FROM rpt_survey_question s ");
		if(null != sid) {
			sql.append(" WHERE s.sid=? ");
			args.add(sid);
		}
		sql.append(" ORDER BY RAND() LIMIT 6  ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), SurveyQuestion.class);
	}

	public PageObject<SurveyQuestionExtend> queryList(QueryInfo queryInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s1.sqid,s1.`name` name,q.*,s1.createTime cTime,s1.status ");
		sql.append(" from (SELECT sq.id sqid,sq.qid,s.createTime,s.`name`,s.status ");
		sql.append(" from `rpt_survey_question` sq INNER JOIN ");
		sql.append(" rpt_survey s ON sq.sid=s.id AND s.`name` ");
		sql.append(" is NOT NULL) s1 LEFT JOIN rpt_question q ");
		sql.append(" ON s1.qid=q.qid ");
		sql.append(" ORDER BY s1.sqid desc ");
		
		return this.queryObjectsBySql(sql.toString(), null, null, null, queryInfo, SurveyQuestionExtend.class);
	}

	public List<SurveyQuestionExtend> queryListBySid(Integer sid) {
		StringBuffer sql = new StringBuffer();
		ArrayList args = new ArrayList();
		sql.append("select s1.sqid,s1.`name` name,q.*,s1.createTime cTime,s1.status ");
		sql.append(" from (SELECT sq.id sqid,sq.qid,s.createTime,s.`name`,s.status ");
		sql.append(" from `rpt_survey_question` sq INNER JOIN ");
		sql.append(" rpt_survey s ON sq.sid=s.id AND s.`name` ");
		sql.append(" is NOT NULL where 1=1 ");
		if(null != sid) {
			sql.append(" and s.id=? ");
			args.add(sid);
		}
		sql.append(" ) s1 inner JOIN rpt_question q ");
		sql.append(" ON s1.qid=q.qid ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), SurveyQuestionExtend.class);
	}

}
