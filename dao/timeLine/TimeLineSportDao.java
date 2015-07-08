package com.bskcare.ch.dao.timeLine;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineSport;

public interface TimeLineSportDao extends BaseDao<TimeLineSport> {
	/**
	 * 时间轴运动库分页
	 * 
	 * @param sport
	 * @param queryInfo
	 * @return
	 */
	public PageObject<TimeLineSport> getLineSport(TimeLineSport sport,
			QueryInfo queryInfo);

	public List<TimeLineSport> getLineSport(TimeLineSport sport);
}
