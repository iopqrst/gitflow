package com.bskcare.ch.dao.timeLine;

import java.util.List;

import com.bskcare.ch.base.dao.BaseDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.vo.timeLine.TimeLineSms;

public interface TimeLineSmsDao extends BaseDao<TimeLineSms> {
	PageObject<TimeLineSms> querypageObject(TimeLineSms lineSMS,QueryInfo info);
	
	List<TimeLineSms> getListSMS(TimeLineSms lineSMS);
}
