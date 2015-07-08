package com.bskcare.ch.dao.impl.timeLine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bskcare.ch.base.dao.impl.BaseDaoImpl;
import com.bskcare.ch.dao.timeLine.TimeLineRuleDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.vo.timeLine.TimeLineRule;

@Repository
@SuppressWarnings("unchecked")
public class TimeLineRuleDaoImpl extends BaseDaoImpl<TimeLineRule> implements
		TimeLineRuleDao {
	public List<TimeLineRule> queryList(TimeLineRule rule) {
		String hql = "from TimeLineRule where 1=1 ";
		ArrayList args = new ArrayList();
		if (rule != null) {
			if (rule.getConType() != null && rule.getConType() != 0) {
				hql += " and conType = ?";
				args.add(rule.getConType());
			}
			if (rule.getIsAlert() != null && rule.getIsAlert() != 0) {
				hql += " and isAlert = ?";
				args.add(rule.getIsAlert());
			}
			if (rule.getCycleType() != null && rule.getCycleType() != 0) {
				hql += " and cycleType = ?";
				args.add(rule.getCycleType());
			}
			if (rule.getResult() != null && !rule.getResult().equals("0")) {
				hql += " and result = ?";
				args.add(rule.getResult());
			}
			if (rule.getSoftType() != null && rule.getSoftType() != 0) {
				hql += " and softType = ?";
				args.add(rule.getSoftType());
			}
			if (rule.getStauts() != null && rule.getStauts() != 0) {
				hql += " and stauts = ?";
				args.add(rule.getStauts());
			}
		}
		hql += " order by taskTime asc ";
		return executeFind(hql, args.toArray());
	}

	public void saveOrUpdate(TimeLineRule rule) {
		if (rule != null) {
			if (rule.getId() != null) {
				this.update(rule);
			} else if (rule.getId() == null) {
				this.add(rule);
			}
		}
	}

	public PageObject<TimeLineRule> queryList(TimeLineRule rule, QueryInfo info) {
		String hql = "from TimeLineRule where 1=1 ";
		ArrayList args = new ArrayList();
		if (rule != null) {
			if (rule.getConType() != null && rule.getConType() != 0) {
				hql += " and conType = ?";
				args.add(rule.getConType());
			}
			if (rule.getIsAlert() != null && rule.getIsAlert() != 0) {
				hql += " and isAlert = ?";
				args.add(rule.getIsAlert());
			}
			if (rule.getCycleType() != null && rule.getCycleType() != 0) {
				hql += " and cycleType = ?";
				args.add(rule.getCycleType());
			}
			if (rule.getResult() != null && !rule.getResult().equals("0")) {
				hql += " and result = ?";
				args.add(rule.getResult());
			}
			if (rule.getSoftType() != null && rule.getSoftType() != 0) {
				hql += " and softType = ?";
				args.add(rule.getSoftType());
			}
			if (rule.getStauts() != null && rule.getStauts() != 0) {
				hql += " and stauts = ?";
				args.add(rule.getStauts());
			}
		}
		return queryObjects(hql, args.toArray(), info);
	}

	public List<TimeLineRule> queryListByTestDate(String result,
			Date testDate, Integer soft, Integer isPay, Date serviceDate,
			Integer complication) {
		String hql = "from TimeLineRule where 1=1 ";
		ArrayList args = new ArrayList();
		if (!StringUtils.isEmpty(result)) {
			hql += " and (result =? or result = ? ) ";
			args.add(result);
			args.add(TimeLineRule.RESULT_ALL);
		}
		if (isPay != null) {
			hql += " and ( isPay = ? or isPay = ? or isPay = ?) ";
			args.add(isPay);
			args.add(TimeLineRule.ISPAY_ALL);
			args.add(TimeLineRule.ISPAY_FREE_HINT);
		}
		if (soft != null) {
			hql += " and softType = ? ";
			args.add(soft);
		}
		hql += " and (complication = ? or complication = ?)";
		args.add(complication);
		args.add(TimeLineRule.COMPLICATION_ALL);

		hql += " and (";
		hql += " cycleType = ?";
		args.add(TimeLineRule.CYCLETYPE_EVERYDAY);
		if (testDate != null) {
			hql += " or ( DATEDIFF(NOW(),?) = 0 and cycleType = ? ) ";
			args.add(testDate);
			args.add(TimeLineRule.CYCLETYPE_SOLE);
			hql += " or ( ";
			hql += " (DATEDIFF(NOW(),?)%cycle=0 and cycleType = ? and (DATEDIFF(NOW(),?)) != 0 )";
			args.add(testDate);
			args.add(TimeLineRule.CYCLETYPE_CYCLICITY_TEST);
			args.add(testDate);
			if (serviceDate != null) {
				hql += " or (DATEDIFF(NOW(),?)%cycle=0 and cycleType = ? and (DATEDIFF(NOW(),?)) != 0 )";
				args.add(testDate);
				args.add(TimeLineRule.CYCLETYPE_CYCLICITY_SERVICE);
				args.add(testDate);
			}
			hql += " ) ";
		}
		hql += " ) ";
		hql += " order by taskTime asc ";
		return executeFind(hql, args.toArray());
	}

}
