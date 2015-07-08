package com.bskcare.ch.dao.timeLine;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineContent;

public interface TimeLineContentDao extends BaseDao<TimeLineContent> {

	public PageObject<TimeLineContent> queryTimeLineContentToPage(
			QueryInfo info, TimeLineContent content);

	/**
	 * 根据用户是否付费以及任务类型，返回提示内容
	 * 
	 * @param content
	 * @return
	 */
	public List<TimeLineContent> queryTimeLineContent(TimeLineContent content);

}
