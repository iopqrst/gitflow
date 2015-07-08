package com.bskcare.ch.dao.timeLine;

import java.util.Date;
import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineRule;

public interface TimeLineRuleDao extends BaseDao<TimeLineRule> {
	/**
	 * 根据条件查询规则列表
	 */
	public List<TimeLineRule> queryList(TimeLineRule rule);

	/**
	 * 根据条件查询规则列表
	 * 
	 * @param consequence
	 *            用户评测结果
	 * @param testDate
	 *            用户评测时间
	 * @param soft
	 *            软件类型
	 * @param serviceDate
	 *            购买服务时间
	 * @return
	 */
	public List<TimeLineRule> queryListByTestDate(String consequence,
			Date testDate, Integer soft, Integer isPay, Date serviceDate,
			Integer complications);

	public PageObject<TimeLineRule> queryList(TimeLineRule rule, QueryInfo info);
	
	public void saveOrUpdate(TimeLineRule rule);
}
