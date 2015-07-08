package com.bskcare.ch.service.impl.timeLine;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskcare.ch.dao.timeLine.TimeLineSmsDao;
import com.bskcare.ch.query.util.PageObject;
import com.bskcare.ch.query.util.QueryInfo;
import com.bskcare.ch.service.timeLine.TimeLineSmsService;
import com.bskcare.ch.vo.timeLine.TimeLineSms;

@Service
public class TimeLineSmsServiceImpl implements TimeLineSmsService {
	@Autowired
	TimeLineSmsDao lineSmsDao;

	public PageObject<TimeLineSms> querypageObject(TimeLineSms lineSMS,
			QueryInfo info) {
		return lineSmsDao.querypageObject(lineSMS, info);
	}

	public TimeLineSms addTimeLineSms(TimeLineSms lineSms) {
		lineSms.setCreateDate(new Date());
		return lineSmsDao.add(lineSms);
	}

	public void delete(Integer id) {
		lineSmsDao.delete(id);
	}

	public TimeLineSms load(Integer id) {
		return lineSmsDao.load(id);
	}

	public void update(TimeLineSms lineSms) {
		lineSmsDao.update(lineSms);
	}

}
