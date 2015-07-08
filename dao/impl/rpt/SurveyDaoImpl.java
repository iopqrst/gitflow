package com.bskcare.ch.dao.impl.rpt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.rpt.SurveyDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.rpt.Survey;
@Repository
@SuppressWarnings("unchecked")
public class SurveyDaoImpl extends BaseDaoImpl<Survey>  implements SurveyDao{

	public List<Survey> querySurveylist(Survey survey) {
		ArrayList args = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * FROM rpt_survey s ");
		sql.append(" where 1=1 ");
		if(null != survey) {
			if(null != survey.getId()) {
				sql.append(" and s.id=? ");
				args.add(survey.getId());
			}
			if(!StringUtils.isEmpty(survey.getName())) {
				sql.append(" and s.name=? ");
				args.add(survey.getName());
			}
			if(null != survey.getUserId()) {
				sql.append(" and s.userId=? ");
				args.add(survey.getUserId());
			}
			if(null != survey.getStatus()) {
				sql.append(" and s.status=? ");
				args.add(survey.getStatus());
			}
		}
		sql.append(" order by s.id desc ");
		return this.executeNativeQuery(sql.toString(), null, null, args.toArray(), Survey.class);
	}

	public PageObject<Survey> queryList(QueryInfo queryInfo) {
		String hql = " from Survey where status !=2 order by createTime desc ";
		return this.queryObjects(hql, null, queryInfo);
	}

}
