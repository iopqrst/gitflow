package com.bskcare.ch.service.impl.timeLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.timeLine.TimeLineContentDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.timeLine.TimeLineContentService;
import com.bskcare.ch.vo.timeLine.TimeLineContent;

@Service
public class TimeLineContentServiceImpl implements TimeLineContentService {
	@Autowired
	private TimeLineContentDao contentDao;

	public void add(TimeLineContent timeLineContent) {
		contentDao.add(timeLineContent);
	}

	public PageObject<TimeLineContent> queryTimeLineContentToPage(
			QueryInfo info, TimeLineContent content) {
		return contentDao.queryTimeLineContentToPage(info, content);
	}

	public void del(Integer id) {
		contentDao.delete(id);
	}

	public TimeLineContent getLineContentById(Integer id) {
		return contentDao.load(id);
	}

	public void update(TimeLineContent timeLineContent) {
		contentDao.update(timeLineContent);
	}

}
