package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.timeLine.TimeLineSportDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.timeLine.TimeLineRule;
import com.bskcare.ch.vo.timeLine.TimeLineSport;

@Repository
@SuppressWarnings("unchecked")
public class TimeLineSportDaoImpl extends BaseDaoImpl<TimeLineSport> implements
		TimeLineSportDao {

	public PageObject<TimeLineSport> getLineSport(TimeLineSport sport,
			QueryInfo queryInfo) {
		String hql = "from TimeLineSport where 1 = 1 ";
		List args = new ArrayList();
		if (sport != null) {
			if (!StringUtils.isEmpty(sport.getResult()) && !sport.getResult().equals("-1")) {
				hql += " and result = ? ";
				args.add(sport.getResult());
			}
			if (sport.getComplication() != null && sport.getComplication()!=-1) {
				hql += " and complication = ? ";
				args.add(sport.getComplication());
			}
			if (!StringUtils.isEmpty(sport.getSportType())) {
				hql += " and sportType = ? ";
				args.add(sport.getSportType());
			}
		}
		return queryObjects(hql, args.toArray(), queryInfo);
	}

	public List<TimeLineSport> getLineSport(TimeLineSport sport) {
		String hql = "from TimeLineSport where 1 = 1 ";
		List args = new ArrayList();
		if (sport != null) {
			if (!StringUtils.isEmpty(sport.getResult())) {
				hql += " and result = ? or result = ? ";
				args.add(sport.getResult());
				args.add(TimeLineRule.RESULT_ALL);
			}
			if (sport.getComplication() != null) {
				hql += " and (complication = ? or complication = ? )";
				args.add(sport.getComplication());
				args.add(TimeLineRule.COMPLICATION_ALL);
			}
			if (sport.getSoftType() != null) {
				hql += " and softType = ? ";
				args.add(sport.getSoftType());
			}
			if (sport.getOverweight() != null) {
				hql += " and overweight = ? ";
				args.add(sport.getOverweight());
			}
			hql += " and stauts = ? ";
			args.add(TimeLineSport.STAUTS_ON);
		}
		return executeFind(hql, args.toArray());
	}

}
