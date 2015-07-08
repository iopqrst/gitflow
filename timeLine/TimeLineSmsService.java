package com.bskcare.ch.service.timeLine;

import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineSms;

public interface TimeLineSmsService {
	PageObject<TimeLineSms> querypageObject(TimeLineSms lineSMS,QueryInfo info);
	
	TimeLineSms addTimeLineSms(TimeLineSms lineSms);
	
	void delete(Integer id);
	
	TimeLineSms load(Integer id);
	
	void update(TimeLineSms lineSms);
}
