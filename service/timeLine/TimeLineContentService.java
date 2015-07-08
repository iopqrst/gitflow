package com.bskcare.ch.service.timeLine;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineContent;

public interface TimeLineContentService {
	/**
	 * 添加任务提示语句
	 * @param timeLineContent
	 */
	public void add(TimeLineContent timeLineContent);
	/**
	 * 删除任务提示语句
	 * @param id
	 */
	public void del(Integer id);
	/**
	 * 分页显示
	 * @param info
	 * @param content
	 * @return
	 */
	public PageObject<TimeLineContent> queryTimeLineContentToPage(
			QueryInfo info, TimeLineContent content);
	/**
	 * 根据id获得任务提示语句
	 * @param timeLineContent
	 */
	public TimeLineContent getLineContentById(Integer id);
	/**
	 * 修改任务提示内容
	 * @param timeLineContent
	 */
	public void update(TimeLineContent timeLineContent);
}
